package com.roth.protocol.response;

import com.roth.protocol.Packet;
import lombok.Data;

import static com.roth.protocol.command.Command.LOGOUT_RESPONSE;

@Data
public class LogoutResponsePacket extends Packet {
    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
