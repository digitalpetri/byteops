package com.digitalpetri.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteBufferByteOpsTest extends AbstractByteOpsTest<ByteBuffer> {

  @Override
  protected ByteBuffer getBytes(byte[] bs) {
    return ByteBuffer.wrap(bs);
  }

  @Override
  protected ByteOps<ByteBuffer> getByteOps(ByteOrder byteOrder) {
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      return ByteBufferByteOps.BIG_ENDIAN;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      return ByteBufferByteOps.LITTLE_ENDIAN;
    } else {
      throw new IllegalArgumentException("unsupported byte order: " + byteOrder);
    }
  }

  @Override
  protected ByteOps<ByteBuffer> getSwappedByteOps(ByteOrder byteOrder) {
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      return ByteBufferByteOps.BIG_ENDIAN_LOW_HIGH;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      return ByteBufferByteOps.LITTLE_ENDIAN_LOW_HIGH;
    } else {
      throw new IllegalArgumentException("unsupported byte order: " + byteOrder);
    }
  }

}
