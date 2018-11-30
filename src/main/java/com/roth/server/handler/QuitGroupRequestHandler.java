package com.roth.server.handler;

import com.roth.attribute.SessionUtil;
import com.roth.protocol.request.QuitGroupRequestPacket;
import com.roth.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    private QuitGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                QuitGroupRequestPacket quitGroupRequestPacket) {
        String groupId = quitGroupRequestPacket.getGroupId();
        ChannelGroup channels = SessionUtil.getChannelGroup(groupId);
        channels.remove(channelHandlerContext.channel());

        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSuccess(true);
        channelHandlerContext.writeAndFlush(responsePacket);
    }
}
