package com.digitalpetri.util;

public interface ByteOps<T> {

  /**
   * Get the boolean value at the given {@code index} in {@code bytes}.
   *
   * <p>A zero value is false and any non-zero is true.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @return the boolean value at the given {@code index}.
   */
  boolean getBoolean(T bytes, int index);

  /**
   * Get the byte value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @return the byte value at the given {@code index}.
   */
  byte getByte(T bytes, int index);

  /**
   * Get the short value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @return the short value at the given {@code index}.
   */
  short getShort(T bytes, int index);

  /**
   * Get the int value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @return the int value at the given {@code index}.
   */
  int getInt(T bytes, int index);

  /**
   * Get the long value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @return the long value at the given {@code index}.
   */
  long getLong(T bytes, int index);

  /**
   * Get the float value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @return the float value at the given {@code index}.
   */
  float getFloat(T bytes, int index);

  /**
   * Get the double value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @return the double value at the given {@code index}.
   */
  double getDouble(T bytes, int index);

  /**
   * Get the boolean array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @param length the length of the array.
   * @return the boolean array at the given {@code index}.
   */
  boolean[] getBooleanArray(T bytes, int index, int length);

  /**
   * Get the byte array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @param length the length of the array.
   * @return the byte array at the given {@code index}.
   */
  byte[] getByteArray(T bytes, int index, int length);

  /**
   * Get the short array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @param length the length of the array.
   * @return the short array at the given {@code index}.
   */
  short[] getShortArray(T bytes, int index, int length);

  /**
   * Get the int array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @param length the length of the array.
   * @return the int array at the given {@code index}.
   */
  int[] getIntArray(T bytes, int index, int length);

  /**
   * Get the long array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @param length the length of the array.
   * @return the long array at the given {@code index}.
   */
  long[] getLongArray(T bytes, int index, int length);

  /**
   * Get the float array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @param length the length of the array.
   * @return the float array at the given {@code index}.
   */
  float[] getFloatArray(T bytes, int index, int length);

  /**
   * Get the double array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @param length the length of the array.
   * @return the double array at the given {@code index}.
   */
  double[] getDoubleArray(T bytes, int index, int length);

  /**
   * Set the boolean value at the given {@code index} in {@code bytes}.
   *
   * <p>A false value is set as zero and a true value is set as one.
   *
   * @param bytes the bytes to set the value in.
   * @param index the index into {@code bytes} to set the value at.
   * @param value the boolean value to set.
   */
  void setBoolean(T bytes, int index, boolean value);

  /**
   * Set the byte value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the value in.
   * @param index the index into {@code bytes} to set the value at.
   * @param value the byte value to set.
   */
  void setByte(T bytes, int index, byte value);

  /**
   * Set the short value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the value in.
   * @param index the index into {@code bytes} to set the value at.
   * @param value the short value to set.
   */
  void setShort(T bytes, int index, short value);

  /**
   * Set the int value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the value in.
   * @param index the index into {@code bytes} to set the value at.
   * @param value the int value to set.
   */
  void setInt(T bytes, int index, int value);

  /**
   * Set the long value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the value in.
   * @param index the index into {@code bytes} to set the value at.
   * @param value the long value to set.
   */
  void setLong(T bytes, int index, long value);

  /**
   * Set the float value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the value in.
   * @param index the index into {@code bytes} to set the value at.
   * @param value the float value to set.
   */
  void setFloat(T bytes, int index, float value);

  /**
   * Set the double value at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the value in.
   * @param index the index into {@code bytes} to set the value at.
   * @param value the double value to set.
   */
  void setDouble(T bytes, int index, double value);

  /**
   * Set the boolean array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the values in.
   * @param index the index into {@code bytes} to set the values at.
   * @param values the boolean array to set.
   */
  void setBooleanArray(T bytes, int index, boolean[] values);

  /**
   * Set the byte array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the values in.
   * @param index the index into {@code bytes} to set the values at.
   * @param values the byte array to set.
   */
  void setByteArray(T bytes, int index, byte[] values);

  /**
   * Set the short array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the values in.
   * @param index the index into {@code bytes} to set the values at.
   * @param values the short array to set.
   */
  void setShortArray(T bytes, int index, short[] values);

  /**
   * Set the int array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the values in.
   * @param index the index into {@code bytes} to set the values at.
   * @param values the int array to set.
   */
  void setIntArray(T bytes, int index, int[] values);

  /**
   * Set the long array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the values in.
   * @param index the index into {@code bytes} to set the values at.
   * @param values the long array to set.
   */
  void setLongArray(T bytes, int index, long[] values);

  /**
   * Set the float array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the values in.
   * @param index the index into {@code bytes} to set the values at.
   * @param values the float array to set.
   */
  void setFloatArray(T bytes, int index, float[] values);

  /**
   * Set the double array at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the values in.
   * @param index the index into {@code bytes} to set the values at.
   * @param values the double array to set.
   */
  void setDoubleArray(T bytes, int index, double[] values);
}
