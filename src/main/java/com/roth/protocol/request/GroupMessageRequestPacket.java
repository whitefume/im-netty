package com.roth.protocol.request;

import com.roth.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.roth.protocol.command.Command.GROUP_MESSAGE_REQUEST;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageRequestPacket extends Packet {
    private String toGroupId;
    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
