package com.digitalpetri.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.ByteOrder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

abstract class AbstractByteOpsTest<T> {

  protected abstract T getBytes(byte[] bs);

  protected abstract ByteOps<T> getByteOps(ByteOrder byteOrder);

  protected abstract ByteOps<T> getSwappedByteOps(ByteOrder byteOrder);

  @Nested
  class BigEndian {

    private final ByteOps<T> byteOps = getByteOps(ByteOrder.BIG_ENDIAN);

    @Test
    void getBoolean() {
      assertFalse(byteOps.getBoolean(getBytes(new byte[]{0x00}), 0));
      assertTrue(byteOps.getBoolean(getBytes(new byte[]{0x01}), 0));
    }

    @Test
    void setBoolean() {
      T bytes = getBytes(new byte[]{0x00});

      byteOps.setBoolean(bytes, 0, true);

      assertTrue(byteOps.getBoolean(bytes, 0));
    }

    @Test
    void getByte() {
      T bytes = getBytes(new byte[]{0x00});

      byte b = byteOps.getByte(bytes, 0);

      assertEquals(0x00, b);
    }

    @Test
    void setByte() {
      T bytes = getBytes(new byte[]{0x00});

      byteOps.setByte(bytes, 0, (byte) 0x01);

      assertEquals(0x01, byteOps.getByte(bytes, 0));
    }

    @Test
    void getShort() {
      T bytes = getBytes(new byte[]{0x00, 0x01});

      short s = byteOps.getShort(bytes, 0);

      assertEquals(0x0001, s);
    }

    @Test
    void setShort() {
      T bytes = getBytes(new byte[]{0x00, 0x00});

      byteOps.setShort(bytes, 0, (short) 0x0102);

      assertEquals(0x01, byteOps.getByte(bytes, 0));
      assertEquals(0x02, byteOps.getByte(bytes, 1));
      assertEquals(0x0102, byteOps.getShort(bytes, 0));
    }

    @Test
    void getInt() {
      T bytes = getBytes(new byte[]{0x00, 0x01, 0x02, 0x03});

      int i = byteOps.getInt(bytes, 0);

      assertEquals(0x0001_0203, i);
    }

    @Test
    void setInt() {
      T bytes = getBytes(new byte[]{0x00, 0x00, 0x00, 0x00});

      byteOps.setInt(bytes, 0, 0x0102_0304);

      assertEquals(0x01, byteOps.getByte(bytes, 0));
      assertEquals(0x02, byteOps.getByte(bytes, 1));
      assertEquals(0x03, byteOps.getByte(bytes, 2));
      assertEquals(0x04, byteOps.getByte(bytes, 3));
      assertEquals(0x0102_0304, byteOps.getInt(bytes, 0));
    }

    @Test
    void getLong() {
      T bytes = getBytes(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

      long l = byteOps.getLong(bytes, 0);

      assertEquals(0x0001_0203_0405_0607L, l);
    }

    @Test
    void setLong() {
      T bytes = getBytes(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

      byteOps.setLong(bytes, 0, 0x0102_0304_0506_0708L);

      assertEquals(0x01, byteOps.getByte(bytes, 0));
      assertEquals(0x02, byteOps.getByte(bytes, 1));
      assertEquals(0x03, byteOps.getByte(bytes, 2));
      assertEquals(0x04, byteOps.getByte(bytes, 3));
      assertEquals(0x05, byteOps.getByte(bytes, 4));
      assertEquals(0x06, byteOps.getByte(bytes, 5));
      assertEquals(0x07, byteOps.getByte(bytes, 6));
      assertEquals(0x08, byteOps.getByte(bytes, 7));
      assertEquals(0x0102_0304_0506_0708L, byteOps.getLong(bytes, 0));
    }

    @Test
    void getFloat() {
      T bytes = getBytes(new byte[]{0x3F, (byte) 0x80, 0x00, 0x00});

      float f = byteOps.getFloat(bytes, 0);

      assertEquals(Float.intBitsToFloat(0x3F80_0000), f);
    }

    @Test
    void setFloat() {
      T bytes = getBytes(new byte[]{0x00, 0x00, 0x00, 0x00});

      byteOps.setFloat(bytes, 0, Float.intBitsToFloat(0x3F80_0000));

      assertEquals(0x3F, byteOps.getByte(bytes, 0));
      assertEquals((byte) 0x80, byteOps.getByte(bytes, 1));
      assertEquals(0x00, byteOps.getByte(bytes, 2));
      assertEquals(0x00, byteOps.getByte(bytes, 3));
      assertEquals(Float.intBitsToFloat(0x3F80_0000), byteOps.getFloat(bytes, 0));
    }

    @Test
    void getDouble() {
      T bytes = getBytes(new byte[]{0x3F, (byte) 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

      double d = byteOps.getDouble(bytes, 0);

      assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), d);
    }

    @Test
    void setDouble() {
      T bytes = getBytes(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

      byteOps.setDouble(bytes, 0, Double.longBitsToDouble(0x3FF0_0000_0000_0000L));

      assertEquals(0x3F, byteOps.getByte(bytes, 0));
      assertEquals((byte) 0xF0, byteOps.getByte(bytes, 1));
      assertEquals(0x00, byteOps.getByte(bytes, 2));
      assertEquals(0x00, byteOps.getByte(bytes, 3));
      assertEquals(0x00, byteOps.getByte(bytes, 4));
      assertEquals(0x00, byteOps.getByte(bytes, 5));
      assertEquals(0x00, byteOps.getByte(bytes, 6));
      assertEquals(0x00, byteOps.getByte(bytes, 7));
      assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), byteOps.getDouble(bytes, 0));
    }

  }

