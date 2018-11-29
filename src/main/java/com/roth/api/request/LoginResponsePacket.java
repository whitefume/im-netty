package com.roth.api.request;

import com.roth.api.Packet;
import lombok.Data;

import static com.roth.api.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
