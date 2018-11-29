package com.roth.protocol.response;

import com.roth.protocol.Packet;
import lombok.Data;

import static com.roth.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
