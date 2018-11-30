package com.roth.server.handler;

import com.roth.attribute.SessionUtil;
import com.roth.protocol.request.GroupMessageRequestPacket;
import com.roth.protocol.response.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

@ChannelHandler.Sharable
@Slf4j
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    private GroupMessageRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                GroupMessageRequestPacket groupMessageRequestPacket) {
        String groupId = groupMessageRequestPacket.getToGroupId();
        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        responsePacket.setFromGroupId(groupId);
        responsePacket.setMessage(groupMessageRequestPacket.getMessage());
        responsePacket.setFromUser(SessionUtil.getSession(channelHandlerContext.channel()));

        ChannelGroup channels = SessionUtil.getChannelGroup(groupId);
        channels.writeAndFlush(responsePacket);
    }
}
