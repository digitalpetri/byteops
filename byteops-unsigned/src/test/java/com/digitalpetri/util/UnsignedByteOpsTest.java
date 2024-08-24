package com.digitalpetri.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.joou.UByte;
import org.joou.UInteger;
import org.joou.ULong;
import org.joou.UShort;
import org.junit.jupiter.api.Test;

class UnsignedByteOpsTest {

  @Test
  void getUByte() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    UnsignedByteOps<byte[]> unsignedOps = UnsignedByteOps.of(ops);

    byte[] bytes = new byte[]{(byte) 0xff};
    UByte uByte = unsignedOps.getUByte(bytes, 0);

    assertEquals(UByte.valueOf((byte) 0xff), uByte);
  }

  @Test
  void setUByte() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    UnsignedByteOps<byte[]> unsignedOps = UnsignedByteOps.of(ops);

    byte[] bytes = new byte[1];
    unsignedOps.setUByte(bytes, 0, UByte.valueOf((byte) 0xff));

    assertEquals((byte) 0xff, bytes[0]);
    assertEquals(0xff, unsignedOps.getUByte(bytes, 0).intValue());
  }

  @Test
  void getUShort() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    UnsignedByteOps<byte[]> unsignedOps = UnsignedByteOps.of(ops);

    byte[] bytes = new byte[]{(byte) 0xff, (byte) 0xff};
    UShort uShort = unsignedOps.getUShort(bytes, 0);

    assertEquals(UShort.valueOf((short) 0xffff), uShort);
  }

  @Test
  void setUShort() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    UnsignedByteOps<byte[]> unsignedOps = UnsignedByteOps.of(ops);

    byte[] bytes = new byte[2];
    unsignedOps.setUShort(bytes, 0, UShort.valueOf((short) 0xffff));

    assertEquals((byte) 0xff, bytes[0]);
    assertEquals((byte) 0xff, bytes[1]);
    assertEquals(0xffff, unsignedOps.getUShort(bytes, 0).intValue());
  }

  @Test
  void getUInt() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    UnsignedByteOps<byte[]> unsignedOps = UnsignedByteOps.of(ops);

    byte[] bytes = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
    UInteger uInteger = unsignedOps.getUInt(bytes, 0);

    assertEquals(UInteger.valueOf(0xffffffffL), uInteger);
  }

  @Test
  void setUInt() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    UnsignedByteOps<byte[]> unsignedOps = UnsignedByteOps.of(ops);

    byte[] bytes = new byte[4];
    unsignedOps.setUInt(bytes, 0, UInteger.valueOf(0xffffffffL));

    assertEquals((byte) 0xff, bytes[0]);
    assertEquals((byte) 0xff, bytes[1]);
    assertEquals((byte) 0xff, bytes[2]);
    assertEquals((byte) 0xff, bytes[3]);
    assertEquals(0xffffffffL, unsignedOps.getUInt(bytes, 0).longValue());
  }

  @Test
  void getULong() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    UnsignedByteOps<byte[]> unsignedOps = UnsignedByteOps.of(ops);

    byte[] bytes = new byte[]{
        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
        (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
    };

    ULong uLong = unsignedOps.getULong(bytes, 0);

    assertEquals(ULong.valueOf(0xffffffffffffffffL), uLong);
  }

  @Test
  void setULong() {
    ByteOps<byte[]> ops = ByteArrayByteOps.BIG_ENDIAN;
    UnsignedByteOps<byte[]> unsignedOps = UnsignedByteOps.of(ops);

    byte[] bytes = new byte[8];
    unsignedOps.setULong(bytes, 0, ULong.valueOf(0xffffffffffffffffL));

    assertEquals((byte) 0xff, bytes[0]);
    assertEquals((byte) 0xff, bytes[1]);
    assertEquals((byte) 0xff, bytes[2]);
    assertEquals((byte) 0xff, bytes[3]);
    assertEquals((byte) 0xff, bytes[4]);
    assertEquals((byte) 0xff, bytes[5]);
    assertEquals((byte) 0xff, bytes[6]);
    assertEquals((byte) 0xff, bytes[7]);
    assertEquals(0xffffffffffffffffL, unsignedOps.getULong(bytes, 0).longValue());
  }

}