package fun.ycdr.io.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.IntBuffer;

/**
 * buffer 使用总结
 * 总体来说，使用Java NIO Buffer类的基本步骤如下：
 * （1）使用创建子类实例对象的allocate()方法创建一个Buffer类的实例对象。
 * （2）调用put()方法将数据写入缓冲区中。
 * （3）写入完成后，在开始读取数据前调用Buffer.flip()方法，将缓冲区转换为读模式。
 * （4）调用get()方法，可以从缓冲区中读取数据。
 * （5）读取完成后，调用Buffer.clear()方法或Buffer.compact()方法，将缓冲区转换为写模式，可以继续写入。
 */
@Slf4j
public class UseBuffer {
    static IntBuffer intBuffer = null;

    public static void main(String[] args) {
        allocateTest();
        putTest();
        flipTest();
        getTest();
        rewindTest();
        markTest();
        resetTest();
        clearTest();
    }

    /**
     * 初始化缓冲区
     */
    public static void allocateTest() {
        intBuffer = IntBuffer.allocate(20);
        log.info("intBuffer limit-> {}",intBuffer.limit());
        log.info("intBuffer position-> {}",intBuffer.position());
        log.info("intBuffer capacity-> {}",intBuffer.capacity());
    }

    /**
     * 写入缓冲区数据
     */
    public static void putTest() {
        log.info("-------------putTest-------------");
        intBuffer.put(1);
        intBuffer.put(2);
        intBuffer.put(3);
        intBuffer.put(4);
        intBuffer.put(5);
        log.info("intBuffer limit-> {}",intBuffer.limit());
        log.info("intBuffer position-> {}",intBuffer.position());
        log.info("intBuffer capacity-> {}",intBuffer.capacity());
    }

    /**
     * 翻转缓存区,从写模式转换为读模式
     */
    public static void flipTest() {
        log.info("-------------filpTest-------------");
        intBuffer.flip();
        log.info("intBuffer limit-> {}",intBuffer.limit());
        log.info("intBuffer position-> {}",intBuffer.position());
        log.info("intBuffer capacity-> {}",intBuffer.capacity());
    }

    /**
     * 读模式下-获取数据
     * 这里强调一下，在读完之后是否可以立即对缓冲区进行数据写入呢？
     * 答案是不能。现在还处于读模式，我们必须调用Buffer.clear()或Buffer.compact()方法，
     * 即清空或者压缩缓冲区，将缓冲区切换成写模式，让其重新可写。
     *
     * 此外还有一个问题：缓冲区是不是可以重复读呢？
     * 答案是可以的，既可以通过倒带方法rewind()去完成，
     * 也可以通过mark()和reset()两个方法组合实现。
     */
    public static void getTest() {
        log.info("-------------getTest-------------");
        log.info("-------------getTest--2-----------");
        for (int i = 0; i < 2; i++) {
            log.info("intBuffer get index-{}-{}",i,intBuffer.get());
        }
        log.info("intBuffer limit-> {}",intBuffer.limit());
        log.info("intBuffer position-> {}",intBuffer.position());
        log.info("intBuffer capacity-> {}",intBuffer.capacity());
        log.info("-------------getTest--3-----------");
        for (int i = 0; i < 3; i++) {
            log.info("intBuffer get index-{}-{}",i,intBuffer.get());
        }
        log.info("intBuffer limit-> {}",intBuffer.limit());
        log.info("intBuffer position-> {}",intBuffer.position());
        log.info("intBuffer capacity-> {}",intBuffer.capacity());
    }

    /**
     * 重新读取数据
     * rewind() 恢复数据重新读取 每次都市从头开始
     */
    public static void rewindTest() {
        log.info("-------------rewindTest-------------");
        intBuffer.rewind();
        log.info("intBuffer limit-> {}",intBuffer.limit());
        log.info("intBuffer position-> {}",intBuffer.position());
        log.info("intBuffer capacity-> {}",intBuffer.capacity());
    }

    /**
     * mark() 与 reset() 方法 配合 恢复数据到指定位置,重新读取
     */
    public static void markTest() {
        log.info("-------------markTest-------------");
        for (int i = 0; i < 5; i++) {
            if (i==2) {
                intBuffer.mark();
            }
            log.info("intBuffer get index-{}-{}",i,intBuffer.get());
        }
        log.info("intBuffer limit-> {}",intBuffer.limit());
        log.info("intBuffer position-> {}",intBuffer.position());
        log.info("intBuffer capacity-> {}",intBuffer.capacity());
    }

    /**
     * mark 标记 需要从那个位置重新读取
     * reset 设置该位置到 position
     */
    public static void resetTest() {
        log.info("-------------resetTest-------------");
        intBuffer.reset();
        log.info("intBuffer limit-> {}",intBuffer.limit());
        log.info("intBuffer position-> {}",intBuffer.position());
        log.info("intBuffer capacity-> {}",intBuffer.capacity());
        for (int i = 0; i < 3; i++) {
            log.info("intBuffer get index-{}-{}",i,intBuffer.get());
        }
    }

    /**
     * 读模式切换为写模式
     */
    public static void clearTest() {
        log.info("-------------clearTest-------------");
        intBuffer.clear();
        log.info("intBuffer limit-> {}",intBuffer.limit());
        log.info("intBuffer position-> {}",intBuffer.position());
        log.info("intBuffer capacity-> {}",intBuffer.capacity());
    }
}
