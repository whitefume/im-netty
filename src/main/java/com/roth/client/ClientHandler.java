package com.roth.client;

import com.roth.api.Packet;
import com.roth.api.PacketCodeC;
import com.roth.api.request.LoginRequestPacket;
import com.roth.api.request.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;
import java.util.Random;


@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    public void channelActive(ChannelHandlerContext ctx) {
        log.debug("client login begin");

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(new Random().nextInt(10000));
        loginRequestPacket.setUsername("flash");
        loginRequestPacket.setPassword("pwd");

        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        ctx.channel().writeAndFlush(buffer);
    }

    @Autowired
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        log.info("client response:{}", byteBuf.toString(Charset.defaultCharset()));
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                log.info("client login success");
            } else {
                log.info("client login false, reson:{}", loginResponsePacket.getReason());
            }
        }
    }


}
