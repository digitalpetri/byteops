# ByteOps

[![License](https://img.shields.io/badge/License-EPL%202.0-blue.svg)](https://www.eclipse.org/legal/epl-2.0/)

Utility functions for reading and writing primitive values from byte collections with explicit byte
order and word order control.

## Features

- **Multiple byte sources**: `byte[]`, `java.nio.ByteBuffer`, and Netty `ByteBuf`
- **Four byte ordering variations**: Big-endian, little-endian, and word-swapped variants
- **Unsigned type support**: Optional modules for jOOU and Eclipse Milo unsigned types
- **Zero dependencies**: Core module has no runtime dependencies (besides JSpecify annotations)
- **Java 11+**: Compatible with Java 11 and later

## Installation

Add the dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>com.digitalpetri.util</groupId>
  <artifactId>byteops</artifactId>
  <version>0.1.4-SNAPSHOT</version>
</dependency>
```

### Available Modules

| Module          | Artifact ID        | Description                                                                     |
|-----------------|--------------------|---------------------------------------------------------------------------------|
| Core            | `byteops`          | `byte[]` and `ByteBuffer` support                                               |
| Netty           | `byteops-netty`    | Netty `ByteBuf` support                                                         |
| Unsigned (jOOU) | `byteops-unsigned` | `UByte`, `UShort`, `UInteger`, `ULong` via [jOOU](https://github.com/jOOQ/jOOU) |
| Unsigned (Milo) | `byteops-milo`     | Unsigned types from [Eclipse Milo](https://github.com/eclipse/milo)             |

## Quick Start

### Reading Values

```java
import com.digitalpetri.util.byteops.ByteArrayByteOps;
import com.digitalpetri.util.byteops.ByteOps;

// Get a ByteOps instance for your preferred byte ordering
ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;

byte[] data = new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

short s = ops.getShort(data, 0);   // 0x0001
int i = ops.getInt(data, 0);       // 0x00010203
long l = ops.getLong(data, 0);     // 0x0001020304050607L
float f = ops.getFloat(data, 0);   // Float.intBitsToFloat(0x00010203)
double d = ops.getDouble(data, 0); // Double.longBitsToDouble(0x0001020304050607L)
```

### Writing Values

```java
ByteOps<byte[]> ops = ByteArrayByteOps.LITTLE_ENDIAN;

byte[] buffer = new byte[8];
ops.setInt(buffer, 0, 0x01020304);
ops.setInt(buffer, 4, 0x05060708);
// buffer: [0x04, 0x03, 0x02, 0x01, 0x08, 0x07, 0x06, 0x05]
```

### Working with Arrays

```java
ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;

// Read an array of ints (2 ints = 8 bytes)
byte[] data = new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};
int[] ints = ops.getIntArray(data, 0, 2); // [0x00010203, 0x04050607]

// Write an array of shorts
byte[] buffer = new byte[4];
ops.setShortArray(buffer, 0, new short[] {0x0102, 0x0304});
// buffer: [0x01, 0x02, 0x03, 0x04]
```

### With ByteBuffer

```java
import com.digitalpetri.util.byteops.ByteBufferByteOps;
import java.nio.ByteBuffer;

ByteOps<ByteBuffer> ops = ByteBufferByteOps.BIG_ENDIAN;

ByteBuffer buffer = ByteBuffer.allocate(8);
ops.setLong(buffer, 0, 0x0102030405060708L);

long value = ops.getLong(buffer, 0); // 0x0102030405060708L
```

### With Netty ByteBuf

```java
import com.digitalpetri.util.byteops.netty.ByteBufByteOps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

ByteOps<ByteBuf> ops = ByteBufByteOps.LITTLE_ENDIAN;

ByteBuf buf = Unpooled.buffer(8);
buf.writeZero(8); // ensure writable bytes
ops.setLong(buf, 0, 0x0102030405060708L);

long value = ops.getLong(buf, 0); // 0x0102030405060708L
```

### Unsigned Types (jOOU)

```java
import com.digitalpetri.util.byteops.unsigned.UnsignedByteOps;
import org.joou.UInteger;

ByteOps<byte[]> baseOps = ByteArrayByteOps.BIG_ENDIAN;
UnsignedByteOps<byte[]> ops = UnsignedByteOps.of(baseOps);

byte[] data = new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
UInteger value = ops.getUInt(data, 0); // 4294967295 (not -1)
```

## Byte Ordering

Four byte ordering variations are available for each implementation:

| Constant                 | Byte Order | Word Order |
|--------------------------|------------|------------|
| `BIG_ENDIAN`             | Big        | High-Low   |
| `LITTLE_ENDIAN`          | Little     | High-Low   |
| `BIG_ENDIAN_LOW_HIGH`    | Big        | Low-High   |
| `LITTLE_ENDIAN_LOW_HIGH` | Little     | Low-High   |

### Visual Example

For a 32-bit integer `0x01020304`:

```
BIG_ENDIAN:             [0x01, 0x02, 0x03, 0x04]  (bytes: 1,2,3,4)
LITTLE_ENDIAN:          [0x04, 0x03, 0x02, 0x01]  (bytes: 4,3,2,1)
BIG_ENDIAN_LOW_HIGH:    [0x03, 0x04, 0x01, 0x02]  (words swapped: 3,4,1,2)
LITTLE_ENDIAN_LOW_HIGH: [0x02, 0x01, 0x04, 0x03]  (words swapped: 2,1,4,3)
```

## API Overview

The `ByteOps<T>` interface provides:

### Single Value Operations

| Method                      | Size    | Description                    |
|-----------------------------|---------|--------------------------------|
| `getBoolean` / `setBoolean` | 1 byte  | `false` = 0, `true` = non-zero |
| `getByte` / `setByte`       | 1 byte  | Signed byte                    |
| `getShort` / `setShort`     | 2 bytes | Signed 16-bit integer          |
| `getInt` / `setInt`         | 4 bytes | Signed 32-bit integer          |
| `getLong` / `setLong`       | 8 bytes | Signed 64-bit integer          |
| `getFloat` / `setFloat`     | 4 bytes | IEEE 754 single-precision      |
| `getDouble` / `setDouble`   | 8 bytes | IEEE 754 double-precision      |

### Array Operations

- `get*Array` / `set*Array` - Primitive arrays (`boolean[]`, `byte[]`, `short[]`, etc.)
- `getBoxed*Array` / `setBoxed*Array` - Boxed arrays (`Boolean[]`, `Byte[]`, `Short[]`, etc.)

## Development

### Requirements

- **JDK 17** is required to build (via Maven Toolchains)
- **Target**: Java 11 bytecode

### Maven Toolchains Setup

Create or edit `~/.m2/toolchains.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<toolchains>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>17</version>
    </provides>
    <configuration>
      <jdkHome>/path/to/your/jdk-17</jdkHome>
    </configuration>
  </toolchain>
</toolchains>
```

### Build Commands

```bash
mvn clean package    # Build all modules
mvn test             # Run tests
mvn spotless:apply   # Format code (Google Java Format)
mvn spotless:check   # Check code formatting
```

### Project Structure

```
byteops/
├── byteops/           # Core module (byte[], ByteBuffer)
├── byteops-netty/     # Netty ByteBuf support
├── byteops-unsigned/  # jOOU unsigned types (UByte, UShort, UInteger, ULong)
└── byteops-milo/      # Eclipse Milo unsigned types
```

## License

This project is licensed under
the [Eclipse Public License 2.0](https://www.eclipse.org/legal/epl-2.0/).
