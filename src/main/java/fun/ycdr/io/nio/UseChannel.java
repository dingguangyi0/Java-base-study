package fun.ycdr.io.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class UseChannel {

    public static void main(String[] args) throws IOException {
        nioCopyResouceFile();
    }
    /**
     * 创建FileChannel
     * @throws FileNotFoundException
     */
    public void createFileChannel() throws FileNotFoundException {
        //创建文件流
        FileInputStream fis = new FileInputStream("");
        //获取文件流通道 只读
        FileChannel fileChannel = fis.getChannel();
        //创建文件输出流
        FileOutputStream fos = new FileOutputStream("");
        //获取文件流通道 只写
        FileChannel channel = fos.getChannel();
        //创建随机访问对象
        RandomAccessFile rFile = new RandomAccessFile("","rw");
        //获取文件流通道(可读可写)
        FileChannel rFileChannel = rFile.getChannel();
    }

    public static void readChannel() throws IOException {
        //创建随机访问对象
        RandomAccessFile rFile = new RandomAccessFile("/e/ycdr/project/Java-base-study/src/main/resources/file/test-channel.txt","rw");
        //获取文件流通道(可读可写)
        FileChannel rFileChannel = rFile.getChannel();
        //获取一个字节缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(20);
        int length = -1;
        while ((length = rFileChannel.read(buffer))!=-1) {
            log.info("readChannel-read-{}",buffer.get());
        }
        rFileChannel.close();
    }
    public static void writeChannel() throws IOException {
        //创建随机访问对象
        RandomAccessFile rFile = new RandomAccessFile("/e/ycdr/project/Java-base-study/src/main/resources/file/test-channel.txt","rw");
        //获取文件流通道(可读可写)
        FileChannel rFileChannel = rFile.getChannel();
        //获取一个字节缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(200);
        int length = -1;
        while ((length = rFileChannel.write(buffer))!=0) {
            log.info("readChannel-write-{}",length);
        }
        rFileChannel.close();
    }

    public static void nioCopyResouceFile(){
        nioCopyFile("/e/ycdr/project/Java-base-study/src/main/resources/file/1.png",
                "/e/ycdr/project/Java-base-study/src/main/resources/file/2.png");
    }

    public static void nioCopyFile(String srcPath,String destPath) {
        File srcFile=new File(srcPath);
        File destFile=new File(destPath);
        if (!destFile.exists()) {
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            int length=-1;
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while ((length=inChannel.read(buffer))!=-1) {
                //从写模式转换为读模式
                buffer.flip();
                int outLength=0;
                while ((outLength = outChannel.write(buffer))!=0) {
                    log.info("写入的字节数-{}",outLength);
                }
                buffer.clear();
            }
            outChannel.force(true);
        }catch (Exception e) {
            log.error("Exception",e);
        }finally {
            try {
                outChannel.close();
                fos.close();
                inChannel.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
