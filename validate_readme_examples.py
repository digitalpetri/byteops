#!/usr/bin/env python3
"""
Validates Java code examples in README.md by generating and running JBang scripts.

Usage:
    python validate_readme_examples.py

Prerequisites:
    - JBang installed (brew install jbang or https://www.jbang.dev/download/)
    - Run 'mvn install' first to install SNAPSHOT dependencies to local repo
"""

import re
import subprocess
import sys
from dataclasses import dataclass
from pathlib import Path
from typing import Optional


@dataclass
class CodeExample:
    """Represents a code example extracted from README."""
    name: str
    code: str
    section_heading: str


@dataclass
class ExpectedValue:
    """Represents an expected value from a comment."""
    variable: str
    expected: str
    line: str


def parse_readme(readme_path: Path) -> list[CodeExample]:
    """Extract Java code blocks from README.md with their section headings."""
    content = readme_path.read_text()

    examples = []
    # Find all ```java ... ``` blocks
    pattern = r'###\s+([^\n]+)\n\n```java\n(.*?)```'
    matches = re.findall(pattern, content, re.DOTALL)

    for i, (heading, code) in enumerate(matches):
        name = re.sub(r'[^a-zA-Z0-9]', '_', heading.strip())
        examples.append(CodeExample(
            name=f"Example{i+1}_{name}",
            code=code.strip(),
            section_heading=heading.strip()
        ))

    return examples


def detect_dependencies(code: str) -> list[str]:
    """Determine Maven dependencies based on code content."""
    deps = []

    # Core byteops is always needed
    if 'byteops.netty' in code or 'ByteBufByteOps' in code:
        deps.append('com.digitalpetri.util:byteops-netty:0.1.4-SNAPSHOT')
        deps.append('io.netty:netty-buffer:4.1.112.Final')
    elif 'byteops.unsigned' in code or 'UnsignedByteOps' in code:
        deps.append('com.digitalpetri.util:byteops-unsigned:0.1.4-SNAPSHOT')
        deps.append('org.jooq:joou:0.9.4')
    else:
        deps.append('com.digitalpetri.util:byteops:0.1.4-SNAPSHOT')

    return deps


def fix_imports(code: str) -> str:
    """Add missing imports to code that uses classes without importing them."""
    imports_to_add = []
    existing_imports = set(re.findall(r'import\s+([\w.]+);', code))

    # Map of class usage patterns to their imports
    import_map = {
        'ByteArrayByteOps': 'com.digitalpetri.util.byteops.ByteArrayByteOps',
        'ByteBufferByteOps': 'com.digitalpetri.util.byteops.ByteBufferByteOps',
        'ByteOps<': 'com.digitalpetri.util.byteops.ByteOps',
        'ByteBuffer': 'java.nio.ByteBuffer',
        'ByteBufByteOps': 'com.digitalpetri.util.byteops.netty.ByteBufByteOps',
        'UnsignedByteOps': 'com.digitalpetri.util.byteops.unsigned.UnsignedByteOps',
    }

    for pattern, import_stmt in import_map.items():
        if pattern in code and import_stmt not in existing_imports:
            imports_to_add.append(f'import {import_stmt};')

    if imports_to_add:
        # Find where to insert imports (after existing imports or at beginning)
        if 'import ' in code:
            # Add after last import
            last_import = max(code.rfind(line) for line in code.split('\n') if line.startswith('import '))
            end_of_import_line = code.find('\n', last_import) + 1
            code = code[:end_of_import_line] + '\n'.join(imports_to_add) + '\n' + code[end_of_import_line:]
        else:
            # Add at beginning
            code = '\n'.join(imports_to_add) + '\n\n' + code

    return code


def extract_expected_values(code: str) -> list[ExpectedValue]:
    """Extract expected values from inline comments."""
    expected = []

    # Pattern: variable = expression; // expected_value
    # Examples:
    #   short s = ops.getShort(data, 0);   // 0x0001
    #   // buffer: [0x04, 0x03, 0x02, 0x01, 0x08, 0x07, 0x06, 0x05]

    lines = code.split('\n')
    for line in lines:
        # Assignment with expected value comment
        # Handles: int x = ...; // comment
        #          int[] arr = ...; // comment
        #          ByteOps<byte[]> ops = ...; // comment
        match = re.match(r'\s*(?:\w+(?:<[^>]+>)?(?:\[\])?\s+)?(\w+)\s*=\s*[^;]+;\s*//\s*(.+)', line)
        if match:
            var_name = match.group(1)
            expected_val = match.group(2).strip()
            expected.append(ExpectedValue(var_name, expected_val, line))

        # Comment showing buffer/array contents
        match = re.match(r'\s*//\s*(buffer|ints):\s*\[(.+)\]', line)
        if match:
            var_name = match.group(1)
            expected_val = match.group(2).strip()
            expected.append(ExpectedValue(var_name, f'[{expected_val}]', line))

    return expected


