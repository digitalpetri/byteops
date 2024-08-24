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

}
