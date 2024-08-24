package com.digitalpetri.util;

import io.netty.buffer.ByteBuf;

public final class ByteBufByteOps extends AbstractByteOps<ByteBuf> {

  /**
   * {@link ByteBufByteOps} that assumes big-endian byte order.
   */
  public static final ByteBufByteOps BIG_ENDIAN =
      new ByteBufByteOps(new OrderedOps.BigEndianOps());

  /**
   * {@link ByteBufByteOps} that assumes little-endian byte order.
   */
  public static final ByteBufByteOps LITTLE_ENDIAN =
      new ByteBufByteOps(new OrderedOps.LittleEndianOps());

  /**
   * {@link ByteBufByteOps} that assumes big-endian byte order and low-high word order.
   */
  public static final ByteBufByteOps BIG_ENDIAN_LOW_HIGH =
      new ByteBufByteOps(new OrderedOps.BigEndianLowHighOps());

  /**
   * {@link ByteBufByteOps} that assumes little-endian byte order and low-high word order.
   */
  public static final ByteBufByteOps LITTLE_ENDIAN_LOW_HIGH =
      new ByteBufByteOps(new OrderedOps.LittleEndianLowHighOps());


  public ByteBufByteOps(OrderedOps orderedOps) {
    super(orderedOps);
  }

  @Override
  protected byte get(ByteBuf bytes, int index) {
    return bytes.getByte(index);
  }

  @Override
  protected void set(ByteBuf bytes, int index, byte value) {
    bytes.setByte(index, value);
  }

}