def generate_validation_code(expected_values: list[ExpectedValue]) -> str:
    """Generate println statements for validation."""
    lines = []
    for ev in expected_values:
        if ev.variable == 'buffer':
            # Print buffer as hex array
            lines.append(f'''
        System.out.print("{ev.variable}=[");
        for (int _i = 0; _i < {ev.variable}.length; _i++) {{
            if (_i > 0) System.out.print(", ");
            System.out.printf("0x%02X", {ev.variable}[_i] & 0xFF);
        }}
        System.out.println("]");''')
        elif ev.variable == 'ints':
            # Print int array as hex
            lines.append(f'''
        System.out.print("{ev.variable}=[");
        for (int _i = 0; _i < {ev.variable}.length; _i++) {{
            if (_i > 0) System.out.print(", ");
            System.out.printf("0x%08X", {ev.variable}[_i]);
        }}
        System.out.println("]");''')
        else:
            lines.append(f'        System.out.println("{ev.variable}=" + {ev.variable});')

    return '\n'.join(lines)


def generate_jbang_script(example: CodeExample) -> str:
    """Generate a complete JBang script from a code example."""
    deps = detect_dependencies(example.code)
    code = fix_imports(example.code)
    expected_values = extract_expected_values(example.code)
    validation_code = generate_validation_code(expected_values)

    # Build deps lines
    deps_lines = '\n'.join(f'//DEPS {dep}' for dep in deps)

    # Extract imports from code
    import_lines = []
    code_lines = []
    for line in code.split('\n'):
        if line.strip().startswith('import '):
            import_lines.append(line)
        else:
            code_lines.append(line)

    imports = '\n'.join(import_lines)
    body = '\n'.join(code_lines)

    # Indent body for main method
    indented_body = '\n'.join('        ' + line if line.strip() else '' for line in body.split('\n'))

    script = f'''///usr/bin/env jbang "$0" "$@" ; exit $?
{deps_lines}

{imports}

public class {example.name} {{
    public static void main(String[] args) {{
{indented_body}

        // Validation output
{validation_code}
    }}
}}
'''
    return script


def run_jbang(script_path: Path) -> tuple[bool, str, str]:
    """Run a JBang script and return (success, stdout, stderr)."""
    try:
        result = subprocess.run(
            ['jbang', str(script_path)],
            capture_output=True,
            text=True,
            timeout=60
        )
        return result.returncode == 0, result.stdout, result.stderr
    except subprocess.TimeoutExpired:
        return False, '', 'Timeout after 60 seconds'
    except FileNotFoundError:
        return False, '', 'JBang not found. Install with: brew install jbang'


def parse_hex_value(s: str) -> Optional[int]:
    """Parse a hex or decimal string to int."""
    s = s.strip().rstrip('L')
    # Handle explanatory text like "4294967295 (not -1)" - extract just the number
    match = re.match(r'^(0x[0-9A-Fa-f]+|\d+)', s)
    if match:
        s = match.group(1)
    try:
        if s.startswith('0x') or s.startswith('0X'):
            return int(s, 16)
        return int(s)
    except ValueError:
        return None


