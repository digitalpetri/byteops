package com.digitalpetri.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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

    @Nested
    class ScalarGet {

      @Test
      void getBoolean() {
        assertFalse(byteOps.getBoolean(getBytes(new byte[] {0x00}), 0));
        assertTrue(byteOps.getBoolean(getBytes(new byte[] {0x01}), 0));
      }

      @Test
      void getByte() {
        T bytes = getBytes(new byte[] {0x00});

        byte b = byteOps.getByte(bytes, 0);

        assertEquals(0x00, b);
      }

      @Test
      void getShort() {
        T bytes = getBytes(new byte[] {0x00, 0x01});

        short s = byteOps.getShort(bytes, 0);

        assertEquals(0x0001, s);
      }

      @Test
      void getInt() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03});

        int i = byteOps.getInt(bytes, 0);

        assertEquals(0x0001_0203, i);
      }

      @Test
      void getLong() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

        long l = byteOps.getLong(bytes, 0);

        assertEquals(0x0001_0203_0405_0607L, l);
      }

      @Test
      void getFloat() {
        T bytes = getBytes(new byte[] {0x3F, (byte) 0x80, 0x00, 0x00});

        float f = byteOps.getFloat(bytes, 0);

        assertEquals(Float.intBitsToFloat(0x3F80_0000), f);
      }

      @Test
      void getDouble() {
        T bytes = getBytes(new byte[] {0x3F, (byte) 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

        double d = byteOps.getDouble(bytes, 0);

        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), d);
      }
    }

    @Nested
    class ScalarSet {

      @Test
      void setBoolean() {
        T bytes = getBytes(new byte[] {0x00});

        byteOps.setBoolean(bytes, 0, true);

        assertTrue(byteOps.getBoolean(bytes, 0));
      }

      @Test
      void setByte() {
        T bytes = getBytes(new byte[] {0x00});

        byteOps.setByte(bytes, 0, (byte) 0x01);

        assertEquals(0x01, byteOps.getByte(bytes, 0));
      }

      @Test
      void setShort() {
        T bytes = getBytes(new byte[] {0x00, 0x00});

        byteOps.setShort(bytes, 0, (short) 0x0102);

        assertEquals(0x01, byteOps.getByte(bytes, 0));
        assertEquals(0x02, byteOps.getByte(bytes, 1));
        assertEquals(0x0102, byteOps.getShort(bytes, 0));
      }

      @Test
      void setInt() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00});

        byteOps.setInt(bytes, 0, 0x0102_0304);

        assertEquals(0x01, byteOps.getByte(bytes, 0));
        assertEquals(0x02, byteOps.getByte(bytes, 1));
        assertEquals(0x03, byteOps.getByte(bytes, 2));
        assertEquals(0x04, byteOps.getByte(bytes, 3));
        assertEquals(0x0102_0304, byteOps.getInt(bytes, 0));
      }

      @Test
      void setLong() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

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
      void setFloat() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00});

        byteOps.setFloat(bytes, 0, Float.intBitsToFloat(0x3F80_0000));

        assertEquals(0x3F, byteOps.getByte(bytes, 0));
        assertEquals((byte) 0x80, byteOps.getByte(bytes, 1));
        assertEquals(0x00, byteOps.getByte(bytes, 2));
        assertEquals(0x00, byteOps.getByte(bytes, 3));
        assertEquals(Float.intBitsToFloat(0x3F80_0000), byteOps.getFloat(bytes, 0));
      }

      @Test
      void setDouble() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

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
    class ArrayGet {

      @Test
      void getBooleanArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01});

        boolean[] bs = byteOps.getBooleanArray(bytes, 0, 2);

        assertFalse(bs[0]);
        assertTrue(bs[1]);
        assertArrayEquals(new boolean[] {false, true}, bs);
      }

      @Test
      void getByteArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01});

        byte[] bs = byteOps.getByteArray(bytes, 0, 2);

        assertEquals(0x00, bs[0]);
        assertEquals(0x01, bs[1]);
        assertArrayEquals(new byte[] {0x00, 0x01}, bs);
      }

      @Test
      void getShortArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03});

        short[] ss = byteOps.getShortArray(bytes, 0, 2);

        assertEquals(0x0001, ss[0]);
        assertEquals(0x0203, ss[1]);
        assertArrayEquals(new short[] {0x0001, 0x0203}, ss);
      }

      @Test
      void getIntArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

        int[] is = byteOps.getIntArray(bytes, 0, 2);

        assertEquals(0x0001_0203, is[0]);
        assertEquals(0x0405_0607, is[1]);
        assertArrayEquals(new int[] {0x0001_0203, 0x0405_0607}, is);
      }

      @Test
      void getLongArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                  0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
                });

        long[] ls = byteOps.getLongArray(bytes, 0, 2);

        assertEquals(0x0001_0203_0405_0607L, ls[0]);
        assertEquals(0x0809_0A0B_0C0D_0E0FL, ls[1]);
        assertArrayEquals(new long[] {0x0001_0203_0405_0607L, 0x0809_0A0B_0C0D_0E0FL}, ls);
      }

      @Test
      void getFloatArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x3F, (byte) 0x80, 0x00, 0x00,
                  0x3F, (byte) 0x80, 0x00, 0x00
                });

        float[] fs = byteOps.getFloatArray(bytes, 0, 2);

        assertEquals(Float.intBitsToFloat(0x3F80_0000), fs[0]);
        assertEquals(Float.intBitsToFloat(0x3F80_0000), fs[1]);
        assertArrayEquals(
            new float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x3F80_0000)}, fs);
      }

      @Test
      void getDoubleArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x3F, (byte) 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                  0x3F, (byte) 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
                });

        double[] ds = byteOps.getDoubleArray(bytes, 0, 2);

        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), ds[0]);
        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), ds[1]);
        assertArrayEquals(
            new double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L)
            },
            ds);
      }

      @Test
      void getBoxedBooleanArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01});

        Boolean[] bs = byteOps.getBoxedBooleanArray(bytes, 0, 2);

        assertFalse(bs[0]);
        assertTrue(bs[1]);
        assertArrayEquals(new Boolean[] {false, true}, bs);
      }

      @Test
      void getBoxedByteArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01});

        Byte[] bs = byteOps.getBoxedByteArray(bytes, 0, 2);

        assertEquals((byte) 0x00, bs[0]);
        assertEquals((byte) 0x01, bs[1]);
        assertArrayEquals(new Byte[] {0x00, 0x01}, bs);
      }

      @Test
      void getBoxedShortArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03});

        Short[] ss = byteOps.getBoxedShortArray(bytes, 0, 2);

        assertEquals((short) 0x0001, ss[0]);
        assertEquals((short) 0x0203, ss[1]);
        assertArrayEquals(new Short[] {0x0001, 0x0203}, ss);
      }

      @Test
      void getBoxedIntArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

        Integer[] is = byteOps.getBoxedIntArray(bytes, 0, 2);

        assertEquals(0x0001_0203, is[0]);
        assertEquals(0x0405_0607, is[1]);
        assertArrayEquals(new Integer[] {0x0001_0203, 0x0405_0607}, is);
      }

      @Test
      void getBoxedLongArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                  0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
                });

        Long[] ls = byteOps.getBoxedLongArray(bytes, 0, 2);

        assertEquals(0x0001_0203_0405_0607L, ls[0]);
        assertEquals(0x0809_0A0B_0C0D_0E0FL, ls[1]);
        assertArrayEquals(new Long[] {0x0001_0203_0405_0607L, 0x0809_0A0B_0C0D_0E0FL}, ls);
      }

      @Test
      void getBoxedFloatArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x3F, (byte) 0x80, 0x00, 0x00,
                  0x3F, (byte) 0x80, 0x00, 0x00
                });

        Float[] fs = byteOps.getBoxedFloatArray(bytes, 0, 2);

        assertEquals(Float.intBitsToFloat(0x3F80_0000), fs[0]);
        assertEquals(Float.intBitsToFloat(0x3F80_0000), fs[1]);
        assertArrayEquals(
            new Float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x3F80_0000)}, fs);
      }

      @Test
      void getBoxedDoubleArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x3F, (byte) 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                  0x3F, (byte) 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
                });

        Double[] ds = byteOps.getBoxedDoubleArray(bytes, 0, 2);

        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), ds[0]);
        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), ds[1]);
        assertArrayEquals(
            new Double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L)
            },
            ds);
      }
    }

    @Nested
    class ArraySet {

      @Test
      void setBooleanArray() {
        T bytes = getBytes(new byte[] {0x00, 0x00});

        byteOps.setBooleanArray(bytes, 0, new boolean[] {true, true});

        assertTrue(byteOps.getBoolean(bytes, 0));
        assertTrue(byteOps.getBoolean(bytes, 1));
      }

      @Test
      void setByteArray() {
        T bytes = getBytes(new byte[] {0x00, 0x00});

        byteOps.setByteArray(bytes, 0, new byte[] {0x01, 0x02});

        assertEquals(0x01, byteOps.getByte(bytes, 0));
        assertEquals(0x02, byteOps.getByte(bytes, 1));
      }

      @Test
      void setShortArray() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00});

        byteOps.setShortArray(bytes, 0, new short[] {0x0102, 0x0304});

        assertEquals(0x01, byteOps.getByte(bytes, 0));
        assertEquals(0x02, byteOps.getByte(bytes, 1));
        assertEquals(0x03, byteOps.getByte(bytes, 2));
        assertEquals(0x04, byteOps.getByte(bytes, 3));
        assertEquals(0x0102, byteOps.getShort(bytes, 0));
        assertEquals(0x0304, byteOps.getShort(bytes, 2));
      }

      @Test
      void setIntArray() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

        byteOps.setIntArray(bytes, 0, new int[] {0x0102_0304, 0x0506_0708});

        assertEquals(0x01, byteOps.getByte(bytes, 0));
        assertEquals(0x02, byteOps.getByte(bytes, 1));
        assertEquals(0x03, byteOps.getByte(bytes, 2));
        assertEquals(0x04, byteOps.getByte(bytes, 3));
        assertEquals(0x05, byteOps.getByte(bytes, 4));
        assertEquals(0x06, byteOps.getByte(bytes, 5));
        assertEquals(0x07, byteOps.getByte(bytes, 6));
        assertEquals(0x08, byteOps.getByte(bytes, 7));
        assertEquals(0x0102_0304, byteOps.getInt(bytes, 0));
        assertEquals(0x0506_0708, byteOps.getInt(bytes, 4));
      }

      @Test
      void setLongArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
                });

        byteOps.setLongArray(bytes, 0, new long[] {0x0102_0304_0506_0708L, 0x090A_0B0C_0D0E_0F10L});

        assertEquals(0x01, byteOps.getByte(bytes, 0));
        assertEquals(0x02, byteOps.getByte(bytes, 1));
        assertEquals(0x03, byteOps.getByte(bytes, 2));
        assertEquals(0x04, byteOps.getByte(bytes, 3));
        assertEquals(0x05, byteOps.getByte(bytes, 4));
        assertEquals(0x06, byteOps.getByte(bytes, 5));
        assertEquals(0x07, byteOps.getByte(bytes, 6));
        assertEquals(0x08, byteOps.getByte(bytes, 7));
        assertEquals(0x09, byteOps.getByte(bytes, 8));
        assertEquals(0x0A, byteOps.getByte(bytes, 9));
        assertEquals(0x0B, byteOps.getByte(bytes, 10));
        assertEquals(0x0C, byteOps.getByte(bytes, 11));
        assertEquals(0x0D, byteOps.getByte(bytes, 12));
        assertEquals(0x0E, byteOps.getByte(bytes, 13));
        assertEquals(0x0F, byteOps.getByte(bytes, 14));
        assertEquals(0x10, byteOps.getByte(bytes, 15));
        assertEquals(0x0102_0304_0506_0708L, byteOps.getLong(bytes, 0));
        assertEquals(0x090A_0B0C_0D0E_0F10L, byteOps.getLong(bytes, 8));
      }

      @Test
      void setFloatArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x00, 0x00, 0x00,
                  0x00, 0x00, 0x00, 0x00
                });

        byteOps.setFloatArray(
            bytes,
            0,
            new float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x4000_0000)});

        assertEquals(0x3F, byteOps.getByte(bytes, 0));
        assertEquals((byte) 0x80, byteOps.getByte(bytes, 1));
        assertEquals(0x00, byteOps.getByte(bytes, 2));
        assertEquals(0x00, byteOps.getByte(bytes, 3));
        assertEquals(0x40, byteOps.getByte(bytes, 4));
        assertEquals(0x00, byteOps.getByte(bytes, 5));
        assertEquals(0x00, byteOps.getByte(bytes, 6));
        assertEquals(0x00, byteOps.getByte(bytes, 7));
        assertEquals(Float.intBitsToFloat(0x3F80_0000), byteOps.getFloat(bytes, 0));
        assertEquals(Float.intBitsToFloat(0x4000_0000), byteOps.getFloat(bytes, 4));
      }

      @Test
      void setDoubleArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
                });

        byteOps.setDoubleArray(
            bytes,
            0,
            new double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x4000_0000_0000_0000L)
            });

        assertEquals(0x3F, byteOps.getByte(bytes, 0));
        assertEquals((byte) 0xF0, byteOps.getByte(bytes, 1));
        assertEquals(0x00, byteOps.getByte(bytes, 2));
        assertEquals(0x00, byteOps.getByte(bytes, 3));
        assertEquals(0x00, byteOps.getByte(bytes, 4));
        assertEquals(0x00, byteOps.getByte(bytes, 5));
        assertEquals(0x00, byteOps.getByte(bytes, 6));
        assertEquals(0x00, byteOps.getByte(bytes, 7));
        assertEquals(0x40, byteOps.getByte(bytes, 8));
        assertEquals(0x00, byteOps.getByte(bytes, 9));
        assertEquals(0x00, byteOps.getByte(bytes, 10));
        assertEquals(0x00, byteOps.getByte(bytes, 11));
        assertEquals(0x00, byteOps.getByte(bytes, 12));
        assertEquals(0x00, byteOps.getByte(bytes, 13));
        assertEquals(0x00, byteOps.getByte(bytes, 14));
        assertEquals(0x00, byteOps.getByte(bytes, 15));
        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), byteOps.getDouble(bytes, 0));
        assertEquals(Double.longBitsToDouble(0x4000_0000_0000_0000L), byteOps.getDouble(bytes, 8));
      }

      @Test
      void setBoxedBooleanArray() {
        T bytes = getBytes(new byte[] {0x00, 0x00});

        byteOps.setBoxedBooleanArray(bytes, 0, new Boolean[] {true, true});

        assertTrue(byteOps.getBoolean(bytes, 0));
        assertTrue(byteOps.getBoolean(bytes, 1));
      }

      @Test
      void setBoxedByteArray() {
        T bytes = getBytes(new byte[] {0x00, 0x00});

        byteOps.setBoxedByteArray(bytes, 0, new Byte[] {0x01, 0x02});

        assertEquals(0x01, byteOps.getByte(bytes, 0));
        assertEquals(0x02, byteOps.getByte(bytes, 1));
      }

      @Test
      void setBoxedShortArray() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00});

        byteOps.setBoxedShortArray(bytes, 0, new Short[] {0x0102, 0x0304});

        assertEquals(0x01, byteOps.getByte(bytes, 0));
        assertEquals(0x02, byteOps.getByte(bytes, 1));
        assertEquals(0x03, byteOps.getByte(bytes, 2));
        assertEquals(0x04, byteOps.getByte(bytes, 3));
        assertEquals(0x0102, byteOps.getShort(bytes, 0));
        assertEquals(0x0304, byteOps.getShort(bytes, 2));
      }

      @Test
      void setBoxedIntArray() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

        byteOps.setBoxedIntArray(bytes, 0, new Integer[] {0x0102_0304, 0x0506_0708});

        assertEquals(0x01, byteOps.getByte(bytes, 0));
        assertEquals(0x02, byteOps.getByte(bytes, 1));
        assertEquals(0x03, byteOps.getByte(bytes, 2));
        assertEquals(0x04, byteOps.getByte(bytes, 3));
        assertEquals(0x05, byteOps.getByte(bytes, 4));
        assertEquals(0x06, byteOps.getByte(bytes, 5));
        assertEquals(0x07, byteOps.getByte(bytes, 6));
        assertEquals(0x08, byteOps.getByte(bytes, 7));
        assertEquals(0x0102_0304, byteOps.getInt(bytes, 0));
        assertEquals(0x0506_0708, byteOps.getInt(bytes, 4));
      }

      @Test
      void setBoxedLongArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
                });

        byteOps.setBoxedLongArray(
            bytes, 0, new Long[] {0x0102_0304_0506_0708L, 0x090A_0B0C_0D0E_0F10L});

        assertEquals(0x01, byteOps.getByte(bytes, 0));
        assertEquals(0x02, byteOps.getByte(bytes, 1));
        assertEquals(0x03, byteOps.getByte(bytes, 2));
        assertEquals(0x04, byteOps.getByte(bytes, 3));
        assertEquals(0x05, byteOps.getByte(bytes, 4));
        assertEquals(0x06, byteOps.getByte(bytes, 5));
        assertEquals(0x07, byteOps.getByte(bytes, 6));
        assertEquals(0x08, byteOps.getByte(bytes, 7));
        assertEquals(0x09, byteOps.getByte(bytes, 8));
        assertEquals(0x0A, byteOps.getByte(bytes, 9));
        assertEquals(0x0B, byteOps.getByte(bytes, 10));
        assertEquals(0x0C, byteOps.getByte(bytes, 11));
        assertEquals(0x0D, byteOps.getByte(bytes, 12));
        assertEquals(0x0E, byteOps.getByte(bytes, 13));
        assertEquals(0x0F, byteOps.getByte(bytes, 14));
        assertEquals(0x10, byteOps.getByte(bytes, 15));
        assertEquals(0x0102_0304_0506_0708L, byteOps.getLong(bytes, 0));
        assertEquals(0x090A_0B0C_0D0E_0F10L, byteOps.getLong(bytes, 8));
      }

      @Test
      void setBoxedFloatArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x00, 0x00, 0x00,
                  0x00, 0x00, 0x00, 0x00
                });

        byteOps.setBoxedFloatArray(
            bytes,
            0,
            new Float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x4000_0000)});

        assertEquals(0x3F, byteOps.getByte(bytes, 0));
        assertEquals((byte) 0x80, byteOps.getByte(bytes, 1));
        assertEquals(0x00, byteOps.getByte(bytes, 2));
        assertEquals(0x00, byteOps.getByte(bytes, 3));
        assertEquals(0x40, byteOps.getByte(bytes, 4));
        assertEquals(0x00, byteOps.getByte(bytes, 5));
        assertEquals(0x00, byteOps.getByte(bytes, 6));
        assertEquals(0x00, byteOps.getByte(bytes, 7));
        assertEquals(Float.intBitsToFloat(0x3F80_0000), byteOps.getFloat(bytes, 0));
        assertEquals(Float.intBitsToFloat(0x4000_0000), byteOps.getFloat(bytes, 4));
      }

      @Test
      void setBoxedDoubleArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
                });

        byteOps.setBoxedDoubleArray(
            bytes,
            0,
            new Double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x4000_0000_0000_0000L)
            });

        assertEquals(0x3F, byteOps.getByte(bytes, 0));
        assertEquals((byte) 0xF0, byteOps.getByte(bytes, 1));
        assertEquals(0x00, byteOps.getByte(bytes, 2));
        assertEquals(0x00, byteOps.getByte(bytes, 3));
        assertEquals(0x00, byteOps.getByte(bytes, 4));
        assertEquals(0x00, byteOps.getByte(bytes, 5));
        assertEquals(0x00, byteOps.getByte(bytes, 6));
        assertEquals(0x00, byteOps.getByte(bytes, 7));
        assertEquals(0x40, byteOps.getByte(bytes, 8));
        assertEquals(0x00, byteOps.getByte(bytes, 9));
        assertEquals(0x00, byteOps.getByte(bytes, 10));
        assertEquals(0x00, byteOps.getByte(bytes, 11));
        assertEquals(0x00, byteOps.getByte(bytes, 12));
        assertEquals(0x00, byteOps.getByte(bytes, 13));
        assertEquals(0x00, byteOps.getByte(bytes, 14));
        assertEquals(0x00, byteOps.getByte(bytes, 15));
        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), byteOps.getDouble(bytes, 0));
        assertEquals(Double.longBitsToDouble(0x4000_0000_0000_0000L), byteOps.getDouble(bytes, 8));
      }
    }
  }

  @Nested
  class BigEndianWordSwapped {

    private final ByteOps<T> byteOps = getSwappedByteOps(ByteOrder.BIG_ENDIAN);

    @Nested
    class ScalarGet {

      @Test
      void getInt() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03});

        int i = byteOps.getInt(bytes, 0);

        assertEquals(0x0203_0001, i);
      }

      @Test
      void getLong() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

        long l = byteOps.getLong(bytes, 0);

        assertEquals(0x0607_0405_0203_0001L, l);
      }

      @Test
      void getFloat() {
        T bytes = getBytes(new byte[] {0x3F, (byte) 0x80, 0x00, 0x00});

        float f = byteOps.getFloat(bytes, 0);

        assertEquals(Float.intBitsToFloat(0x0000_3F80), f);
      }

      @Test
      void getDouble() {
        T bytes = getBytes(new byte[] {0x3F, (byte) 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

        double d = byteOps.getDouble(bytes, 0);

        assertEquals(Double.longBitsToDouble(0x0000_0000_0000_3FF0L), d);
      }
    }

    @Nested
    class ScalarSet {

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
    class ArrayGet {

      @Test
      void getIntArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

        int[] is = byteOps.getIntArray(bytes, 0, 2);

        assertEquals(0x0203_0001, is[0]);
        assertEquals(0x0607_0405, is[1]);
        assertArrayEquals(new int[] {0x0203_0001, 0x0607_0405}, is);
      }

      @Test
      void getLongArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                  0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
                });

        long[] ls = byteOps.getLongArray(bytes, 0, 2);

        assertEquals(0x0607_0405_0203_0001L, ls[0]);
        assertEquals(0x0E0F_0C0D_0A0B_0809L, ls[1]);
        assertArrayEquals(new long[] {0x0607_0405_0203_0001L, 0x0E0F_0C0D_0A0B_0809L}, ls);
      }

      @Test
      void getFloatArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x3F, (byte) 0x80, 0x00, 0x00,
                  0x3F, (byte) 0x80, 0x00, 0x00
                });

        float[] fs = byteOps.getFloatArray(bytes, 0, 2);

        assertEquals(Float.intBitsToFloat(0x0000_3F80), fs[0]);
        assertEquals(Float.intBitsToFloat(0x0000_3F80), fs[1]);
        assertArrayEquals(
            new float[] {Float.intBitsToFloat(0x0000_3F80), Float.intBitsToFloat(0x0000_3F80)}, fs);
      }

      @Test
      void getDoubleArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x3F,
                  (byte) 0xF0,
                  0x00,
                  0x00,
                  0x00,
                  0x00,
                  0x00,
                  0x00,
                  0x40,
                  0x00,
                  0x00,
                  0x00,
                  0x00,
                  0x00,
                  0x00,
                  0x00
                });

        double[] ds = byteOps.getDoubleArray(bytes, 0, 2);

        assertEquals(Double.longBitsToDouble(0x0000_0000_0000_3FF0L), ds[0]);
        assertEquals(Double.longBitsToDouble(0x0000_0000_0000_4000L), ds[1]);
        assertArrayEquals(
            new double[] {
              Double.longBitsToDouble(0x0000_0000_0000_3FF0L),
              Double.longBitsToDouble(0x0000_0000_0000_4000L)
            },
            ds);
      }
    }

    @Nested
    class ArraySet {

      @Test
      void setIntArray() {
        T bytes = getBytes(new byte[8]);

        byteOps.setIntArray(bytes, 0, new int[] {0x0102_0304, 0x0506_0708});

        assertEquals(0x03, byteOps.getByte(bytes, 0));
        assertEquals(0x04, byteOps.getByte(bytes, 1));
        assertEquals(0x01, byteOps.getByte(bytes, 2));
        assertEquals(0x02, byteOps.getByte(bytes, 3));
        assertEquals(0x07, byteOps.getByte(bytes, 4));
        assertEquals(0x08, byteOps.getByte(bytes, 5));
        assertEquals(0x05, byteOps.getByte(bytes, 6));
        assertEquals(0x06, byteOps.getByte(bytes, 7));
        assertEquals(0x0102_0304, byteOps.getInt(bytes, 0));
        assertEquals(0x0506_0708, byteOps.getInt(bytes, 4));
      }

      @Test
      void setLongArray() {
        T bytes = getBytes(new byte[16]);

        byteOps.setLongArray(bytes, 0, new long[] {0x0102_0304_0506_0708L, 0x090A_0B0C_0D0E_0F10L});

        assertEquals(0x07, byteOps.getByte(bytes, 0));
        assertEquals(0x08, byteOps.getByte(bytes, 1));
        assertEquals(0x05, byteOps.getByte(bytes, 2));
        assertEquals(0x06, byteOps.getByte(bytes, 3));
        assertEquals(0x03, byteOps.getByte(bytes, 4));
        assertEquals(0x04, byteOps.getByte(bytes, 5));
        assertEquals(0x01, byteOps.getByte(bytes, 6));
        assertEquals(0x02, byteOps.getByte(bytes, 7));
        assertEquals(0x0F, byteOps.getByte(bytes, 8));
        assertEquals(0x10, byteOps.getByte(bytes, 9));
        assertEquals(0x0D, byteOps.getByte(bytes, 10));
        assertEquals(0x0E, byteOps.getByte(bytes, 11));
        assertEquals(0x0B, byteOps.getByte(bytes, 12));
        assertEquals(0x0C, byteOps.getByte(bytes, 13));
        assertEquals(0x09, byteOps.getByte(bytes, 14));
        assertEquals(0x0A, byteOps.getByte(bytes, 15));
        assertEquals(0x0102_0304_0506_0708L, byteOps.getLong(bytes, 0));
        assertEquals(0x090A_0B0C_0D0E_0F10L, byteOps.getLong(bytes, 8));
      }

      @Test
      void setFloatArray() {
        T bytes = getBytes(new byte[8]);

        byteOps.setFloatArray(
            bytes,
            0,
            new float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x4000_0000)});

        assertEquals(0x00, byteOps.getByte(bytes, 0));
        assertEquals(0x00, byteOps.getByte(bytes, 1));
        assertEquals(0x3F, byteOps.getByte(bytes, 2));
        assertEquals((byte) 0x80, byteOps.getByte(bytes, 3));
        assertEquals(0x00, byteOps.getByte(bytes, 4));
        assertEquals(0x00, byteOps.getByte(bytes, 5));
        assertEquals(0x40, byteOps.getByte(bytes, 6));
        assertEquals(0x00, byteOps.getByte(bytes, 7));
        assertEquals(Float.intBitsToFloat(0x3F80_0000), byteOps.getFloat(bytes, 0));
        assertEquals(Float.intBitsToFloat(0x4000_0000), byteOps.getFloat(bytes, 4));
      }

      @Test
      void setDoubleArray() {
        T bytes = getBytes(new byte[16]);

        byteOps.setDoubleArray(
            bytes,
            0,
            new double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x4000_0000_0000_0000L)
            });

        assertEquals(0x00, byteOps.getByte(bytes, 0));
        assertEquals(0x00, byteOps.getByte(bytes, 1));
        assertEquals(0x00, byteOps.getByte(bytes, 2));
        assertEquals(0x00, byteOps.getByte(bytes, 3));
        assertEquals(0x00, byteOps.getByte(bytes, 4));
        assertEquals(0x00, byteOps.getByte(bytes, 5));
        assertEquals(0x3F, byteOps.getByte(bytes, 6));
        assertEquals((byte) 0xF0, byteOps.getByte(bytes, 7));
        assertEquals(0x00, byteOps.getByte(bytes, 8));
        assertEquals(0x00, byteOps.getByte(bytes, 9));
        assertEquals(0x00, byteOps.getByte(bytes, 10));
        assertEquals(0x00, byteOps.getByte(bytes, 11));
        assertEquals(0x00, byteOps.getByte(bytes, 12));
        assertEquals(0x00, byteOps.getByte(bytes, 13));
        assertEquals(0x40, byteOps.getByte(bytes, 14));
        assertEquals(0x00, byteOps.getByte(bytes, 15));
        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), byteOps.getDouble(bytes, 0));
        assertEquals(Double.longBitsToDouble(0x4000_0000_0000_0000L), byteOps.getDouble(bytes, 8));
      }
    }
  }

  @Nested
  class LittleEndian {

    private final ByteOps<T> byteOps = getByteOps(ByteOrder.LITTLE_ENDIAN);

    @Nested
    class ScalarGet {

      @Test
      void getBoolean() {
        assertFalse(byteOps.getBoolean(getBytes(new byte[] {0x00}), 0));
        assertTrue(byteOps.getBoolean(getBytes(new byte[] {0x01}), 0));
      }

      @Test
      void getByte() {
        T bytes = getBytes(new byte[] {0x00});

        byte b = byteOps.getByte(bytes, 0);

        assertEquals(0x00, b);
      }

      @Test
      void getShort() {
        T bytes = getBytes(new byte[] {0x00, 0x01});

        short s = byteOps.getShort(bytes, 0);

        assertEquals(0x0100, s);
      }

      @Test
      void getInt() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03});

        int i = byteOps.getInt(bytes, 0);

        assertEquals(0x0302_0100, i);
      }

      @Test
      void getLong() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

        long l = byteOps.getLong(bytes, 0);

        assertEquals(0x0706_0504_0302_0100L, l);
      }

      @Test
      void getFloat() {
        T bytes = getBytes(new byte[] {0x00, 0x00, (byte) 0x80, 0x3F});

        float f = byteOps.getFloat(bytes, 0);

        assertEquals(Float.intBitsToFloat(0x3F80_0000), f);
      }

      @Test
      void getDouble() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xF0, 0x3F});

        double d = byteOps.getDouble(bytes, 0);

        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), d);
      }
    }

    @Nested
    class ScalarSet {

      @Test
      void setBoolean() {
        T bytes = getBytes(new byte[] {0x00});

        byteOps.setBoolean(bytes, 0, true);

        assertTrue(byteOps.getBoolean(bytes, 0));
      }

      @Test
      void setByte() {
        T bytes = getBytes(new byte[] {0x00});

        byteOps.setByte(bytes, 0, (byte) 0x01);

        assertEquals(0x01, byteOps.getByte(bytes, 0));
      }

      @Test
      void setShort() {
        T bytes = getBytes(new byte[] {0x00, 0x00});

        byteOps.setShort(bytes, 0, (short) 0x0102);

        assertEquals(0x02, byteOps.getByte(bytes, 0));
        assertEquals(0x01, byteOps.getByte(bytes, 1));
        assertEquals(0x0102, byteOps.getShort(bytes, 0));
      }

      @Test
      void setInt() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00});

        byteOps.setInt(bytes, 0, 0x0102_0304);

        assertEquals(0x04, byteOps.getByte(bytes, 0));
        assertEquals(0x03, byteOps.getByte(bytes, 1));
        assertEquals(0x02, byteOps.getByte(bytes, 2));
        assertEquals(0x01, byteOps.getByte(bytes, 3));
        assertEquals(0x0102_0304, byteOps.getInt(bytes, 0));
      }

      @Test
      void setLong() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

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
      void setFloat() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00});

        byteOps.setFloat(bytes, 0, Float.intBitsToFloat(0x3F80_0000));

        assertEquals(0x00, byteOps.getByte(bytes, 0));
        assertEquals(0x00, byteOps.getByte(bytes, 1));
        assertEquals((byte) 0x80, byteOps.getByte(bytes, 2));
        assertEquals(0x3F, byteOps.getByte(bytes, 3));
        assertEquals(Float.intBitsToFloat(0x3F80_0000), byteOps.getFloat(bytes, 0));
      }

      @Test
      void setDouble() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

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
    class ArrayGet {

      @Test
      void getShortArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03});

        short[] shorts = byteOps.getShortArray(bytes, 0, 2);

        assertEquals(0x0100, shorts[0]);
        assertEquals(0x0302, shorts[1]);
        assertArrayEquals(new short[] {0x0100, 0x0302}, shorts);
      }

      @Test
      void getIntArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

        int[] is = byteOps.getIntArray(bytes, 0, 2);

        assertEquals(0x0302_0100, is[0]);
        assertEquals(0x0706_0504, is[1]);
        assertArrayEquals(new int[] {0x0302_0100, 0x0706_0504}, is);
      }

      @Test
      void getLongArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                  0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
                });

        long[] ls = byteOps.getLongArray(bytes, 0, 2);

        assertEquals(0x0706_0504_0302_0100L, ls[0]);
        assertEquals(0x0F0E_0D0C_0B0A_0908L, ls[1]);
        assertArrayEquals(new long[] {0x0706_0504_0302_0100L, 0x0F0E_0D0C_0B0A_0908L}, ls);
      }

      @Test
      void getFloatArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x00, (byte) 0x80, 0x3F,
                  0x00, 0x00, (byte) 0x80, 0x3F
                });

        float[] fs = byteOps.getFloatArray(bytes, 0, 2);

        assertEquals(Float.intBitsToFloat(0x3F80_0000), fs[0]);
        assertEquals(Float.intBitsToFloat(0x3F80_0000), fs[1]);
        assertArrayEquals(
            new float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x3F80_0000)}, fs);
      }

      @Test
      void getDoubleArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xF0, 0x3F,
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xF0, 0x3F
                });

        double[] ds = byteOps.getDoubleArray(bytes, 0, 2);

        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), ds[0]);
        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), ds[1]);
        assertArrayEquals(
            new double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L)
            },
            ds);
      }

      @Test
      void getBoxedBooleanArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01});

        Boolean[] bs = byteOps.getBoxedBooleanArray(bytes, 0, 2);

        assertFalse(bs[0]);
        assertTrue(bs[1]);
        assertArrayEquals(new Boolean[] {false, true}, bs);
      }

      @Test
      void getBoxedByteArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01});

        Byte[] bs = byteOps.getBoxedByteArray(bytes, 0, 2);

        assertEquals((byte) 0x00, bs[0]);
        assertEquals((byte) 0x01, bs[1]);
        assertArrayEquals(new Byte[] {0x00, 0x01}, bs);
      }

      @Test
      void getBoxedShortArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03});

        Short[] ss = byteOps.getBoxedShortArray(bytes, 0, 2);

        assertEquals((short) 0x0100, ss[0]);
        assertEquals((short) 0x0302, ss[1]);
        assertArrayEquals(new Short[] {0x0100, 0x0302}, ss);
      }

      @Test
      void getBoxedIntArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

        Integer[] is = byteOps.getBoxedIntArray(bytes, 0, 2);

        assertEquals(0x0302_0100, is[0]);
        assertEquals(0x0706_0504, is[1]);
        assertArrayEquals(new Integer[] {0x0302_0100, 0x0706_0504}, is);
      }

      @Test
      void getBoxedLongArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                  0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
                });

        Long[] ls = byteOps.getBoxedLongArray(bytes, 0, 2);

        assertEquals(0x0706_0504_0302_0100L, ls[0]);
        assertEquals(0x0F0E_0D0C_0B0A_0908L, ls[1]);
        assertArrayEquals(new Long[] {0x0706_0504_0302_0100L, 0x0F0E_0D0C_0B0A_0908L}, ls);
      }

      @Test
      void getBoxedFloatArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x00, (byte) 0x80, 0x3F,
                  0x00, 0x00, (byte) 0x80, 0x3F
                });

        Float[] fs = byteOps.getBoxedFloatArray(bytes, 0, 2);

        assertEquals(Float.intBitsToFloat(0x3F80_0000), fs[0]);
        assertEquals(Float.intBitsToFloat(0x3F80_0000), fs[1]);
        assertArrayEquals(
            new Float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x3F80_0000)}, fs);
      }

      @Test
      void getBoxedDoubleArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xF0, 0x3F,
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xF0, 0x3F
                });

        Double[] ds = byteOps.getBoxedDoubleArray(bytes, 0, 2);

        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), ds[0]);
        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), ds[1]);
        assertArrayEquals(
            new Double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L)
            },
            ds);
      }
    }

    @Nested
    class ArraySet {

      @Test
      void setShortArray() {
        T bytes = getBytes(new byte[4]);

        byteOps.setShortArray(bytes, 0, new short[] {0x0102, 0x0304});

        assertEquals(0x02, byteOps.getByte(bytes, 0));
        assertEquals(0x01, byteOps.getByte(bytes, 1));
        assertEquals(0x04, byteOps.getByte(bytes, 2));
        assertEquals(0x03, byteOps.getByte(bytes, 3));
        assertEquals(0x0102, byteOps.getShort(bytes, 0));
        assertEquals(0x0304, byteOps.getShort(bytes, 2));
        assertArrayEquals(new short[] {0x0102, 0x0304}, byteOps.getShortArray(bytes, 0, 2));
      }

      @Test
      void setIntArray() {
        T bytes = getBytes(new byte[8]);

        byteOps.setIntArray(bytes, 0, new int[] {0x0102_0304, 0x0506_0708});

        assertEquals(0x04, byteOps.getByte(bytes, 0));
        assertEquals(0x03, byteOps.getByte(bytes, 1));
        assertEquals(0x02, byteOps.getByte(bytes, 2));
        assertEquals(0x01, byteOps.getByte(bytes, 3));
        assertEquals(0x08, byteOps.getByte(bytes, 4));
        assertEquals(0x07, byteOps.getByte(bytes, 5));
        assertEquals(0x06, byteOps.getByte(bytes, 6));
        assertEquals(0x05, byteOps.getByte(bytes, 7));
        assertEquals(0x0102_0304, byteOps.getInt(bytes, 0));
        assertEquals(0x0506_0708, byteOps.getInt(bytes, 4));
        assertArrayEquals(new int[] {0x0102_0304, 0x0506_0708}, byteOps.getIntArray(bytes, 0, 2));
      }

      @Test
      void setLongArray() {
        T bytes = getBytes(new byte[16]);

        byteOps.setLongArray(bytes, 0, new long[] {0x0102_0304_0506_0708L, 0x090A_0B0C_0D0E_0F10L});

        assertEquals(0x08, byteOps.getByte(bytes, 0));
        assertEquals(0x07, byteOps.getByte(bytes, 1));
        assertEquals(0x06, byteOps.getByte(bytes, 2));
        assertEquals(0x05, byteOps.getByte(bytes, 3));
        assertEquals(0x04, byteOps.getByte(bytes, 4));
        assertEquals(0x03, byteOps.getByte(bytes, 5));
        assertEquals(0x02, byteOps.getByte(bytes, 6));
        assertEquals(0x01, byteOps.getByte(bytes, 7));
        assertEquals(0x10, byteOps.getByte(bytes, 8));
        assertEquals(0x0F, byteOps.getByte(bytes, 9));
        assertEquals(0x0E, byteOps.getByte(bytes, 10));
        assertEquals(0x0D, byteOps.getByte(bytes, 11));
        assertEquals(0x0C, byteOps.getByte(bytes, 12));
        assertEquals(0x0B, byteOps.getByte(bytes, 13));
        assertEquals(0x0A, byteOps.getByte(bytes, 14));
        assertEquals(0x09, byteOps.getByte(bytes, 15));
        assertEquals(0x0102_0304_0506_0708L, byteOps.getLong(bytes, 0));
        assertEquals(0x090A_0B0C_0D0E_0F10L, byteOps.getLong(bytes, 8));
        assertArrayEquals(
            new long[] {0x0102_0304_0506_0708L, 0x090A_0B0C_0D0E_0F10L},
            byteOps.getLongArray(bytes, 0, 2));
      }

      @Test
      void setFloatArray() {
        T bytes = getBytes(new byte[8]);

        byteOps.setFloatArray(
            bytes,
            0,
            new float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x4000_0000)});

        assertEquals(0x00, byteOps.getByte(bytes, 0));
        assertEquals(0x00, byteOps.getByte(bytes, 1));
        assertEquals((byte) 0x80, byteOps.getByte(bytes, 2));
        assertEquals(0x3F, byteOps.getByte(bytes, 3));
        assertEquals(0x00, byteOps.getByte(bytes, 4));
        assertEquals(0x00, byteOps.getByte(bytes, 5));
        assertEquals(0x00, byteOps.getByte(bytes, 6));
        assertEquals(0x40, byteOps.getByte(bytes, 7));
        assertEquals(Float.intBitsToFloat(0x3F80_0000), byteOps.getFloat(bytes, 0));
        assertEquals(Float.intBitsToFloat(0x4000_0000), byteOps.getFloat(bytes, 4));
        assertArrayEquals(
            new float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x4000_0000)},
            byteOps.getFloatArray(bytes, 0, 2));
      }

      @Test
      void setDoubleArray() {
        T bytes = getBytes(new byte[16]);

        byteOps.setDoubleArray(
            bytes,
            0,
            new double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x4000_0000_0000_0000L)
            });

        assertEquals(0x00, byteOps.getByte(bytes, 0));
        assertEquals(0x00, byteOps.getByte(bytes, 1));
        assertEquals(0x00, byteOps.getByte(bytes, 2));
        assertEquals(0x00, byteOps.getByte(bytes, 3));
        assertEquals(0x00, byteOps.getByte(bytes, 4));
        assertEquals(0x00, byteOps.getByte(bytes, 5));
        assertEquals((byte) 0xF0, byteOps.getByte(bytes, 6));
        assertEquals(0x3F, byteOps.getByte(bytes, 7));
        assertEquals(0x00, byteOps.getByte(bytes, 8));
        assertEquals(0x00, byteOps.getByte(bytes, 9));
        assertEquals(0x00, byteOps.getByte(bytes, 10));
        assertEquals(0x00, byteOps.getByte(bytes, 11));
        assertEquals(0x00, byteOps.getByte(bytes, 12));
        assertEquals(0x00, byteOps.getByte(bytes, 13));
        assertEquals(0x00, byteOps.getByte(bytes, 14));
        assertEquals(0x40, byteOps.getByte(bytes, 15));
        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), byteOps.getDouble(bytes, 0));
        assertEquals(Double.longBitsToDouble(0x4000_0000_0000_0000L), byteOps.getDouble(bytes, 8));
        assertArrayEquals(
            new double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x4000_0000_0000_0000L)
            },
            byteOps.getDoubleArray(bytes, 0, 2));
      }

      @Test
      void setBoxedBooleanArray() {
        T bytes = getBytes(new byte[] {0x00, 0x00});

        byteOps.setBoxedBooleanArray(bytes, 0, new Boolean[] {true, true});

        assertTrue(byteOps.getBoolean(bytes, 0));
        assertTrue(byteOps.getBoolean(bytes, 1));
      }

      @Test
      void setBoxedByteArray() {
        T bytes = getBytes(new byte[] {0x00, 0x00});

        byteOps.setBoxedByteArray(bytes, 0, new Byte[] {0x01, 0x02});

        assertEquals(0x01, byteOps.getByte(bytes, 0));
        assertEquals(0x02, byteOps.getByte(bytes, 1));
      }

      @Test
      void setBoxedShortArray() {
        T bytes = getBytes(new byte[4]);

        byteOps.setBoxedShortArray(bytes, 0, new Short[] {0x0102, 0x0304});

        assertEquals(0x02, byteOps.getByte(bytes, 0));
        assertEquals(0x01, byteOps.getByte(bytes, 1));
        assertEquals(0x04, byteOps.getByte(bytes, 2));
        assertEquals(0x03, byteOps.getByte(bytes, 3));
        assertEquals(0x0102, byteOps.getShort(bytes, 0));
        assertEquals(0x0304, byteOps.getShort(bytes, 2));
        assertArrayEquals(new Short[] {0x0102, 0x0304}, byteOps.getBoxedShortArray(bytes, 0, 2));
      }

      @Test
      void setBoxedIntArray() {
        T bytes = getBytes(new byte[8]);

        byteOps.setBoxedIntArray(bytes, 0, new Integer[] {0x0102_0304, 0x0506_0708});

        assertEquals(0x04, byteOps.getByte(bytes, 0));
        assertEquals(0x03, byteOps.getByte(bytes, 1));
        assertEquals(0x02, byteOps.getByte(bytes, 2));
        assertEquals(0x01, byteOps.getByte(bytes, 3));
        assertEquals(0x08, byteOps.getByte(bytes, 4));
        assertEquals(0x07, byteOps.getByte(bytes, 5));
        assertEquals(0x06, byteOps.getByte(bytes, 6));
        assertEquals(0x05, byteOps.getByte(bytes, 7));
        assertEquals(0x0102_0304, byteOps.getInt(bytes, 0));
        assertEquals(0x0506_0708, byteOps.getInt(bytes, 4));
        assertArrayEquals(
            new Integer[] {0x0102_0304, 0x0506_0708}, byteOps.getBoxedIntArray(bytes, 0, 2));
      }

      @Test
      void setBoxedLongArray() {
        T bytes = getBytes(new byte[16]);

        byteOps.setBoxedLongArray(
            bytes, 0, new Long[] {0x0102_0304_0506_0708L, 0x090A_0B0C_0D0E_0F10L});

        assertEquals(0x08, byteOps.getByte(bytes, 0));
        assertEquals(0x07, byteOps.getByte(bytes, 1));
        assertEquals(0x06, byteOps.getByte(bytes, 2));
        assertEquals(0x05, byteOps.getByte(bytes, 3));
        assertEquals(0x04, byteOps.getByte(bytes, 4));
        assertEquals(0x03, byteOps.getByte(bytes, 5));
        assertEquals(0x02, byteOps.getByte(bytes, 6));
        assertEquals(0x01, byteOps.getByte(bytes, 7));
        assertEquals(0x10, byteOps.getByte(bytes, 8));
        assertEquals(0x0F, byteOps.getByte(bytes, 9));
        assertEquals(0x0E, byteOps.getByte(bytes, 10));
        assertEquals(0x0D, byteOps.getByte(bytes, 11));
        assertEquals(0x0C, byteOps.getByte(bytes, 12));
        assertEquals(0x0B, byteOps.getByte(bytes, 13));
        assertEquals(0x0A, byteOps.getByte(bytes, 14));
        assertEquals(0x09, byteOps.getByte(bytes, 15));
        assertEquals(0x0102_0304_0506_0708L, byteOps.getLong(bytes, 0));
        assertEquals(0x090A_0B0C_0D0E_0F10L, byteOps.getLong(bytes, 8));
        assertArrayEquals(
            new Long[] {0x0102_0304_0506_0708L, 0x090A_0B0C_0D0E_0F10L},
            byteOps.getBoxedLongArray(bytes, 0, 2));
      }

      @Test
      void setBoxedFloatArray() {
        T bytes = getBytes(new byte[8]);

        byteOps.setBoxedFloatArray(
            bytes,
            0,
            new Float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x4000_0000)});

        assertEquals(0x00, byteOps.getByte(bytes, 0));
        assertEquals(0x00, byteOps.getByte(bytes, 1));
        assertEquals((byte) 0x80, byteOps.getByte(bytes, 2));
        assertEquals(0x3F, byteOps.getByte(bytes, 3));
        assertEquals(0x00, byteOps.getByte(bytes, 4));
        assertEquals(0x00, byteOps.getByte(bytes, 5));
        assertEquals(0x00, byteOps.getByte(bytes, 6));
        assertEquals(0x40, byteOps.getByte(bytes, 7));
        assertEquals(Float.intBitsToFloat(0x3F80_0000), byteOps.getFloat(bytes, 0));
        assertEquals(Float.intBitsToFloat(0x4000_0000), byteOps.getFloat(bytes, 4));
        assertArrayEquals(
            new Float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x4000_0000)},
            byteOps.getBoxedFloatArray(bytes, 0, 2));
      }

      @Test
      void setBoxedDoubleArray() {
        T bytes = getBytes(new byte[16]);

        byteOps.setBoxedDoubleArray(
            bytes,
            0,
            new Double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x4000_0000_0000_0000L)
            });

        assertEquals(0x00, byteOps.getByte(bytes, 0));
        assertEquals(0x00, byteOps.getByte(bytes, 1));
        assertEquals(0x00, byteOps.getByte(bytes, 2));
        assertEquals(0x00, byteOps.getByte(bytes, 3));
        assertEquals(0x00, byteOps.getByte(bytes, 4));
        assertEquals(0x00, byteOps.getByte(bytes, 5));
        assertEquals((byte) 0xF0, byteOps.getByte(bytes, 6));
        assertEquals(0x3F, byteOps.getByte(bytes, 7));
        assertEquals(0x00, byteOps.getByte(bytes, 8));
        assertEquals(0x00, byteOps.getByte(bytes, 9));
        assertEquals(0x00, byteOps.getByte(bytes, 10));
        assertEquals(0x00, byteOps.getByte(bytes, 11));
        assertEquals(0x00, byteOps.getByte(bytes, 12));
        assertEquals(0x00, byteOps.getByte(bytes, 13));
        assertEquals(0x00, byteOps.getByte(bytes, 14));
        assertEquals(0x40, byteOps.getByte(bytes, 15));
        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), byteOps.getDouble(bytes, 0));
        assertEquals(Double.longBitsToDouble(0x4000_0000_0000_0000L), byteOps.getDouble(bytes, 8));
        assertArrayEquals(
            new Double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x4000_0000_0000_0000L)
            },
            byteOps.getBoxedDoubleArray(bytes, 0, 2));
      }
    }
  }

  @Nested
  class LittleEndianWordSwapped {

    private final ByteOps<T> byteOps = getSwappedByteOps(ByteOrder.LITTLE_ENDIAN);

    @Nested
    class ScalarGet {

      @Test
      void getInt() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03});

        int i = byteOps.getInt(bytes, 0);

        assertEquals(0x0100_0302, i);
      }

      @Test
      void getLong() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

        long l = byteOps.getLong(bytes, 0);

        assertEquals(0x0100_0302_0504_0706L, l);
      }

      @Test
      void getFloat() {
        T bytes = getBytes(new byte[] {0x00, 0x00, (byte) 0x80, 0x3F});

        float f = byteOps.getFloat(bytes, 0);

        assertEquals(Float.intBitsToFloat(0x0000_3F80), f);
      }

      @Test
      void getDouble() {
        T bytes = getBytes(new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xF0, 0x3F});

        double d = byteOps.getDouble(bytes, 0);

        assertEquals(Double.longBitsToDouble(0x0000_0000_0000_3FF0L), d);
      }
    }

    @Nested
    class ScalarSet {

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

    @Nested
    class ArrayGet {

      @Test
      void getIntArray() {
        T bytes = getBytes(new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07});

        int[] is = byteOps.getIntArray(bytes, 0, 2);

        assertEquals(0x0100_0302, is[0]);
        assertEquals(0x0504_0706, is[1]);
        assertArrayEquals(new int[] {0x0100_0302, 0x0504_0706}, is);
      }

      @Test
      void getLongArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                  0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
                });

        long[] ls = byteOps.getLongArray(bytes, 0, 2);

        assertEquals(0x0100_0302_0504_0706L, ls[0]);
        assertEquals(0x0908_0B0A_0D0C_0F0EL, ls[1]);
        assertArrayEquals(new long[] {0x0100_0302_0504_0706L, 0x0908_0B0A_0D0C_0F0EL}, ls);
      }

      @Test
      void getFloatArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x00, (byte) 0x80, 0x3F,
                  0x00, 0x00, (byte) 0x80, 0x3F
                });

        float[] fs = byteOps.getFloatArray(bytes, 0, 2);

        assertEquals(Float.intBitsToFloat(0x0000_3F80), fs[0]);
        assertEquals(Float.intBitsToFloat(0x0000_3F80), fs[1]);
        assertArrayEquals(
            new float[] {Float.intBitsToFloat(0x0000_3F80), Float.intBitsToFloat(0x0000_3F80)}, fs);
      }

      @Test
      void getDoubleArray() {
        T bytes =
            getBytes(
                new byte[] {
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xF0, 0x3F,
                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xF0, 0x3F
                });

        double[] ds = byteOps.getDoubleArray(bytes, 0, 2);

        assertEquals(Double.longBitsToDouble(0x0000_0000_0000_3FF0L), ds[0]);
        assertEquals(Double.longBitsToDouble(0x0000_0000_0000_3FF0L), ds[1]);
        assertArrayEquals(
            new double[] {
              Double.longBitsToDouble(0x0000_0000_0000_3FF0L),
              Double.longBitsToDouble(0x0000_0000_0000_3FF0L)
            },
            ds);
      }
    }

    @Nested
    class ArraySet {

      @Test
      void setIntArray() {
        T bytes = getBytes(new byte[8]);

        byteOps.setIntArray(bytes, 0, new int[] {0x0102_0304, 0x0506_0708});

        assertEquals(0x02, byteOps.getByte(bytes, 0));
        assertEquals(0x01, byteOps.getByte(bytes, 1));
        assertEquals(0x04, byteOps.getByte(bytes, 2));
        assertEquals(0x03, byteOps.getByte(bytes, 3));
        assertEquals(0x06, byteOps.getByte(bytes, 4));
        assertEquals(0x05, byteOps.getByte(bytes, 5));
        assertEquals(0x08, byteOps.getByte(bytes, 6));
        assertEquals(0x07, byteOps.getByte(bytes, 7));
        assertEquals(0x0102_0304, byteOps.getInt(bytes, 0));
        assertEquals(0x0506_0708, byteOps.getInt(bytes, 4));
        assertArrayEquals(new int[] {0x0102_0304, 0x0506_0708}, byteOps.getIntArray(bytes, 0, 2));
      }

      @Test
      void setLongArray() {
        T bytes = getBytes(new byte[16]);

        byteOps.setLongArray(bytes, 0, new long[] {0x0102_0304_0506_0708L, 0x090A_0B0C_0D0E_0F10L});

        assertEquals(0x02, byteOps.getByte(bytes, 0));
        assertEquals(0x01, byteOps.getByte(bytes, 1));
        assertEquals(0x04, byteOps.getByte(bytes, 2));
        assertEquals(0x03, byteOps.getByte(bytes, 3));
        assertEquals(0x06, byteOps.getByte(bytes, 4));
        assertEquals(0x05, byteOps.getByte(bytes, 5));
        assertEquals(0x08, byteOps.getByte(bytes, 6));
        assertEquals(0x07, byteOps.getByte(bytes, 7));
        assertEquals(0x0A, byteOps.getByte(bytes, 8));
        assertEquals(0x09, byteOps.getByte(bytes, 9));
        assertEquals(0x0C, byteOps.getByte(bytes, 10));
        assertEquals(0x0B, byteOps.getByte(bytes, 11));
        assertEquals(0x0E, byteOps.getByte(bytes, 12));
        assertEquals(0x0D, byteOps.getByte(bytes, 13));
        assertEquals(0x10, byteOps.getByte(bytes, 14));
        assertEquals(0x0F, byteOps.getByte(bytes, 15));
        assertEquals(0x0102_0304_0506_0708L, byteOps.getLong(bytes, 0));
        assertEquals(0x090A_0B0C_0D0E_0F10L, byteOps.getLong(bytes, 8));
        assertArrayEquals(
            new long[] {0x0102_0304_0506_0708L, 0x090A_0B0C_0D0E_0F10L},
            byteOps.getLongArray(bytes, 0, 2));
      }

      @Test
      void setFloatArray() {
        T bytes = getBytes(new byte[8]);

        byteOps.setFloatArray(
            bytes,
            0,
            new float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x4000_0000)});

        assertEquals((byte) 0x80, byteOps.getByte(bytes, 0));
        assertEquals(0x3F, byteOps.getByte(bytes, 1));
        assertEquals(0x00, byteOps.getByte(bytes, 2));
        assertEquals(0x00, byteOps.getByte(bytes, 3));
        assertEquals(0x00, byteOps.getByte(bytes, 4));
        assertEquals(0x40, byteOps.getByte(bytes, 5));
        assertEquals(0x00, byteOps.getByte(bytes, 6));
        assertEquals(0x00, byteOps.getByte(bytes, 7));
        assertEquals(Float.intBitsToFloat(0x3F80_0000), byteOps.getFloat(bytes, 0));
        assertEquals(Float.intBitsToFloat(0x4000_0000), byteOps.getFloat(bytes, 4));
        assertArrayEquals(
            new float[] {Float.intBitsToFloat(0x3F80_0000), Float.intBitsToFloat(0x4000_0000)},
            byteOps.getFloatArray(bytes, 0, 2));
      }

      @Test
      void setDoubleArray() {
        T bytes = getBytes(new byte[16]);

        byteOps.setDoubleArray(
            bytes,
            0,
            new double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x4000_0000_0000_0000L)
            });

        assertEquals((byte) 0xF0, byteOps.getByte(bytes, 0));
        assertEquals(0x3F, byteOps.getByte(bytes, 1));
        assertEquals(0x00, byteOps.getByte(bytes, 2));
        assertEquals(0x00, byteOps.getByte(bytes, 3));
        assertEquals(0x00, byteOps.getByte(bytes, 4));
        assertEquals(0x00, byteOps.getByte(bytes, 5));
        assertEquals(0x00, byteOps.getByte(bytes, 6));
        assertEquals(0x00, byteOps.getByte(bytes, 7));
        assertEquals(0x00, byteOps.getByte(bytes, 8));
        assertEquals(0x40, byteOps.getByte(bytes, 9));
        assertEquals(0x00, byteOps.getByte(bytes, 10));
        assertEquals(0x00, byteOps.getByte(bytes, 11));
        assertEquals(0x00, byteOps.getByte(bytes, 12));
        assertEquals(0x00, byteOps.getByte(bytes, 13));
        assertEquals(0x00, byteOps.getByte(bytes, 14));
        assertEquals(0x00, byteOps.getByte(bytes, 15));
        assertEquals(Double.longBitsToDouble(0x3FF0_0000_0000_0000L), byteOps.getDouble(bytes, 0));
        assertEquals(Double.longBitsToDouble(0x4000_0000_0000_0000L), byteOps.getDouble(bytes, 8));
        assertArrayEquals(
            new double[] {
              Double.longBitsToDouble(0x3FF0_0000_0000_0000L),
              Double.longBitsToDouble(0x4000_0000_0000_0000L)
            },
            byteOps.getDoubleArray(bytes, 0, 2));
      }
    }
  }
}
