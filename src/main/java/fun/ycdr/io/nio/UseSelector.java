package fun.ycdr.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class UseSelector {
    public static void demo() throws IOException {
        //获取一个 selector的实例
        Selector selector = Selector.open();
        //获取一个通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置为非阻塞的
        serverSocketChannel.configureBlocking(false);
        //绑定链接
        serverSocketChannel.bind(new InetSocketAddress(1888));
        //将通道注册到选择器上,并指定监听事件为"接受连接"
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select()>0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                if (selectionKey.isAcceptable()) {

                }else if (selectionKey.isConnectable()) {

                }else if (selectionKey.isReadable()) {

                }else if (selectionKey.isWritable()) {

                }
                keyIterator.remove();
            }
        }
    }
}
