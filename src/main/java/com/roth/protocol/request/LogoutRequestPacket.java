package com.roth.protocol.request;

import com.roth.protocol.Packet;
import lombok.Data;

import static com.roth.protocol.command.Command.LOGOUT_REQUEST;

@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
