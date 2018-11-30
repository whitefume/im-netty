package com.roth.server.handler;

import com.roth.attribute.SessionUtil;
import com.roth.protocol.request.CreateGroupRequestPacket;
import com.roth.protocol.response.CreateGroupResponsePacket;
import com.roth.utils.IDUti;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    private CreateGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                CreateGroupRequestPacket createGroupRequestPacket) {
        List<String> userIdList = createGroupRequestPacket.getUserIdList();
        List<String> userNameList = new ArrayList<>();
        ChannelGroup channels = new DefaultChannelGroup(channelHandlerContext.executor());

        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channels.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        String groupId = IDUti.randomId();
        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        responsePacket.setUserNameList(userNameList);

        channels.writeAndFlush(responsePacket);

        log.info("群创建成功， id:{}  群成员为：{}", groupId, userIdList);

        SessionUtil.bindChannelGroup(groupId, channels);
    }
}
