package fun.ycdr.io.nio.example;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Slf4j
public class NioDiscardClient {
    public static void startClient() throws IOException {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 1888);
        //获取通道
        SocketChannel socketChannel = SocketChannel.open(address);
        socketChannel.configureBlocking(false);
        //不断自旋 等待连接完成
        while (!socketChannel.finishConnect()) {
            log.info("客户端等待服务器连接........");
        }
        log.info("客户端连接成功");
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("hello world".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        socketChannel.shutdownOutput();
        socketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        while (true) {
            startClient();
        }
    }
}