  @Nested
  class BigEndianWordSwapped {

    private final ByteOps<T> byteOps = getSwappedByteOps(ByteOrder.BIG_ENDIAN);

    @Test
    void getInt() {
      T bytes = getBytes(new byte[]{0x00, 0x01, 0x02, 0x03});

      int i = byteOps.getInt(bytes, 0);

      assertEquals(0x0203_0001, i);
    }

    @Test
    void setInt() {
      T bytes = getBytes(new byte[4]);

      byteOps.setInt(bytes, 0, 0x0102_0304);

      assertEquals(0x03, byteOps.getByte(bytes, 0));
      assertEquals(0x04, byteOps.getByte(bytes, 1));
      assertEquals(0x01, byteOps.getByte(bytes, 2));
      assertEquals(0x02, byteOps.getByte(bytes, 3));
      assertEquals(0x0102_0304, byteOps.getInt(bytes, 0));
    }

    @Test
    void getLong() {
      T bytes = getBytes(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

      long l = byteOps.getLong(bytes, 0);

      assertEquals(0x0607_0405_0203_0001L, l);
    }

    @Test
    void setLong() {
      T bytes = getBytes(new byte[8]);

      byteOps.setLong(bytes, 0, 0x0102_0304_0506_0708L);

      assertEquals(0x07, byteOps.getByte(bytes, 0));
      assertEquals(0x08, byteOps.getByte(bytes, 1));
      assertEquals(0x05, byteOps.getByte(bytes, 2));
      assertEquals(0x06, byteOps.getByte(bytes, 3));
      assertEquals(0x03, byteOps.getByte(bytes, 4));
      assertEquals(0x04, byteOps.getByte(bytes, 5));
      assertEquals(0x01, byteOps.getByte(bytes, 6));
      assertEquals(0x02, byteOps.getByte(bytes, 7));
      assertEquals(0x0102_0304_0506_0708L, byteOps.getLong(bytes, 0));
    }

    @Test
    void getFloat() {
      T bytes = getBytes(new byte[]{0x3F, (byte) 0x80, 0x00, 0x00});

      float f = byteOps.getFloat(bytes, 0);

      assertEquals(Float.intBitsToFloat(0x0000_3F80), f);
    }

    @Test
    void setFloat() {
      T bytes = getBytes(new byte[4]);

      byteOps.setFloat(bytes, 0, Float.intBitsToFloat(0x3F80_0000));

      assertEquals(0x00, byteOps.getByte(bytes, 0));
      assertEquals(0x00, byteOps.getByte(bytes, 1));
      assertEquals(0x3F, byteOps.getByte(bytes, 2));
      assertEquals((byte) 0x80, byteOps.getByte(bytes, 3));
      assertEquals(Float.intBitsToFloat(0x3F80_0000), byteOps.getFloat(bytes, 0));
    }

    @Test
    void getDouble() {
      T bytes = getBytes(new byte[]{0x3F, (byte) 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

      double d = byteOps.getDouble(bytes, 0);

      assertEquals(Double.longBitsToDouble(0x0000_0000_0000_3FF0L), d);
    }

    @Test
    void setDouble() {
      T bytes = getBytes(new byte[8]);

      byteOps.setDouble(bytes, 0, Double.longBitsToDouble(0x3FF0_0000_0000_0000L));

      assertEquals(0x00, byteOps.getByte(bytes, 0));
      assertEquals(0x00, byteOps.getByte(bytes, 1));
      assertEquals(0x00, byteOps.getByte(bytes, 2));
      assertEquals(0x00, byteOps.getByte(bytes, 3));
      assertEquals(0x00, byteOps.getByte(bytes, 4));
      assertEquals(0x00, byteOps.getByte(bytes, 5));
      assertEquals(0x3F, byteOps.getByte(bytes, 6));
      assertEquals((byte) 0xF0, byteOps.getByte(bytes, 7));
      assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), byteOps.getDouble(bytes, 0));
    }

  }

