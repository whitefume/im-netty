package com.roth.attribute;

import io.netty.util.AttributeKey;

/**
 * author:  Wang Yunlong
 * times:    2018-11-29
 * purpose:
 **/
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
