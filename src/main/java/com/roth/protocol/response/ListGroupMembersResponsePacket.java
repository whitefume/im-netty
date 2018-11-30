package com.roth.protocol.response;

import com.roth.attribute.Session;
import com.roth.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.roth.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {
    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
