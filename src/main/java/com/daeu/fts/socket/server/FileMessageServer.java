package com.daeu.fts.socket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class FileMessageServer {

    static FileMessageServer server;
    private static final Logger logger = LoggerFactory.getLogger(FileMessageServer.class);

    static public FileMessageServer getInstance() {
        if (server == null) {
            server = new FileMessageServer();
        }

        return server;
    }

    public void run(Environment env) throws Exception {
        // EventLoop : 연결의 수명주기 중 발생하는 이벤트를 처리하는 핵심 추상화를 정의
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // 쓰레드 개수를 정할 수 있음. 현재는 값이 없으므로 CPU 코어 수로 세팅됨.

        try {
            ServerBootstrap b = new ServerBootstrap();                      // 부트 스트랩(서버를 구성하는 시동코드) 객체 생성
            b.group(bossGroup, workerGroup)                                 // 그룹 지정
                    .channel(NioServerSocketChannel.class)                  // 채널 초기화
                    .childHandler(new FileMessageServerInitializer(env))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);     // 자식 채널의 초기화

            // 인커밍 커넥션을 액세스하기 위해 바인드하고 시작합니다.
            ChannelFuture f = b.bind(env.getProperty("SERVER.SOCKET.PORT", Integer.class)).sync();

            // 서버 소켓이 닫힐때까지 대기합니다.
            f.channel().closeFuture().sync();

            // this get notified when a write request is finished
            // f.addListener(ChannelFutureListener.CLOSE);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
