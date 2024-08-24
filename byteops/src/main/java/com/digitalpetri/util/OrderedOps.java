package com.digitalpetri.util;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Operations that span multiple bytes/words that need to be aware of the underlying byte/word
 * order.
 */
public interface OrderedOps {

  /**
   * Get a short value using the given {@code getByte} function, starting at {@code index}.
   *
   * @param getByte a function that takes an index and returns a byte value.
   * @param index the index to start at.
   * @return the assembled short value.
   */
  short getShort(Function<Integer, Byte> getByte, int index);

  /**
   * Get an int value using the given {@code getByte} function, starting at {@code index}.
   *
   * @param getByte a function that takes an index and returns a byte value.
   * @param index the index to start at.
   * @return the assembled int value.
   */
  int getInt(Function<Integer, Byte> getByte, int index);

  /**
   * Get a long value using the given {@code getByte} function, starting at {@code index}.
   *
   * @param getByte a function that takes an index and returns a byte value.
   * @param index the index to start at.
   * @return the assembled long value.
   */
  long getLong(Function<Integer, Byte> getByte, int index);

  /**
   * Set the bytes of a short value using the given {@code setByte} function, starting
   * {@code index}.
   *
   * @param setByte a function that takes an index and a byte value to set.
   * @param index the index to start at.
   * @param value the short value to set.
   */
  void setShort(BiConsumer<Integer, Byte> setByte, int index, short value);

  /**
   * Set the bytes of an int value using the given {@code setByte} function, starting at
   * {@code index}.
   *
   * @param setByte a function that takes an index and a byte value to set.
   * @param index the index to start at.
   * @param value the int value to set.
   */
  void setInt(BiConsumer<Integer, Byte> setByte, int index, int value);

  /**
   * Set the bytes of a long value using the given {@code setByte} function, starting at
   * {@code index}.
   *
   * @param setByte a function that takes an index and a byte value to set.
   * @param index the index to start at.
   * @param value the long value to set.
   */
  void setLong(BiConsumer<Integer, Byte> setByte, int index, long value);

  /**
   * Operations that assume big-endian byte order and high-low word order.
   *
   * <pre>
   *  2-byte order: 0,1
   *  4-byte order: 0,1 | 2,3
   *  8-byte order: 0,1 | 2,3 | 4,5 | 6,7
   * </pre>
   */
  final class BigEndianOps implements OrderedOps {

    @Override
    public short getShort(Function<Integer, Byte> getByte, int index) {
      int b0 = getByte.apply(index) & 0xFF;
      int b1 = getByte.apply(index + 1) & 0xFF;
      return (short) (b0 << 8 | b1);
    }

    @Override
    public int getInt(Function<Integer, Byte> getByte, int index) {
      int b0 = getByte.apply(index) & 0xFF;
      int b1 = getByte.apply(index + 1) & 0xFF;
      int b2 = getByte.apply(index + 2) & 0xFF;
      int b3 = getByte.apply(index + 3) & 0xFF;
      return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }

    @Override
    public long getLong(Function<Integer, Byte> getByte, int index) {
      long b0 = getByte.apply(index) & 0xFF;
      long b1 = getByte.apply(index + 1) & 0xFF;
      long b2 = getByte.apply(index + 2) & 0xFF;
      long b3 = getByte.apply(index + 3) & 0xFF;
      long b4 = getByte.apply(index + 4) & 0xFF;
      long b5 = getByte.apply(index + 5) & 0xFF;
      long b6 = getByte.apply(index + 6) & 0xFF;
      long b7 = getByte.apply(index + 7) & 0xFF;
      return (b0 << 56) | (b1 << 48) | (b2 << 40) | (b3 << 32)
          | (b4 << 24) | (b5 << 16) | (b6 << 8) | b7;
    }

    @Override
    public void setShort(BiConsumer<Integer, Byte> setByte, int index, short value) {
      setByte.accept(index, (byte) (value >> 8 & 0xFF));
      setByte.accept(index + 1, (byte) (value & 0xFF));
    }

    @Override
    public void setInt(BiConsumer<Integer, Byte> setByte, int index, int value) {
      setByte.accept(index, (byte) (value >> 24 & 0xFF));
      setByte.accept(index + 1, (byte) (value >> 16 & 0xFF));
      setByte.accept(index + 2, (byte) (value >> 8 & 0xFF));
      setByte.accept(index + 3, (byte) (value & 0xFF));
    }

    @Override
    public void setLong(BiConsumer<Integer, Byte> setByte, int index, long value) {
      setByte.accept(index, (byte) (value >> 56 & 0xFF));
      setByte.accept(index + 1, (byte) (value >> 48 & 0xFF));
      setByte.accept(index + 2, (byte) (value >> 40 & 0xFF));
      setByte.accept(index + 3, (byte) (value >> 32 & 0xFF));
      setByte.accept(index + 4, (byte) (value >> 24 & 0xFF));
      setByte.accept(index + 5, (byte) (value >> 16 & 0xFF));
      setByte.accept(index + 6, (byte) (value >> 8 & 0xFF));
      setByte.accept(index + 7, (byte) (value & 0xFF));
    }

  }

