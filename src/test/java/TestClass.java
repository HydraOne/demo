import cn.geny.Animal;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass extends Thread{
    int speed;
    private static String winer;
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
        Random random = new Random();
        for (int index = 0; index < set.length; index++) {
            while (true){
                Object temp;
                if ((temp = helloArray[random.nextInt(helloArray.length)]).equals(set[index])){
                    System.out.print(temp);
                    break;
                }
            }
        }
    }
//将C盘下某个目录下一个文本文件的读取出来并写入D盘下指定的文件中
    @Test
    public void test2(){
//        try(){}与try{}的区别是对于流而言前者会自动调用close
        try (FileInputStream fis = new FileInputStream(new File("C:\\License.txt"));
             FileOutputStream fos = new FileOutputStream(new File("D:\\License.txt"))) {
            int len;
            byte[] buffer = new byte[4096];
            while ((len = fis.read(buffer)) > 0) {
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

    @Test
    public void test2_1(){
        File file = new File("C:\\License.txt");
        try {
            Reader reader = new FileReader(file);
            char[] charBuffer = new char[128];
            while ((reader.read(charBuffer))!=-1){
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //实现一个多线程
    @Override
    public void run() {
        int total_distance = 100;
        int distance = 0;
        while (distance < total_distance) {
            System.out.println(this.getName() + "距离终点还剩" + (total_distance - distance) + "M");
            distance = distance + this.speed;
            if (this.getName() == "rabbit" && distance == 27) {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (winer==null){
            winer = this.getName();
        }
    }
    @Test
    public void test3() throws InterruptedException {
        TestClass rabbit = new TestClass();
        rabbit.setName("rabbit");
        rabbit.speed = 3;
        rabbit.start();
        TestClass tortoise = new TestClass();
        tortoise.setName("tortoise");
        tortoise.speed = 1;
        tortoise.start();
        Thread.currentThread().sleep(100);
        System.out.println(winer);
    }



// 反射
    @Test
    public void test5() throws Exception {
        System.out.println("A(无参构造函数)--加载类、反射类的构造函数、利用构造函数new一个Animal实例instance--");

        //1、加载类,指定类的完全限定名：包名+类名
        Class c1 = Class.forName("cn.geny.Animal");
        System.out.println(c1); //打印c1，发现值和字节码中的类的名称一样

        //2、解刨(反射)类c1的公开构造函数，且参数为null
        Constructor ctor1 = c1.getConstructor();

        //3、构造函数的用途，就是创建类的对象（实例）的
        //除了私有构造函数外（单列模式，禁止通过构造函数创建类的实例，保证一个类只有一个实例）
        //ctor1.newInstance()默认生成一个Object对象,我们需要转化成我们要的Animal类对象
        Animal a1 = (Animal) ctor1.newInstance();

        //4、证明一下a1确实是Animal的实例，我们通过访问类中的变量来证明
        System.out.println(a1.name);

        System.out.println("A(有参构造函数)--加载类、反射类的构造函数、利用构造函数new一个Animal实例instance--");

        //2.b、 解刨(反射)类c1的公开构造函数，参数为string和int
        Constructor ctor2 = c1.getConstructor(String.class, int.class);
        Animal a2 = (Animal) ctor2.newInstance("cat", 20);

        System.out.println("B--获得本类中的所有的字段----------------------------");

        //5、获得类中的所有的字段	包括public、private和protected，不包括父类中申明的字段
        Field[] fields = c1.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        System.out.println("C--获得本类中的所有公有的字段，并获得指定对象的字段值-----");

        //6、获得类中的所有的公有字段
        fields = c1.getFields();
        for (Field field : fields) {
            System.out.println(field + ", 字段值 = " + field.get(a1));
            //注意：私有变量值，无法通过field.get(a1)进行获取值
            //通过反射类中的字段name，修改name的值（注意，原值在类中name="Dog"）
            //如果，字段名称等于"name"，且字段类型为String，我们就修改字段的值，也就是类中变量name的值
            if (field.getName() == "name" && field.getType().equals(String.class)) {
                String name_new = (String) field.get(a1); //记得转换一下类型
                name_new = "哈士奇"; //重新给name赋值
                field.set(a1, name_new); //设置当前实例a1的name值，使修改后的值生效
            }
        }

        System.out.println("利用反射出的字段，修改字段值，修改后的name = "+a1.name);
        System.out.println("D--获取本类中的所有的方法--------------------");

        //7、获取本类中所有的方法 包括public、private和protected，不包括父类中申明的方法
        Method[] methods = c1.getDeclaredMethods();
        for (Method m : methods) {
            System.out.println(m);//我们在类Animal中只定义了一个public方法，sayName
        }

        System.out.println("E--获取本类中的所有的公有方法，包括父类中和实现接口中的所有public方法-----------");

        //8、获取类中所有公有方法，包括父类中的和实现接口中的所有public 方法
        methods = c1.getMethods();
        for (Method m : methods) {
            System.out.println(m);//我们在类Animal中只定义了一个public方法，sayName
        }

        System.out.println("F--根据方法名称和参数类型获取指定方法，并唤起方法：指定所属对象a1，并给对应参数赋值-----------");

        //9、唤起Method方法(执行) getMethod:第一个参数是方法名，后面跟方法参数的类
        Method sayName = c1.getMethod("sayName", String.class);
        System.out.println(sayName.invoke(a1, "riemann"));
    }

//    正则表达式
    @Test
    public void test6(){
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

        Pattern regex = Pattern.compile(check);

        Matcher matcher = regex.matcher("dffd.fdf@qq.com");

        boolean isMatched = matcher.matches();

        System.out.println(isMatched);
    }
}