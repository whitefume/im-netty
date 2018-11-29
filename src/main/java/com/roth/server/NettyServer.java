package com.roth.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.roth.api.Constants.SERVER_PORT;

/**
 * author:  Wang Yunlong
 * times:    2018-11-28
 * purpose:
 **/
public class NettyServer {
    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);

    public static void main(String... args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel nioSocketChannel) {
                        nioSocketChannel.pipeline().addLast(new ServerHandler());
                    }
                });
        bind(serverBootstrap, SERVER_PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(
                future -> {
                    if (future.isSuccess()) {
                        LOG.info("port: {} bind success", port);
                    } else {
                        LOG.warn("prot: {} bind false", port);
                        bind(serverBootstrap, port + 1);
                    }
                });
    }
}
