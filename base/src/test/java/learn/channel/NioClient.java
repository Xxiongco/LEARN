package learn.channel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioClient {
    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));
        while (true) {
            //阻塞 等待事件发生
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(key -> {
                try {
                    if (key.isConnectable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        //是否正在连接
                        if (channel.isConnectionPending()) {
                            //结束正在连接
                            channel.finishConnect();
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((LocalDateTime.now() + " 连接成功").getBytes());
                            writeBuffer.flip();
                            //将buffer写入channel
                            channel.write(writeBuffer);
                            ExecutorService service = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            //线程，从键盘读入数据
                            service.submit(() -> {
                                try {
                                    while (true) {
                                        //清空buffer
                                        writeBuffer.clear();
                                        InputStreamReader input = new InputStreamReader(System.in);
                                        BufferedReader bufferedReader = new BufferedReader(input);
                                        String senderMessage = bufferedReader.readLine();
                                        writeBuffer.put(senderMessage.getBytes());
                                        writeBuffer.flip();
                                        channel.write(writeBuffer);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                        //注册事件
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        //channel 有信息的输入
                        //哪个channel 触发了 read
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int count = channel.read(readBuffer);//server发来的
                        if (count > 0) {
                            String receiveMessage = new String(readBuffer.array(), 0, count);
                            System.out.println(receiveMessage);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    selectionKeys.clear();//移除已经发生的事件
                }
            });
        }
    }
}