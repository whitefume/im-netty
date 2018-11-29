package com.roth.server;

import com.roth.codec.PacketDecoder;
import com.roth.codec.PacketEncoder;
import com.roth.server.handler.LoginRequestHandler;
import com.roth.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
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
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel nioSocketChannel) {
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(new LoginRequestHandler());
                        nioSocketChannel.pipeline().addLast(new MessageRequestHandler());
                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
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
