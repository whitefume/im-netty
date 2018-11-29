package com.roth.server;

import com.roth.api.Packet;
import com.roth.api.PacketCodeC;
import com.roth.api.request.LoginRequestPacket;
import com.roth.api.request.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Packet packet = PacketCodeC.INSTANCE.decode((ByteBuf) msg);
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                log.info("client userid:{} login success", loginRequestPacket.getUserId());
                // login success
            } else {
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
                log.info("client userid:{} login false", loginRequestPacket.getUserId());
            }
            ByteBuf responseBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
