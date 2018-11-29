package com.roth.protocol.request;

import com.roth.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.roth.protocol.command.Command.MESSAGE_REQUEST;

/**
 * author:  Wang Yunlong
 * times:    2018-11-29
 * purpose:
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestPacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