  /**
   * Operations that assume little-endian byte order and high-low word order.
   *
   * <pre>
   *  2-byte order: 1,0
   *  4-byte order: 3,2 | 1,0
   *  8-byte order: 7,6 | 5,4 | 3,2 | 1,0
   * </pre>
   */
  final class LittleEndianOps implements OrderedOps {

    @Override
    public short getShort(Function<Integer, Byte> getByte, int index) {
      int b0 = getByte.apply(index) & 0xFF;
      int b1 = getByte.apply(index + 1) & 0xFF;
      return (short) (b1 << 8 | b0);
    }

    @Override
    public int getInt(Function<Integer, Byte> getByte, int index) {
      int b0 = getByte.apply(index) & 0xFF;
      int b1 = getByte.apply(index + 1) & 0xFF;
      int b2 = getByte.apply(index + 2) & 0xFF;
      int b3 = getByte.apply(index + 3) & 0xFF;
      return (b3 << 24) | (b2 << 16) | (b1 << 8) | b0;
    }

    @Override
    public long getLong(Function<Integer, Byte> getByte, int index) {
      long b0 = getByte.apply(index) & 0xFF;
      long b1 = getByte.apply(index + 1) & 0xFF;
      long b2 = getByte.apply(index + 2) & 0xFF;
      long b3 = getByte.apply(index + 3) & 0xFF;
      long b4 = getByte.apply(index + 4) & 0xFF;
      long b5 = getByte.apply(index + 5) & 0xFF;
      long b6 = getByte.apply(index + 6) & 0xFF;
      long b7 = getByte.apply(index + 7) & 0xFF;
      return (b7 << 56) | (b6 << 48) | (b5 << 40) | (b4 << 32)
          | (b3 << 24) | (b2 << 16) | (b1 << 8) | b0;
    }

    @Override
    public void setShort(BiConsumer<Integer, Byte> setByte, int index, short value) {
      setByte.accept(index, (byte) (value & 0xFF));
      setByte.accept(index + 1, (byte) (value >> 8 & 0xFF));
    }

    @Override
    public void setInt(BiConsumer<Integer, Byte> setByte, int index, int value) {
      setByte.accept(index, (byte) (value & 0xFF));
      setByte.accept(index + 1, (byte) (value >> 8 & 0xFF));
      setByte.accept(index + 2, (byte) (value >> 16 & 0xFF));
      setByte.accept(index + 3, (byte) (value >> 24 & 0xFF));
    }

    @Override
    public void setLong(BiConsumer<Integer, Byte> setByte, int index, long value) {
      setByte.accept(index, (byte) (value & 0xFF));
      setByte.accept(index + 1, (byte) (value >> 8 & 0xFF));
      setByte.accept(index + 2, (byte) (value >> 16 & 0xFF));
      setByte.accept(index + 3, (byte) (value >> 24 & 0xFF));
      setByte.accept(index + 4, (byte) (value >> 32 & 0xFF));
      setByte.accept(index + 5, (byte) (value >> 40 & 0xFF));
      setByte.accept(index + 6, (byte) (value >> 48 & 0xFF));
      setByte.accept(index + 7, (byte) (value >> 56 & 0xFF));
    }

  }

  /**
   * Operations that assume big-endian byte order and low-high word order.
   *
   * <pre>
   *   2-byte order: 0,1
   *   4-byte order: 2,3 | 0,1
   *   8-byte order: 6,7 | 4,5 | 2,3 | 0,1
   * </pre>
   */
  final class BigEndianLowHighOps implements OrderedOps {

    @Override
    public short getShort(Function<Integer, Byte> getByte, int index) {
      int b0 = getByte.apply(index) & 0xFF;
      int b1 = getByte.apply(index + 1) & 0xFF;
      return (short) (b0 << 8 | b1);
    }

    @Override
    public int getInt(Function<Integer, Byte> getByte, int index) {
      int b0 = getByte.apply(index) & 0xFF;
      int b1 = getByte.apply(index + 1) & 0xFF;
      int b2 = getByte.apply(index + 2) & 0xFF;
      int b3 = getByte.apply(index + 3) & 0xFF;
      return (b2 << 24) | (b3 << 16) | (b0 << 8) | b1;
    }

    @Override
    public long getLong(Function<Integer, Byte> getByte, int index) {
      long b0 = getByte.apply(index) & 0xFF;
      long b1 = getByte.apply(index + 1) & 0xFF;
      long b2 = getByte.apply(index + 2) & 0xFF;
      long b3 = getByte.apply(index + 3) & 0xFF;
      long b4 = getByte.apply(index + 4) & 0xFF;
      long b5 = getByte.apply(index + 5) & 0xFF;
      long b6 = getByte.apply(index + 6) & 0xFF;
      long b7 = getByte.apply(index + 7) & 0xFF;
      return (b6 << 56) | (b7 << 48) | (b4 << 40) | (b5 << 32)
          | (b2 << 24) | (b3 << 16) | (b0 << 8) | b1;
    }

