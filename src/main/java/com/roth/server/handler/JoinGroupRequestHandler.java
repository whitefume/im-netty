package com.roth.server.handler;

import com.roth.attribute.SessionUtil;
import com.roth.protocol.request.JoinGroupRequestPacket;
import com.roth.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

@ChannelHandler.Sharable
@Slf4j
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    private JoinGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                JoinGroupRequestPacket joinGroupRequestPacket) {
        String groupId = joinGroupRequestPacket.getGroupId();
        ChannelGroup channels = SessionUtil.getChannelGroup(groupId);
        channels.add(channelHandlerContext.channel());
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        channelHandlerContext.writeAndFlush(responsePacket);
    }
}
