package com.roth.codec;

import com.roth.protocol.Packet;
import com.roth.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {
    public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) {
        ByteBuf byteBuf = channelHandlerContext.alloc().ioBuffer();
        PacketCodeC.INSTANCE.encode(byteBuf, packet);
        list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        list.add(PacketCodeC.INSTANCE.decode(byteBuf));
    }
}
