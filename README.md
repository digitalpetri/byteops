## ByteOps Utility Functions

Utility functions for getting values from and setting values into collections of bytes, taking into account byte order and word order.

Supported "bytes":
- `byte[]`
- `java.nio.ByteBuffer`
- `io.netty.buffer.ByteBuf`

Supported variations:
- Big-Endian
- Little-Endian
- Big-Endian + Low-High 
- Little-Endian + Low-High
