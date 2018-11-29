package com.roth.api.request;

import com.roth.api.Packet;
import lombok.Data;

import static com.roth.api.Command.LOGIN_REQUEST;

/**
 * 登录请求
 */
@Data
public class LoginRequestPacket extends Packet {
    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
