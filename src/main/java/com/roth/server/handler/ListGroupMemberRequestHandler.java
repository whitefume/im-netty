package com.roth.server.handler;

import com.roth.attribute.Session;
import com.roth.attribute.SessionUtil;
import com.roth.protocol.request.ListGroupMembersRequestPacket;
import com.roth.protocol.response.ListGroupMembersResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class ListGroupMemberRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    public static final ListGroupMemberRequestHandler INSTANCE = new ListGroupMemberRequestHandler();

    private ListGroupMemberRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                ListGroupMembersRequestPacket listGroupMembersRequestPacket) {
        String groupId = listGroupMembersRequestPacket.getGroupId();
        ChannelGroup channels = SessionUtil.getChannelGroup(groupId);

        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channels) {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }

        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);
        channelHandlerContext.writeAndFlush(responsePacket);
    }
}