def validate_output(stdout: str, expected_values: list[ExpectedValue]) -> list[tuple[str, bool, str]]:
    """Validate stdout against expected values. Returns list of (description, passed, details)."""
    results = []

    for ev in expected_values:
        # Find the corresponding output line
        pattern = f'{ev.variable}='
        found = False
        for line in stdout.split('\n'):
            if line.startswith(pattern):
                found = True
                actual = line[len(pattern):]

                # Handle different comparison types
                if ev.expected.startswith('['):
                    # Array comparison - normalize format
                    actual_normalized = re.sub(r'\s+', '', actual.upper())
                    expected_normalized = re.sub(r'\s+', '', ev.expected.upper())
                    passed = actual_normalized == expected_normalized
                    results.append((
                        f'{ev.variable}',
                        passed,
                        f'actual={actual}, expected={ev.expected}'
                    ))
                elif ev.expected.startswith('Float.') or ev.expected.startswith('Double.'):
                    # Float/double from bits - just verify it ran
                    results.append((
                        f'{ev.variable}',
                        True,
                        f'actual={actual} (expression: {ev.expected})'
                    ))
                else:
                    # Numeric comparison
                    expected_num = parse_hex_value(ev.expected)
                    actual_num = parse_hex_value(actual)
                    if expected_num is not None and actual_num is not None:
                        passed = expected_num == actual_num
                        results.append((
                            f'{ev.variable}',
                            passed,
                            f'actual={actual_num}, expected={ev.expected} ({expected_num})'
                        ))
                    else:
                        # String comparison
                        passed = actual.strip() == ev.expected.strip()
                        results.append((
                            f'{ev.variable}',
                            passed,
                            f'actual={actual}, expected={ev.expected}'
                        ))
                break

        if not found:
            results.append((
                f'{ev.variable}',
                False,
                f'Variable not found in output'
            ))

    return results


def check_prerequisites() -> bool:
    """Check that required tools are available."""
    # Check JBang
    try:
        result = subprocess.run(['jbang', '--version'], capture_output=True, text=True)
        if result.returncode != 0:
            print("Error: JBang not working properly")
            return False
    except FileNotFoundError:
        print("Error: JBang not found. Install with: brew install jbang")
        return False

    return True


def run_maven_install() -> bool:
    """Run mvn install to ensure SNAPSHOT deps are in local repo."""
    print("Building project with Maven (mvn -q install)...")
    try:
        result = subprocess.run(
            ['mvn', '-q', 'install', '-DskipTests'],
            capture_output=True,
            text=True,
            timeout=300
        )
        if result.returncode != 0:
            print(f"Maven install failed:\n{result.stderr}")
            return False
        print("Maven build successful")
        return True
    except subprocess.TimeoutExpired:
        print("Maven build timed out")
        return False
    except FileNotFoundError:
        print("Maven not found")
        return False


def main():
    """Main entry point."""
    script_dir = Path(__file__).parent
    readme_path = script_dir / 'README.md'
    output_dir = script_dir / 'generated-examples'

    if not readme_path.exists():
        print(f"Error: README.md not found at {readme_path}")
        sys.exit(1)

    if not check_prerequisites():
        sys.exit(1)

    # Build project first
    if not run_maven_install():
        sys.exit(1)

    # Create output directory for generated scripts
    output_dir.mkdir(exist_ok=True)
    print(f"\nGenerated JBang scripts will be saved to: {output_dir}\n")

    print("Validating README examples...\n")

    examples = parse_readme(readme_path)
    print(f"Found {len(examples)} code examples\n")

    passed = 0
    failed = 0
    generated_scripts = []

    for i, example in enumerate(examples):
        print(f"[{i+1}/{len(examples)}] {example.section_heading}")

        # Generate JBang script
        script = generate_jbang_script(example)
        script_path = output_dir / f"{example.name}.java"
        script_path.write_text(script)
        generated_scripts.append(script_path)
        print(f"  Script: {script_path}")

        # Run it
        print("  Running JBang script...")
        success, stdout, stderr = run_jbang(script_path)

        if not success:
            print(f"  FAILED to run:")
            print(f"    {stderr[:500]}")
            failed += 1
            continue

        # Validate output
        expected_values = extract_expected_values(example.code)
        if expected_values:
            results = validate_output(stdout, expected_values)
            all_passed = all(r[1] for r in results)

            for desc, ok, details in results:
                symbol = '\u2713' if ok else '\u2717'
                print(f"  {symbol} {desc}: {details}")

            if all_passed:
                print("  PASSED")
                passed += 1
            else:
                print("  FAILED")
                failed += 1
        else:
            # No expected values, just verify it compiled and ran
            print("  PASSED (compiled and ran successfully)")
            passed += 1

    print(f"\nSummary: {passed}/{len(examples)} examples passed")
    print(f"\nGenerated JBang scripts for inspection:")
    for script_path in generated_scripts:
        print(f"  {script_path}")

    if failed > 0:
        sys.exit(1)


if __name__ == '__main__':
    main()
