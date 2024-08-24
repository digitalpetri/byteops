package com.digitalpetri.util;

/**
 * {@link ByteOps} implementation that operates on byte arrays.
 */
public final class ByteArrayByteOps extends AbstractByteOps<byte[]> {

  /**
   * {@link ByteArrayByteOps} that assumes big-endian byte order.
   */
  public static final ByteArrayByteOps BIG_ENDIAN =
      new ByteArrayByteOps(new OrderedOps.BigEndianOps());

  /**
   * {@link ByteArrayByteOps} that assumes little-endian byte order.
   */
  public static final ByteArrayByteOps LITTLE_ENDIAN =
      new ByteArrayByteOps(new OrderedOps.LittleEndianOps());

  /**
   * {@link ByteArrayByteOps} that assumes big-endian byte order and low-high word order.
   */
  public static final ByteArrayByteOps BIG_ENDIAN_LOW_HIGH =
      new ByteArrayByteOps(new OrderedOps.BigEndianLowHighOps());

  /**
   * {@link ByteArrayByteOps} that assumes little-endian byte order and low-high word order.
   */
  public static final ByteArrayByteOps LITTLE_ENDIAN_LOW_HIGH =
      new ByteArrayByteOps(new OrderedOps.LittleEndianLowHighOps());


  public ByteArrayByteOps(OrderedOps orderedOps) {
    super(orderedOps);
  }

  @Override
  protected byte get(byte[] bytes, int index) {
    return bytes[index];
  }

  @Override
  protected void set(byte[] bytes, int index, byte value) {
    bytes[index] = value;
  }

}
