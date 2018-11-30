package com.roth.server.handler;

import com.roth.attribute.Session;
import com.roth.attribute.SessionUtil;
import com.roth.protocol.request.LoginRequestPacket;
import com.roth.protocol.response.LoginResponsePacket;
import com.roth.utils.IDUti;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * author:  Wang Yunlong
 * times:    2018-11-29
 * purpose:
 **/
@Slf4j
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) {
        channelHandlerContext.channel().writeAndFlush(login(loginRequestPacket, channelHandlerContext));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unBindSession(ctx.channel());
    }

    private LoginResponsePacket login(LoginRequestPacket loginRequestPacket, ChannelHandlerContext ctx) {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUsername(loginRequestPacket.getUsername());
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = IDUti.randomId();
            loginResponsePacket.setUserId(userId);
            log.info("客户端 user:{} 登录成功", loginRequestPacket.getUsername());
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), ctx.channel());
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            log.info("客户端 userid:{} 登录失败", loginRequestPacket.getUserId());
        }
        return loginResponsePacket;
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        if ("roth".equalsIgnoreCase(loginRequestPacket.getUsername()) && "roth".equals(loginRequestPacket.getPassword())) {
            return true;
        }
        if ("jeff".equalsIgnoreCase(loginRequestPacket.getUsername()) && "jeff".equals(loginRequestPacket.getPassword())) {
            return true;
        }
        return false;
    }

}
