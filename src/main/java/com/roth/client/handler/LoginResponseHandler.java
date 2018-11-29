package com.roth.client.handler;

import com.roth.attribute.AttributeUtil;
import com.roth.protocol.request.LoginRequestPacket;
import com.roth.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * author:  Wang Yunlong
 * times:    2018-11-29
 * purpose:
 **/
@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId(new Random().nextInt(10000));
        requestPacket.setUsername("flash");
        requestPacket.setPassword("pwd");

        channelHandlerContext.channel().writeAndFlush(requestPacket);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) {
        if (loginResponsePacket.isSuccess()) {
            log.info("客户端登录成功");
            AttributeUtil.markAsLogin(channelHandlerContext.channel());
        } else {
            log.info("客户端登录失败");
        }
    }
}
