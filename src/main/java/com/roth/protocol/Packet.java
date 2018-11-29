package com.roth.protocol;

import lombok.Data;

/**
 * 数据包抽象
 */
@Data
public abstract class Packet {

    private Byte version = 1;

    public abstract Byte getCommand();
}
