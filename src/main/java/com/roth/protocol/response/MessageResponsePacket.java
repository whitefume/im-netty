package com.roth.protocol.response;

import com.roth.protocol.Packet;
import lombok.Data;

import static com.roth.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * author:  Wang Yunlong
 * times:    2018-11-29
 * purpose:
 **/
@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
