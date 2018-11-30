package com.roth.server;

import com.roth.codec.PacketCodecHandler;
import com.roth.codec.Spliter;
import com.roth.server.handler.AuthHandler;
import com.roth.server.handler.IMHandler;
import com.roth.server.handler.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import static com.roth.Constants.SERVER_PORT;

/**
 * author:  Wang Yunlong
 * times:    2018-11-29
 * purpose:
 **/
@Slf4j
public class NettyServer {

    public static void main(String... args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel nioSocketChannel) {
                        nioSocketChannel.pipeline().addLast(new Spliter());
                        nioSocketChannel.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(AuthHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(IMHandler.INSTANCE);
                    }
                });
        bind(serverBootstrap, SERVER_PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(
                future -> {
                    if (future.isSuccess()) {
                        log.info("port: {} bind success", port);
                    } else {
                        log.warn("prot: {} bind false", port);
                        bind(serverBootstrap, port + 1);
                    }
                });
    }
}
