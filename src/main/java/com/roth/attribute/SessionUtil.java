package com.roth.attribute;


import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author:  Wang Yunlong
 * times:    2018-11-29
 * purpose:
 **/
@Slf4j
public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            userIdChannelMap.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
            log.info("{} 退出登录!", session);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.SESSION).get() != null;
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupid, ChannelGroup channels) {
        groupIdChannelGroupMap.put(groupid, channels);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelGroupMap.get(groupId);
    }
}
