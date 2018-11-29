package com.roth.codec;

import com.roth.protocol.Packet;
import com.roth.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * author:  Wang Yunlong
 * times:    2018-11-29
 * purpose:
 **/
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) {
        PacketCodeC.INSTANCE.encode(byteBuf, packet);
    }
}