  @Nested
  class LittleEndian {

    private final ByteOps<T> byteOps = getByteOps(ByteOrder.LITTLE_ENDIAN);

    @Test
    void getBoolean() {
      assertFalse(byteOps.getBoolean(getBytes(new byte[]{0x00}), 0));
      assertTrue(byteOps.getBoolean(getBytes(new byte[]{0x01}), 0));
    }

    @Test
    void setBoolean() {
      T bytes = getBytes(new byte[]{0x00});

      byteOps.setBoolean(bytes, 0, true);

      assertTrue(byteOps.getBoolean(bytes, 0));
    }
    
    @Test
    void getByte() {
      T bytes = getBytes(new byte[]{0x00});

      byte b = byteOps.getByte(bytes, 0);

      assertEquals(0x00, b);
    }

    @Test
    void setByte() {
      T bytes = getBytes(new byte[]{0x00});

      byteOps.setByte(bytes, 0, (byte) 0x01);

      assertEquals(0x01, byteOps.getByte(bytes, 0));
    }

    @Test
    void getShort() {
      T bytes = getBytes(new byte[]{0x00, 0x01});

      short s = byteOps.getShort(bytes, 0);

      assertEquals(0x0100, s);
    }

    @Test
    void setShort() {
      T bytes = getBytes(new byte[]{0x00, 0x00});

      byteOps.setShort(bytes, 0, (short) 0x0102);

      assertEquals(0x02, byteOps.getByte(bytes, 0));
      assertEquals(0x01, byteOps.getByte(bytes, 1));
      assertEquals(0x0102, byteOps.getShort(bytes, 0));
    }

    @Test
    void getInt() {
      T bytes = getBytes(new byte[]{0x00, 0x01, 0x02, 0x03});

      int i = byteOps.getInt(bytes, 0);

      assertEquals(0x0302_0100, i);
    }

    @Test
    void setInt() {
      T bytes = getBytes(new byte[]{0x00, 0x00, 0x00, 0x00});

      byteOps.setInt(bytes, 0, 0x0102_0304);

      assertEquals(0x04, byteOps.getByte(bytes, 0));
      assertEquals(0x03, byteOps.getByte(bytes, 1));
      assertEquals(0x02, byteOps.getByte(bytes, 2));
      assertEquals(0x01, byteOps.getByte(bytes, 3));
      assertEquals(0x0102_0304, byteOps.getInt(bytes, 0));
    }

    @Test
    void getLong() {
      T bytes = getBytes(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

      long l = byteOps.getLong(bytes, 0);

      assertEquals(0x0706_0504_0302_0100L, l);
    }

    @Test
    void setLong() {
      T bytes = getBytes(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

      byteOps.setLong(bytes, 0, 0x0102_0304_0506_0708L);

      assertEquals(0x08, byteOps.getByte(bytes, 0));
      assertEquals(0x07, byteOps.getByte(bytes, 1));
      assertEquals(0x06, byteOps.getByte(bytes, 2));
      assertEquals(0x05, byteOps.getByte(bytes, 3));
      assertEquals(0x04, byteOps.getByte(bytes, 4));
      assertEquals(0x03, byteOps.getByte(bytes, 5));
      assertEquals(0x02, byteOps.getByte(bytes, 6));
      assertEquals(0x01, byteOps.getByte(bytes, 7));
      assertEquals(0x0102_0304_0506_0708L, byteOps.getLong(bytes, 0));
    }

    @Test
    void getFloat() {
      T bytes = getBytes(new byte[]{0x00, 0x00, (byte) 0x80, 0x3F});

      float f = byteOps.getFloat(bytes, 0);

      assertEquals(Float.intBitsToFloat(0x3F80_0000), f);
    }

    @Test
    void setFloat() {
      T bytes = getBytes(new byte[]{0x00, 0x00, 0x00, 0x00});

      byteOps.setFloat(bytes, 0, Float.intBitsToFloat(0x3F80_0000));

      assertEquals(0x00, byteOps.getByte(bytes, 0));
      assertEquals(0x00, byteOps.getByte(bytes, 1));
      assertEquals((byte) 0x80, byteOps.getByte(bytes, 2));
      assertEquals(0x3F, byteOps.getByte(bytes, 3));
      assertEquals(Float.intBitsToFloat(0x3F80_0000), byteOps.getFloat(bytes, 0));
    }

    @Test
    void getDouble() {
      T bytes = getBytes(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xF0, 0x3F});

      double d = byteOps.getDouble(bytes, 0);

      assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), d);
    }

