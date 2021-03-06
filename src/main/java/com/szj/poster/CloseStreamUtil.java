package com.szj.poster;


import java.io.Closeable;
import java.io.IOException;

/**
 * @author shenggongjie
 * @date 2021/1/19 10:14
 */
public class CloseStreamUtil {
    public static void close(Closeable stream1, Closeable stream2, Closeable stream3, Closeable stream4,String message){
            try {
            if (stream1 != null) {
                stream1.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream2 != null) {
                    stream2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stream3 != null) {
                        stream3.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (stream4!=null) {
                            stream4.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void close(Closeable stream1, Closeable stream2, Closeable stream3,String message){
        close(stream1,stream2,stream3,null,message);
    }
    public static void close(Closeable stream1, Closeable stream2,String message){
        close(stream1,stream2,null,null,message);
    }
    public static void close(Closeable stream1,String message){
        close(stream1,null,null,null,message);
    }
}
