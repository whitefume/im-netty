package com.roth.client.handler;

import com.roth.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * author:  Wang Yunlong
 * times:    2018-11-30
 * purpose:
 **/
@Slf4j
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) {
        log.info("收到服务端的消息:{} ", messageResponsePacket.getMessage());
    }
}
