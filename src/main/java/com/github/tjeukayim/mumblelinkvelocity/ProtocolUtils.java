package com.github.tjeukayim.mumblelinkvelocity;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import java.nio.charset.StandardCharsets;

/**
 * Inspired by https://github.com/VelocityPowered/Velocity/blob/5ea6728d1a780186a98841d59b88867debf864fa/proxy/src/main/java/com/velocitypowered/proxy/protocol/ProtocolUtils.java
 */
public class ProtocolUtils {
    public static void writeString(ByteBuf buf, CharSequence str) {
        int size = ByteBufUtil.utf8Bytes(str);
        writeVarInt(buf, size);
        buf.writeCharSequence(str, StandardCharsets.UTF_8);
    }

    public static void writeVarInt(ByteBuf buf, int value) {
        while (true) {
            if ((value & 0xFFFFFF80) == 0) {
                buf.writeByte(value);
                return;
            }

            buf.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }
    }

    public static void writeEnumConstant(ByteBuf buf, Enum<?> e) {
        writeVarInt(buf, e.ordinal());
    }
}
