package com.roth.protocol.request;

import com.roth.protocol.Packet;
import lombok.Data;

import static com.roth.protocol.command.Command.QUIT_GROUP_REQUEST;

@Data
public class QuitGroupRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
