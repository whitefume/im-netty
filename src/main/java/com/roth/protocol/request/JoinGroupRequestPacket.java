package com.roth.protocol.request;

import com.roth.protocol.Packet;
import lombok.Data;

import static com.roth.protocol.command.Command.JOIN_GROUP_REQUEST;

@Data
public class JoinGroupRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