    @Override
    public void setShort(BiConsumer<Integer, Byte> setByte, int index, short value) {
      setByte.accept(index, (byte) (value >> 8 & 0xFF));
      setByte.accept(index + 1, (byte) (value & 0xFF));
    }

    @Override
    public void setInt(BiConsumer<Integer, Byte> setByte, int index, int value) {
      setByte.accept(index, (byte) (value >> 8 & 0xFF));
      setByte.accept(index + 1, (byte) (value & 0xFF));
      setByte.accept(index + 2, (byte) (value >> 24 & 0xFF));
      setByte.accept(index + 3, (byte) (value >> 16 & 0xFF));
    }

    @Override
    public void setLong(BiConsumer<Integer, Byte> setByte, int index, long value) {
      setByte.accept(index, (byte) (value >> 8 & 0xFF));
      setByte.accept(index + 1, (byte) (value & 0xFF));
      setByte.accept(index + 2, (byte) (value >> 24 & 0xFF));
      setByte.accept(index + 3, (byte) (value >> 16 & 0xFF));
      setByte.accept(index + 4, (byte) (value >> 40 & 0xFF));
      setByte.accept(index + 5, (byte) (value >> 32 & 0xFF));
      setByte.accept(index + 6, (byte) (value >> 56 & 0xFF));
      setByte.accept(index + 7, (byte) (value >> 48 & 0xFF));
    }

  }

  /**
   * Operations that assume little-endian byte order and low-high word order.
   *
   * <pre>
   *   2-byte order: 1,0
   *   4-byte order: 1,0 | 3,2
   *   8-byte order: 1,0 | 3,2 | 5,4 | 7,6
   * </pre>
   */
  final class LittleEndianLowHighOps implements OrderedOps {

    @Override
    public short getShort(Function<Integer, Byte> getByte, int index) {
      int b0 = getByte.apply(index) & 0xFF;
      int b1 = getByte.apply(index + 1) & 0xFF;
      return (short) (b1 << 8 | b0);
    }

    @Override
    public int getInt(Function<Integer, Byte> getByte, int index) {
      int b0 = getByte.apply(index) & 0xFF;
      int b1 = getByte.apply(index + 1) & 0xFF;
      int b2 = getByte.apply(index + 2) & 0xFF;
      int b3 = getByte.apply(index + 3) & 0xFF;
      return (b1 << 24) | (b0 << 16) | (b3 << 8) | b2;
    }

    @Override
    public long getLong(Function<Integer, Byte> getByte, int index) {
      long b0 = getByte.apply(index) & 0xFF;
      long b1 = getByte.apply(index + 1) & 0xFF;
      long b2 = getByte.apply(index + 2) & 0xFF;
      long b3 = getByte.apply(index + 3) & 0xFF;
      long b4 = getByte.apply(index + 4) & 0xFF;
      long b5 = getByte.apply(index + 5) & 0xFF;
      long b6 = getByte.apply(index + 6) & 0xFF;
      long b7 = getByte.apply(index + 7) & 0xFF;
      return (b1 << 56) | (b0 << 48) | (b3 << 40) | (b2 << 32)
          | (b5 << 24) | (b4 << 16) | (b7 << 8) | b6;
    }

    @Override
    public void setShort(BiConsumer<Integer, Byte> setByte, int index, short value) {
      setByte.accept(index, (byte) (value & 0xFF));
      setByte.accept(index + 1, (byte) (value >> 8 & 0xFF));
    }

    @Override
    public void setInt(BiConsumer<Integer, Byte> setByte, int index, int value) {
      setByte.accept(index, (byte) (value >> 16 & 0xFF));
      setByte.accept(index + 1, (byte) (value >> 24 & 0xFF));
      setByte.accept(index + 2, (byte) (value & 0xFF));
      setByte.accept(index + 3, (byte) (value >> 8 & 0xFF));
    }

    @Override
    public void setLong(BiConsumer<Integer, Byte> setByte, int index, long value) {
      setByte.accept(index, (byte) (value >> 48 & 0xFF));
      setByte.accept(index + 1, (byte) (value >> 56 & 0xFF));
      setByte.accept(index + 2, (byte) (value >> 32 & 0xFF));
      setByte.accept(index + 3, (byte) (value >> 40 & 0xFF));
      setByte.accept(index + 4, (byte) (value >> 16 & 0xFF));
      setByte.accept(index + 5, (byte) (value >> 24 & 0xFF));
      setByte.accept(index + 6, (byte) (value & 0xFF));
      setByte.accept(index + 7, (byte) (value >> 8 & 0xFF));
    }

  }

}
