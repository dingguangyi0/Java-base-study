package fun.ycdr.io.nio.example;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class NioDiscardServer {
    public static void startServer() throws IOException {
        // 获取选择器
        Selector selector = Selector.open();
        // 获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置通道为非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定连接
        serverSocketChannel.bind(new InetSocketAddress(1888));
        log.info("服务器启动成功");
        //将通道 “接受新连接” io 事件注册到选择器上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //轮训感兴趣的IO就绪事件
        while (selector.select()>0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                if (selectionKey.isAcceptable()) {
                    //选择连接就绪 获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置新连接为非阻塞
                    socketChannel.configureBlocking(false);
                    // 注册新连接的到选择器-事件为-可读事件
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int length = 0;
                    while ((length = socketChannel.read(byteBuffer))!=-1) {
                        byteBuffer.flip();
                        log.info("客户端发送信息-{}",new String(byteBuffer.array(),0,length));
                        byteBuffer.clear();
                    }
                    socketChannel.close();
                }
                keyIterator.remove();
            }
        }
        serverSocketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        startServer();
    }
}
