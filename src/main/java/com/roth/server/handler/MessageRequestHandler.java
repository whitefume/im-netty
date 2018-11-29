package com.roth.server.handler;

import com.roth.protocol.request.MessageRequestPacket;
import com.roth.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * author:  Wang Yunlong
 * times:    2018-11-29
 * purpose:
 **/
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) {
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        log.info("收到客户端消息:{}", messageRequestPacket.getMessage());
        messageResponsePacket.setMessage(String.format("%s 的服务端应答", messageRequestPacket.getMessage()));
        channelHandlerContext.channel().writeAndFlush(messageResponsePacket);
    }
}
