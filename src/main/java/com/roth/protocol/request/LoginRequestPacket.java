package com.roth.protocol.request;

import com.roth.protocol.Packet;
import lombok.Data;

import static com.roth.protocol.command.Command.LOGIN_REQUEST;

/**
 * 登录请求
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