    @Test
    void setDouble() {
      T bytes = getBytes(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

      byteOps.setDouble(bytes, 0, Double.longBitsToDouble(0x3FF0_0000_0000_0000L));

      assertEquals(0x00, byteOps.getByte(bytes, 0));
      assertEquals(0x00, byteOps.getByte(bytes, 1));
      assertEquals(0x00, byteOps.getByte(bytes, 2));
      assertEquals(0x00, byteOps.getByte(bytes, 3));
      assertEquals(0x00, byteOps.getByte(bytes, 4));
      assertEquals(0x00, byteOps.getByte(bytes, 5));
      assertEquals((byte) 0xF0, byteOps.getByte(bytes, 6));
      assertEquals(0x3F, byteOps.getByte(bytes, 7));
      assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), byteOps.getDouble(bytes, 0));
    }

  }

  @Nested
  class LittleEndianWordSwapped {

    private final ByteOps<T> byteOps = getSwappedByteOps(ByteOrder.LITTLE_ENDIAN);

    @Test
    void getInt() {
      T bytes = getBytes(new byte[]{0x00, 0x01, 0x02, 0x03});

      int i = byteOps.getInt(bytes, 0);

      assertEquals(0x0100_0302, i);
    }

    @Test
    void setInt() {
      T bytes = getBytes(new byte[4]);

      byteOps.setInt(bytes, 0, 0x0102_0304);

      assertEquals(0x02, byteOps.getByte(bytes, 0));
      assertEquals(0x01, byteOps.getByte(bytes, 1));
      assertEquals(0x04, byteOps.getByte(bytes, 2));
      assertEquals(0x03, byteOps.getByte(bytes, 3));
      assertEquals(0x0102_0304, byteOps.getInt(bytes, 0));
    }

    @Test
    void getLong() {
      T bytes = getBytes(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

      long l = byteOps.getLong(bytes, 0);

      assertEquals(0x0100_0302_0504_0706L, l);
    }

    @Test
    void setLong() {
      T bytes = getBytes(new byte[8]);

      byteOps.setLong(bytes, 0, 0x0102_0304_0506_0708L);

      assertEquals(0x02, byteOps.getByte(bytes, 0));
      assertEquals(0x01, byteOps.getByte(bytes, 1));
      assertEquals(0x04, byteOps.getByte(bytes, 2));
      assertEquals(0x03, byteOps.getByte(bytes, 3));
      assertEquals(0x06, byteOps.getByte(bytes, 4));
      assertEquals(0x05, byteOps.getByte(bytes, 5));
      assertEquals(0x08, byteOps.getByte(bytes, 6));
      assertEquals(0x07, byteOps.getByte(bytes, 7));
    }

    @Test
    void getFloat() {
      T bytes = getBytes(new byte[]{0x00, 0x00, (byte) 0x80, 0x3F});

      float f = byteOps.getFloat(bytes, 0);

      assertEquals(Float.intBitsToFloat(0x0000_3F80), f);
    }

    @Test
    void setFloat() {
      T bytes = getBytes(new byte[4]);

      byteOps.setFloat(bytes, 0, Float.intBitsToFloat(0x3F80_0000));

      assertEquals((byte) 0x80, byteOps.getByte(bytes, 0));
      assertEquals(0x3F, byteOps.getByte(bytes, 1));
      assertEquals(0x00, byteOps.getByte(bytes, 2));
      assertEquals(0x00, byteOps.getByte(bytes, 3));
      assertEquals(Float.intBitsToFloat(0x3F80_0000), byteOps.getFloat(bytes, 0));
    }

    @Test
    void getDouble() {
      T bytes = getBytes(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xF0, 0x3F});

      double d = byteOps.getDouble(bytes, 0);

      assertEquals(Double.longBitsToDouble(0x0000_0000_0000_3FF0L), d);
    }

    @Test
    void setDouble() {
      T bytes = getBytes(new byte[8]);

      byteOps.setDouble(bytes, 0, Double.longBitsToDouble(0x3FF0_0000_0000_0000L));

      assertEquals((byte) 0xF0, byteOps.getByte(bytes, 0));
      assertEquals(0x3F, byteOps.getByte(bytes, 1));
      assertEquals(0x00, byteOps.getByte(bytes, 2));
      assertEquals(0x00, byteOps.getByte(bytes, 3));
      assertEquals(0x00, byteOps.getByte(bytes, 4));
      assertEquals(0x00, byteOps.getByte(bytes, 5));
      assertEquals(0x00, byteOps.getByte(bytes, 6));
      assertEquals(0x00, byteOps.getByte(bytes, 7));
      assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), byteOps.getDouble(bytes, 0));
    }

  }


}
