package com.digitalpetri.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteOrder;

class ByteBufByteOpsTest extends AbstractByteOpsTest<ByteBuf> {

  @Override
  protected ByteBuf getBytes(byte[] bs) {
    return Unpooled.wrappedBuffer(bs);
  }

  @Override
  protected ByteOps<ByteBuf> getByteOps(ByteOrder byteOrder) {
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      return ByteBufByteOps.BIG_ENDIAN;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      return ByteBufByteOps.LITTLE_ENDIAN;
    } else {
      throw new IllegalArgumentException("unsupported byte order: " + byteOrder);
    }
  }

  @Override
  protected ByteOps<ByteBuf> getSwappedByteOps(ByteOrder byteOrder) {
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      return ByteBufByteOps.BIG_ENDIAN_LOW_HIGH;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      return ByteBufByteOps.LITTLE_ENDIAN_LOW_HIGH;
    } else {
      throw new IllegalArgumentException("unsupported byte order: " + byteOrder);
    }
  }

}
