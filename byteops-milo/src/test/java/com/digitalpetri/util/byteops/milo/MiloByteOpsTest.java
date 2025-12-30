package com.digitalpetri.util.byteops.milo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.digitalpetri.util.byteops.ByteArrayByteOps;
import com.digitalpetri.util.byteops.ByteOps;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.ULong;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UShort;
import org.junit.jupiter.api.Test;

class MiloByteOpsTest {

  @Test
  void getUByte() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[] {(byte) 0xff};
    UByte uByte = miloOps.getUByte(bytes, 0);

    assertEquals(UByte.valueOf((byte) 0xff), uByte);
  }

  @Test
  void getUByteArray() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[] {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04};
    UByte[] uBytes = miloOps.getUByteArray(bytes, 0, 4);

    assertEquals(UByte.valueOf((byte) 0x01), uBytes[0]);
    assertEquals(UByte.valueOf((byte) 0x02), uBytes[1]);
    assertEquals(UByte.valueOf((byte) 0x03), uBytes[2]);
    assertEquals(UByte.valueOf((byte) 0x04), uBytes[3]);
  }

  @Test
  void setUByte() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[1];
    miloOps.setUByte(bytes, 0, UByte.valueOf((byte) 0xff));

    assertEquals((byte) 0xff, bytes[0]);
    assertEquals(0xff, miloOps.getUByte(bytes, 0).intValue());
  }

  @Test
  void setUByteArray() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[4];
    miloOps.setUByteArray(
        bytes,
        0,
        new UByte[] {
          UByte.valueOf((byte) 0x01),
          UByte.valueOf((byte) 0x02),
          UByte.valueOf((byte) 0x03),
          UByte.valueOf((byte) 0x04)
        });

    assertEquals((byte) 0x01, bytes[0]);
    assertEquals((byte) 0x02, bytes[1]);
    assertEquals((byte) 0x03, bytes[2]);
    assertEquals((byte) 0x04, bytes[3]);
    assertEquals(0x01, miloOps.getUByte(bytes, 0).intValue());
    assertEquals(0x02, miloOps.getUByte(bytes, 1).intValue());
    assertEquals(0x03, miloOps.getUByte(bytes, 2).intValue());
    assertEquals(0x04, miloOps.getUByte(bytes, 3).intValue());
  }

  @Test
  void getUShort() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[] {(byte) 0xff, (byte) 0xff};
    UShort uShort = miloOps.getUShort(bytes, 0);

    assertEquals(UShort.valueOf((short) 0xffff), uShort);
  }

  @Test
  void getUShortArray() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[] {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04};
    UShort[] uShorts = miloOps.getUShortArray(bytes, 0, 2);

    assertEquals(UShort.valueOf((short) 0x0102), uShorts[0]);
    assertEquals(UShort.valueOf((short) 0x0304), uShorts[1]);
  }

  @Test
  void setUShort() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[2];
    miloOps.setUShort(bytes, 0, UShort.valueOf((short) 0xffff));

    assertEquals((byte) 0xff, bytes[0]);
    assertEquals((byte) 0xff, bytes[1]);
    assertEquals(0xffff, miloOps.getUShort(bytes, 0).intValue());
  }

  @Test
  void setUShortArray() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[4];
    miloOps.setUShortArray(
        bytes, 0, new UShort[] {UShort.valueOf((short) 0x0102), UShort.valueOf((short) 0x0304)});

    assertEquals((byte) 0x01, bytes[0]);
    assertEquals((byte) 0x02, bytes[1]);
    assertEquals((byte) 0x03, bytes[2]);
    assertEquals((byte) 0x04, bytes[3]);
    assertEquals(0x0102, miloOps.getUShort(bytes, 0).intValue());
    assertEquals(0x0304, miloOps.getUShort(bytes, 2).intValue());
  }

  @Test
  void getUInt() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
    UInteger uInteger = miloOps.getUInt(bytes, 0);

    assertEquals(UInteger.valueOf(0xffffffffL), uInteger);
  }

  @Test
  void getUIntArray() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes =
        new byte[] {
          (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04,
          (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08
        };

    UInteger[] uInts = miloOps.getUIntArray(bytes, 0, 2);

    assertEquals(UInteger.valueOf(0x01020304), uInts[0]);
    assertEquals(UInteger.valueOf(0x05060708), uInts[1]);
  }

  @Test
  void setUInt() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[4];
    miloOps.setUInt(bytes, 0, UInteger.valueOf(0xffffffffL));

    assertEquals((byte) 0xff, bytes[0]);
    assertEquals((byte) 0xff, bytes[1]);
    assertEquals((byte) 0xff, bytes[2]);
    assertEquals((byte) 0xff, bytes[3]);
    assertEquals(0xffffffffL, miloOps.getUInt(bytes, 0).longValue());
  }

  @Test
  void setUIntArray() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[8];
    miloOps.setUIntArray(
        bytes, 0, new UInteger[] {UInteger.valueOf(0x01020304), UInteger.valueOf(0x05060708)});

    assertEquals((byte) 0x01, bytes[0]);
    assertEquals((byte) 0x02, bytes[1]);
    assertEquals((byte) 0x03, bytes[2]);
    assertEquals((byte) 0x04, bytes[3]);
    assertEquals((byte) 0x05, bytes[4]);
    assertEquals((byte) 0x06, bytes[5]);
    assertEquals((byte) 0x07, bytes[6]);
    assertEquals((byte) 0x08, bytes[7]);
    assertEquals(0x01020304, miloOps.getUInt(bytes, 0).intValue());
    assertEquals(0x05060708, miloOps.getUInt(bytes, 4).intValue());
  }

  @Test
  void getULong() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes =
        new byte[] {
          (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
          (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
        };

    ULong uLong = miloOps.getULong(bytes, 0);

    assertEquals(ULong.valueOf(0xffffffffffffffffL), uLong);
  }

  @Test
  void getULongArray() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes =
        new byte[] {
          (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04,
          (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08,
          (byte) 0x09, (byte) 0x0a, (byte) 0x0b, (byte) 0x0c,
          (byte) 0x0d, (byte) 0x0e, (byte) 0x0f, (byte) 0x10
        };

    ULong[] uLongs = miloOps.getULongArray(bytes, 0, 2);

    assertEquals(ULong.valueOf(0x0102030405060708L), uLongs[0]);
    assertEquals(ULong.valueOf(0x090a0b0c0d0e0f10L), uLongs[1]);
  }

  @Test
  void setULong() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[8];
    miloOps.setULong(bytes, 0, ULong.valueOf(0xffffffffffffffffL));

    assertEquals((byte) 0xff, bytes[0]);
    assertEquals((byte) 0xff, bytes[1]);
    assertEquals((byte) 0xff, bytes[2]);
    assertEquals((byte) 0xff, bytes[3]);
    assertEquals((byte) 0xff, bytes[4]);
    assertEquals((byte) 0xff, bytes[5]);
    assertEquals((byte) 0xff, bytes[6]);
    assertEquals((byte) 0xff, bytes[7]);
    assertEquals(0xffffffffffffffffL, miloOps.getULong(bytes, 0).longValue());
  }

  @Test
  void setULongArray() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    MiloByteOps<byte[]> miloOps = MiloByteOps.of(ops);

    byte[] bytes = new byte[16];
    miloOps.setULongArray(
        bytes,
        0,
        new ULong[] {ULong.valueOf(0x0102030405060708L), ULong.valueOf(0x090a0b0c0d0e0f10L)});

    assertEquals((byte) 0x01, bytes[0]);
    assertEquals((byte) 0x02, bytes[1]);
    assertEquals((byte) 0x03, bytes[2]);
    assertEquals((byte) 0x04, bytes[3]);
    assertEquals((byte) 0x05, bytes[4]);
    assertEquals((byte) 0x06, bytes[5]);
    assertEquals((byte) 0x07, bytes[6]);
    assertEquals((byte) 0x08, bytes[7]);
    assertEquals((byte) 0x09, bytes[8]);
    assertEquals((byte) 0x0a, bytes[9]);
    assertEquals((byte) 0x0b, bytes[10]);
    assertEquals((byte) 0x0c, bytes[11]);
    assertEquals((byte) 0x0d, bytes[12]);
    assertEquals((byte) 0x0e, bytes[13]);
    assertEquals((byte) 0x0f, bytes[14]);
    assertEquals((byte) 0x10, bytes[15]);
    assertEquals(0x0102030405060708L, miloOps.getULong(bytes, 0).longValue());
    assertEquals(0x090a0b0c0d0e0f10L, miloOps.getULong(bytes, 8).longValue());
  }
}
