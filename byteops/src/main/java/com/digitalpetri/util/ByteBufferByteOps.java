package com.digitalpetri.util;

import java.nio.ByteBuffer;

/**
 * {@link ByteOps} implementation that operates on {@link ByteBuffer}s.
 */
public final class ByteBufferByteOps extends AbstractByteOps<ByteBuffer> {

  /**
   * {@link ByteBufferByteOps} that assumes big-endian byte order.
   */
  public static final ByteBufferByteOps BIG_ENDIAN =
      new ByteBufferByteOps(new OrderedOps.BigEndianOps());

  /**
   * {@link ByteBufferByteOps} that assumes little-endian byte order.
   */
  public static final ByteBufferByteOps LITTLE_ENDIAN =
      new ByteBufferByteOps(new OrderedOps.LittleEndianOps());

  /**
   * {@link ByteBufferByteOps} that assumes big-endian byte order and low-high word order.
   */
  public static final ByteBufferByteOps BIG_ENDIAN_LOW_HIGH =
      new ByteBufferByteOps(new OrderedOps.BigEndianLowHighOps());

  /**
   * {@link ByteBufferByteOps} that assumes little-endian byte order and low-high word order.
   */
  public static final ByteBufferByteOps LITTLE_ENDIAN_LOW_HIGH =
      new ByteBufferByteOps(new OrderedOps.LittleEndianLowHighOps());


  public ByteBufferByteOps(OrderedOps orderedOps) {
    super(orderedOps);
  }

  @Override
  protected byte get(ByteBuffer bytes, int index) {
    return bytes.get(index);
  }

  @Override
  protected void set(ByteBuffer bytes, int index, byte value) {
    bytes.put(index, value);
  }

}
