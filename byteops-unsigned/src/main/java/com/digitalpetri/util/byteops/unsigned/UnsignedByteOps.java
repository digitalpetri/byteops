package com.digitalpetri.util.byteops.unsigned;

import com.digitalpetri.util.byteops.ByteOps;
import org.joou.UByte;
import org.joou.UInteger;
import org.joou.ULong;
import org.joou.UShort;

/**
 * Additional operations for working with unsigned numbers.
 *
 * @param <T> the type of bytes.
 */
public final class UnsignedByteOps<T> implements ByteOps<T> {

  /**
   * Create a new {@link UnsignedByteOps} derived from the given {@link ByteOps}.
   *
   * @param ops the {@link ByteOps} to derive from.
   * @param <T> the type of bytes.
   * @return a new {@link UnsignedByteOps}.
   */
  public static <T> UnsignedByteOps<T> of(ByteOps<T> ops) {
    return new UnsignedByteOps<>(ops);
  }

  private final ByteOps<T> delegate;

  public UnsignedByteOps(ByteOps<T> delegate) {
    this.delegate = delegate;
  }

  /**
   * Get the {@link UByte} value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @return the {@link UByte} value at the given {@code index}.
   */
  public UByte getUByte(T bytes, int index) {
    return UByte.valueOf(getByte(bytes, index));
  }

  /**
   * Get the {@link UShort} value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @return the {@link UShort} value at the given {@code index}.
   */
  public UShort getUShort(T bytes, int index) {
    return UShort.valueOf(getShort(bytes, index));
  }

  /**
   * Get the {@link UInteger} value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @return the {@link UInteger} value at the given {@code index}.
   */
  public UInteger getUInt(T bytes, int index) {
    return UInteger.valueOf(getInt(bytes, index));
  }

  /**
   * Get the {@link ULong} value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @return the {@link ULong} value at the given {@code index}.
   */
  public ULong getULong(T bytes, int index) {
    return ULong.valueOf(getLong(bytes, index));
  }

  /**
   * Get the {@link UByte} array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @param length the length of the array.
   * @return the UByte array at the given {@code index}.
   */
  public UByte[] getUByteArray(T bytes, int index, int length) {
    byte[] byteArray = delegate.getByteArray(bytes, index, length);
    UByte[] uByteArray = new UByte[byteArray.length];
    for (int i = 0; i < byteArray.length; i++) {
      uByteArray[i] = UByte.valueOf(byteArray[i]);
    }
    return uByteArray;
  }

  /**
   * Get the {@link UShort} array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @param length the length of the array.
   * @return the UShort array at the given {@code index}.
   */
  public UShort[] getUShortArray(T bytes, int index, int length) {
    short[] shortArray = delegate.getShortArray(bytes, index, length);
    UShort[] uShortArray = new UShort[shortArray.length];
    for (int i = 0; i < shortArray.length; i++) {
      uShortArray[i] = UShort.valueOf(shortArray[i]);
    }
    return uShortArray;
  }

  /**
   * Get the {@link UInteger} array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @param length the length of the array.
   * @return the UInteger array at the given {@code index}.
   */
  public UInteger[] getUIntArray(T bytes, int index, int length) {
    int[] intArray = delegate.getIntArray(bytes, index, length);
    UInteger[] uIntArray = new UInteger[intArray.length];
    for (int i = 0; i < intArray.length; i++) {
      uIntArray[i] = UInteger.valueOf(intArray[i]);
    }
    return uIntArray;
  }

  /**
   * Get the {@link ULong} array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @param length the length of the array.
   * @return the ULong array at the given {@code index}.
   */
  public ULong[] getULongArray(T bytes, int index, int length) {
    long[] longArray = delegate.getLongArray(bytes, index, length);
    ULong[] uLongArray = new ULong[longArray.length];
    for (int i = 0; i < longArray.length; i++) {
      uLongArray[i] = ULong.valueOf(longArray[i]);
    }
    return uLongArray;
  }

  /**
   * Set the {@link UByte} value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the value in.
   * @param index the index into {@code bytes} to set the value at.
   * @param value the {@link UByte} value to set.
   */
  public void setUByte(T bytes, int index, UByte value) {
    setByte(bytes, index, value.byteValue());
  }

