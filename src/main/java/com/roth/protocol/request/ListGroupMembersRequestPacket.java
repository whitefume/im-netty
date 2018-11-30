package com.roth.protocol.request;

import com.roth.protocol.Packet;
import lombok.Data;

import static com.roth.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

@Data
public class ListGroupMembersRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
