package com.roth.server.handler;

import com.roth.attribute.SessionUtil;
import com.roth.protocol.request.LogoutRequestPacket;
import com.roth.protocol.response.LogoutResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Data;

@ChannelHandler.Sharable
@Data
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                LogoutRequestPacket logoutRequestPacket) {
        SessionUtil.unBindSession(channelHandlerContext.channel());
        LogoutResponsePacket responsePacket = new LogoutResponsePacket();
        responsePacket.setSuccess(true);
        channelHandlerContext.writeAndFlush(responsePacket);
    }


}