  /**
   * Set the {@link UShort} value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the value in.
   * @param index the index into {@code bytes} to set the value at.
   * @param value the {@link UShort} value to set.
   */
  public void setUShort(T bytes, int index, UShort value) {
    setShort(bytes, index, value.shortValue());
  }

  /**
   * Set the {@link UInteger} value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the value in.
   * @param index the index into {@code bytes} to set the value at.
   * @param value the {@link UInteger} value to set.
   */
  public void setUInt(T bytes, int index, UInteger value) {
    setInt(bytes, index, value.intValue());
  }

  /**
   * Set the {@link ULong} value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the value in.
   * @param index the index into {@code bytes} to set the value at.
   * @param value the {@link ULong} value to set.
   */
  public void setULong(T bytes, int index, ULong value) {
    setLong(bytes, index, value.longValue());
  }

  /**
   * Set the {@link UByte} array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the values in.
   * @param index the index into {@code bytes} to set the values at.
   * @param values the UByte array to set.
   */
  public void setUByteArray(T bytes, int index, UByte[] values) {
    byte[] byteArray = new byte[values.length];
    for (int i = 0; i < values.length; i++) {
      byteArray[i] = values[i].byteValue();
    }
    delegate.setByteArray(bytes, index, byteArray);
  }

  /**
   * Set the {@link UShort} array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the values in.
   * @param index the index into {@code bytes} to set the values at.
   * @param values the UShort array to set.
   */
  public void setUShortArray(T bytes, int index, UShort[] values) {
    short[] shortArray = new short[values.length];
    for (int i = 0; i < values.length; i++) {
      shortArray[i] = values[i].shortValue();
    }
    delegate.setShortArray(bytes, index, shortArray);
  }

  /**
   * Set the {@link UInteger} array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the values in.
   * @param index the index into {@code bytes} to set the values at.
   * @param values the UInteger array to set.
   */
  public void setUIntArray(T bytes, int index, UInteger[] values) {
    int[] intArray = new int[values.length];
    for (int i = 0; i < values.length; i++) {
      intArray[i] = values[i].intValue();
    }
    delegate.setIntArray(bytes, index, intArray);
  }

  /**
   * Set the {@link ULong} array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the values in.
   * @param index the index into {@code bytes} to set the values at.
   * @param values the ULong array to set.
   */
  public void setULongArray(T bytes, int index, ULong[] values) {
    long[] longArray = new long[values.length];
    for (int i = 0; i < values.length; i++) {
      longArray[i] = values[i].longValue();
    }
    delegate.setLongArray(bytes, index, longArray);
  }

  // region ByteOps delegated methods

  @Override
  public boolean getBoolean(T bytes, int index) {
    return delegate.getBoolean(bytes, index);
  }

  @Override
  public byte getByte(T bytes, int index) {
    return delegate.getByte(bytes, index);
  }

  @Override
  public short getShort(T bytes, int index) {
    return delegate.getShort(bytes, index);
  }

  @Override
  public int getInt(T bytes, int index) {
    return delegate.getInt(bytes, index);
  }

  @Override
  public long getLong(T bytes, int index) {
    return delegate.getLong(bytes, index);
  }

  @Override
  public float getFloat(T bytes, int index) {
    return delegate.getFloat(bytes, index);
  }

  @Override
  public double getDouble(T bytes, int index) {
    return delegate.getDouble(bytes, index);
  }

  @Override
  public boolean[] getBooleanArray(T bytes, int index, int length) {
    return delegate.getBooleanArray(bytes, index, length);
  }

  @Override
  public byte[] getByteArray(T bytes, int index, int length) {
    return delegate.getByteArray(bytes, index, length);
  }

  @Override
  public short[] getShortArray(T bytes, int index, int length) {
    return delegate.getShortArray(bytes, index, length);
  }

  @Override
  public int[] getIntArray(T bytes, int index, int length) {
    return delegate.getIntArray(bytes, index, length);
  }

  @Override
  public long[] getLongArray(T bytes, int index, int length) {
    return delegate.getLongArray(bytes, index, length);
  }

  @Override
  public float[] getFloatArray(T bytes, int index, int length) {
    return delegate.getFloatArray(bytes, index, length);
  }

