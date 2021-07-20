import org.junit.Test;

import java.io.*;
import java.util.HashSet;

public class TestClass implements Runnable{
    boolean writeFileState = false;
//    完成Hello world程序
    @Test
    public void test1(){
        char[] set ="Hello World!".toCharArray();
        HashSet<Character> characterHashSet = new HashSet<>();
        for (char item:set) {
            characterHashSet.add(item);
        }
        Object[] helloArray = characterHashSet.toArray();
        for (Object item:helloArray) {
            System.out.print(item);
        }
        System.out.println("");
    }
//将C盘下某个目录下一个文本文件的读取出来并写入D盘下指定的文件中
    @Test
    public void test2(){
        try (FileInputStream fis = new FileInputStream(new File("C:\\License.txt"));
             FileOutputStream fos = new FileOutputStream(new File("D:\\License.txt"))) {
            int len;
            byte[] buffer = new byte[4096];
            while ((len = fis.read(buffer)) > 0) {
                System.out.println(len);
                fos.write(buffer, 0, len);
            }
            fis.close();
            fos.close();
            System.out.println("write file is done");
        } catch (IOException e) {
            // ... handle IO exception
            e.printStackTrace();
        }
    }
//实现一个多线程
    @Override
    public void run() {
        test2();
    }

    @Test
    public void test3(){
        new Thread(this).start();
    }

}
