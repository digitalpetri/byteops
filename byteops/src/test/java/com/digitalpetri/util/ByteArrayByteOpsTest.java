package com.digitalpetri.util;

import static java.nio.ByteOrder.BIG_ENDIAN;
import static java.nio.ByteOrder.LITTLE_ENDIAN;

import java.nio.ByteOrder;

class ByteArrayByteOpsTest extends AbstractByteOpsTest<byte[]> {

  @Override
  protected byte[] getBytes(byte[] bs) {
    return bs;
  }

  @Override
  protected ByteOps<byte[]> getByteOps(ByteOrder byteOrder) {
    if (byteOrder == BIG_ENDIAN) {
      return ByteArrayByteOps.BIG_ENDIAN;
    } else if (byteOrder == LITTLE_ENDIAN) {
      return ByteArrayByteOps.LITTLE_ENDIAN;
    } else {
      throw new IllegalArgumentException("unsupported byte order: " + byteOrder);
    }
  }

  @Override
  protected ByteOps<byte[]> getSwappedByteOps(ByteOrder byteOrder) {
    if (byteOrder == BIG_ENDIAN) {
      return ByteArrayByteOps.BIG_ENDIAN_LOW_HIGH;
    } else if (byteOrder == LITTLE_ENDIAN) {
      return ByteArrayByteOps.LITTLE_ENDIAN_LOW_HIGH;
    } else {
      throw new IllegalArgumentException("unsupported byte order: " + byteOrder);
    }
  }

}