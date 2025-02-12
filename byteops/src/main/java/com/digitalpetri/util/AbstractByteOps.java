package com.digitalpetri.util;

public abstract class AbstractByteOps<T> implements ByteOps<T> {

  private final OrderedOps orderedOps;

  public AbstractByteOps(OrderedOps orderedOps) {
    this.orderedOps = orderedOps;
  }

  /**
   * Get the byte at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to get the value from.
   * @param index the index into {@code bytes} to get the value at.
   * @return the byte at the given {@code index}.
   */
  protected abstract byte get(T bytes, int index);

  /**
   * Set the byte at the given {@code index} in {@code bytes}.
   *
   * @param bytes the bytes to set the value in.
   * @param index the index into {@code bytes} to set the value at.
   * @param value the value to set.
   */
  protected abstract void set(T bytes, int index, byte value);

  @Override
  public boolean getBoolean(T bytes, int index) {
    return get(bytes, index) != 0;
  }

  @Override
  public byte getByte(T bytes, int index) {
    return get(bytes, index);
  }

  @Override
  public short getShort(T bytes, int index) {
    return orderedOps.getShort(idx -> get(bytes, idx), index);
  }

  @Override
  public int getInt(T bytes, int index) {
    return orderedOps.getInt(idx -> get(bytes, idx), index);
  }

  @Override
  public long getLong(T bytes, int index) {
    return orderedOps.getLong(idx -> get(bytes, idx), index);
  }

  @Override
  public float getFloat(T bytes, int index) {
    return Float.intBitsToFloat(getInt(bytes, index));
  }

  @Override
  public double getDouble(T bytes, int index) {
    return Double.longBitsToDouble(getLong(bytes, index));
  }

  @Override
  public void setBoolean(T bytes, int index, boolean value) {
    if (value) {
      set(bytes, index, (byte) 1);
    } else {
      set(bytes, index, (byte) 0);
    }
  }

  @Override
  public boolean[] getBooleanArray(T bytes, int index, int length) {
    var value = new boolean[length];

    for (int i = 0; i < length; i++) {
      value[i] = getBoolean(bytes, index + i);
    }

    return value;
  }

  @Override
  public byte[] getByteArray(T bytes, int index, int length) {
    var value = new byte[length];

    for (int i = 0; i < length; i++) {
      value[i] = getByte(bytes, index + i);
    }

    return value;
  }

  @Override
  public short[] getShortArray(T bytes, int index, int length) {
    var value = new short[length];

    for (int i = 0; i < length; i++) {
      value[i] = getShort(bytes, index + i * 2);
    }

    return value;
  }

  @Override
  public int[] getIntArray(T bytes, int index, int length) {
    var value = new int[length];

    for (int i = 0; i < length; i++) {
      value[i] = getInt(bytes, index + i * 4);
    }

    return value;
  }

  @Override
  public long[] getLongArray(T bytes, int index, int length) {
    var value = new long[length];

    for (int i = 0; i < length; i++) {
      value[i] = getLong(bytes, index + i * 8);
    }

    return value;
  }

  @Override
  public float[] getFloatArray(T bytes, int index, int length) {
    var value = new float[length];

    for (int i = 0; i < length; i++) {
      value[i] = getFloat(bytes, index + i * 4);
    }

    return value;
  }

  @Override
  public double[] getDoubleArray(T bytes, int index, int length) {
    var value = new double[length];

    for (int i = 0; i < length; i++) {
      value[i] = getDouble(bytes, index + i * 8);
    }

    return value;
  }

  @Override
  public void setByte(T bytes, int index, byte value) {
    set(bytes, index, value);
  }

  @Override
  public void setShort(T bytes, int index, short value) {
    orderedOps.setShort((idx, b) -> set(bytes, idx, b), index, value);
  }

  @Override
  public void setInt(T bytes, int index, int value) {
    orderedOps.setInt((idx, b) -> set(bytes, idx, b), index, value);
  }

  @Override
  public void setLong(T bytes, int index, long value) {
    orderedOps.setLong((idx, b) -> set(bytes, idx, b), index, value);
  }

  @Override
  public void setFloat(T bytes, int index, float value) {
    setInt(bytes, index, Float.floatToRawIntBits(value));
  }

  @Override
  public void setDouble(T bytes, int index, double value) {
    setLong(bytes, index, Double.doubleToRawLongBits(value));
  }

  @Override
  public void setBooleanArray(T bytes, int index, boolean[] values) {
    for (int i = 0; i < values.length; i++) {
      setBoolean(bytes, index + i, values[i]);
    }
  }

  @Override
  public void setBooleanArray(T bytes, int index, Boolean[] values) {
    for (int i = 0; i < values.length; i++) {
      setBoolean(bytes, index + i, values[i]);
    }
  }

  @Override
  public void setByteArray(T bytes, int index, byte[] values) {
    for (int i = 0; i < values.length; i++) {
      setByte(bytes, index + i, values[i]);
    }
  }

  @Override
  public void setByteArray(T bytes, int index, Byte[] values) {
    for (int i = 0; i < values.length; i++) {
      setByte(bytes, index + i, values[i]);
    }
  }

  @Override
  public void setShortArray(T bytes, int index, short[] values) {
    for (int i = 0; i < values.length; i++) {
      setShort(bytes, index + i * 2, values[i]);
    }
  }

  @Override
  public void setShortArray(T bytes, int index, Short[] values) {
    for (int i = 0; i < values.length; i++) {
      setShort(bytes, index + i * 2, values[i]);
    }
  }

  @Override
  public void setIntArray(T bytes, int index, int[] values) {
    for (int i = 0; i < values.length; i++) {
      setInt(bytes, index + i * 4, values[i]);
    }
  }

  @Override
  public void setIntArray(T bytes, int index, Integer[] values) {
    for (int i = 0; i < values.length; i++) {
      setInt(bytes, index + i * 4, values[i]);
    }
  }

  @Override
  public void setLongArray(T bytes, int index, long[] values) {
    for (int i = 0; i < values.length; i++) {
      setLong(bytes, index + i * 8, values[i]);
    }
  }

  @Override
  public void setLongArray(T bytes, int index, Long[] values) {
    for (int i = 0; i < values.length; i++) {
      setLong(bytes, index + i * 8, values[i]);
    }
  }

  @Override
  public void setFloatArray(T bytes, int index, float[] values) {
    for (int i = 0; i < values.length; i++) {
      setFloat(bytes, index + i * 4, values[i]);
    }
  }

  @Override
  public void setFloatArray(T bytes, int index, Float[] values) {
    for (int i = 0; i < values.length; i++) {
      setFloat(bytes, index + i * 4, values[i]);
    }
  }

  @Override
  public void setDoubleArray(T bytes, int index, double[] values) {
    for (int i = 0; i < values.length; i++) {
      setDouble(bytes, index + i * 8, values[i]);
    }
  }

  @Override
  public void setDoubleArray(T bytes, int index, Double[] values) {
    for (int i = 0; i < values.length; i++) {
      setDouble(bytes, index + i * 8, values[i]);
    }
  }
}