  @Override
  public double[] getDoubleArray(T bytes, int index, int length) {
    return delegate.getDoubleArray(bytes, index, length);
  }

  @Override
  public void setBoolean(T bytes, int index, boolean value) {
    delegate.setBoolean(bytes, index, value);
  }

  @Override
  public void setByte(T bytes, int index, byte value) {
    delegate.setByte(bytes, index, value);
  }

  @Override
  public void setShort(T bytes, int index, short value) {
    delegate.setShort(bytes, index, value);
  }

  @Override
  public void setInt(T bytes, int index, int value) {
    delegate.setInt(bytes, index, value);
  }

  @Override
  public void setLong(T bytes, int index, long value) {
    delegate.setLong(bytes, index, value);
  }

  @Override
  public void setFloat(T bytes, int index, float value) {
    delegate.setFloat(bytes, index, value);
  }

  @Override
  public void setDouble(T bytes, int index, double value) {
    delegate.setDouble(bytes, index, value);
  }

  @Override
  public void setBooleanArray(T bytes, int index, boolean[] values) {
    delegate.setBooleanArray(bytes, index, values);
  }

  @Override
  public void setByteArray(T bytes, int index, byte[] values) {
    delegate.setByteArray(bytes, index, values);
  }

  @Override
  public void setShortArray(T bytes, int index, short[] values) {
    delegate.setShortArray(bytes, index, values);
  }

  @Override
  public void setIntArray(T bytes, int index, int[] values) {
    delegate.setIntArray(bytes, index, values);
  }

  @Override
  public void setLongArray(T bytes, int index, long[] values) {
    delegate.setLongArray(bytes, index, values);
  }

  @Override
  public void setFloatArray(T bytes, int index, float[] values) {
    delegate.setFloatArray(bytes, index, values);
  }

  @Override
  public void setDoubleArray(T bytes, int index, double[] values) {
    delegate.setDoubleArray(bytes, index, values);
  }

  @Override
  public Boolean[] getBoxedBooleanArray(T bytes, int index, int length) {
    return delegate.getBoxedBooleanArray(bytes, index, length);
  }

  @Override
  public void setBoxedBooleanArray(T bytes, int index, Boolean[] values) {
    delegate.setBoxedBooleanArray(bytes, index, values);
  }

  @Override
  public Byte[] getBoxedByteArray(T bytes, int index, int length) {
    return delegate.getBoxedByteArray(bytes, index, length);
  }

  @Override
  public void setBoxedByteArray(T bytes, int index, Byte[] values) {
    delegate.setBoxedByteArray(bytes, index, values);
  }

  @Override
  public Short[] getBoxedShortArray(T bytes, int index, int length) {
    return delegate.getBoxedShortArray(bytes, index, length);
  }

  @Override
  public void setBoxedShortArray(T bytes, int index, Short[] values) {
    delegate.setBoxedShortArray(bytes, index, values);
  }

  @Override
  public Integer[] getBoxedIntArray(T bytes, int index, int length) {
    return delegate.getBoxedIntArray(bytes, index, length);
  }

  @Override
  public void setBoxedIntArray(T bytes, int index, Integer[] values) {
    delegate.setBoxedIntArray(bytes, index, values);
  }

  @Override
  public Long[] getBoxedLongArray(T bytes, int index, int length) {
    return delegate.getBoxedLongArray(bytes, index, length);
  }

  @Override
  public void setBoxedLongArray(T bytes, int index, Long[] values) {
    delegate.setBoxedLongArray(bytes, index, values);
  }

  @Override
  public Float[] getBoxedFloatArray(T bytes, int index, int length) {
    return delegate.getBoxedFloatArray(bytes, index, length);
  }

  @Override
  public void setBoxedFloatArray(T bytes, int index, Float[] values) {
    delegate.setBoxedFloatArray(bytes, index, values);
  }

  @Override
  public Double[] getBoxedDoubleArray(T bytes, int index, int length) {
    return delegate.getBoxedDoubleArray(bytes, index, length);
  }

  @Override
  public void setBoxedDoubleArray(T bytes, int index, Double[] values) {
    delegate.setBoxedDoubleArray(bytes, index, values);
  }

  // endregion
}
