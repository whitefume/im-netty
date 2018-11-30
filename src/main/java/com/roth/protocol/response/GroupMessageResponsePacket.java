package com.roth.protocol.response;

import com.roth.attribute.Session;
import com.roth.protocol.Packet;
import lombok.Data;

import static com.roth.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {
    private String fromGroupId;
    private Session fromUser;
    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
