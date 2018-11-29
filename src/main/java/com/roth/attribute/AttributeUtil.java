package com.roth.attribute;


import io.netty.channel.Channel;

/**
 * author:  Wang Yunlong
 * times:    2018-11-29
 * purpose:
 **/
public class AttributeUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.LOGIN).get() != null;
    }

}
