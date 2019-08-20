import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JavaMineFields {
    /**
     *String的split()返回数组时忽略
     */
    @Test
    public void stringSplitTest(){
        String str = "a,b,c,,,,";
        String[] ary = str.split(",");
        // 预期大于 3，结果是 3
        System.out.println(ary.length);//3
    }

    /**
     * 问题:list.toArray()返回Object[],
     * 强转为具体类型时会报错.
     * 解决方案:传入类型完全一致、长度为0 的空数组即可将list转为相同类型的数组
     * 说明：使用 toArray 带参方法，数组空间大小的 length：
     * 1） 等于 0，动态创建与 size 相同的数组，性能最好。
     * 2） 大于 0 但小于 size，重新创建大小等于 size 的数组，增加 GC 负担。
     * 3） 等于 size，在高并发情况下，数组创建完成之后，size 正在变大的情况下，负面影响与上相同。
     * 4） 大于 size，空间浪费，且在 size 处插入 null 值，存在 NPE 隐患
     */
    @Test
    public void listToArrayTest(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Object[] list2 = list.toArray();
        String[] list3 = list.toArray(new String[0]);
        for (String s : list3) {
            System.out.println(s);
        }
        //java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
        String[] list4 = (String[]) list.toArray();
    }

    /**
     * 使用工具类 Arrays.asList()把数组转换成集合时，不能使用其修改集合的 add/remove/clear 方法,
     * 否则会抛出 UnsupportedOperationException 异常。
     * 说明：asList 的返回对象是一个 Arrays 内部类，并没有实现集合的修改方法。Arrays.asList 体现的是适
     * 配器模式，只是转换接口，后台的数据仍是数组
     * 注意:修改数组指定位置的元素内容,集合里面也同步改变,修改集合指定位置的元素数组也跟着改变,
     * 就是不能修改集合的个数.
     */
    @Test
    public void arrayToListTest(){
        String[] str = new String[] { "a", "b" };
        List list = Arrays.asList(str);
        System.out.println(list);
        str[0] = "changed";
        list.set(1,"成功了吗?");
        System.out.println(list);
        for (String s : str) {
            System.out.println(s);
        }
//     java.lang.UnsupportedOperationException
        list.add("c");
    }


    /**
     * 在使用 Collection 接口任何实现类的 addAll()方法时，都要对输入的集合参数进行
     * NPE 判断。
     */
    @Test
    public void CollectionAddAllTest(){
        List<String> a = new ArrayList<>();
        boolean b = a.addAll(null);
    }

    /**
     * 1.ArrayList实例的 subList(不含头) 结果不可强转成 ArrayList，
     *  否则会抛出 ClassCastException 异常，
     *  即 java.util.RandomAccessSubList cannot be cast to java.util.ArrayList。
     *  说明：subList 返回的是 ArrayList 的内部类 SubList，并不是 ArrayList,
     *  而是 ArrayList 的一个视图，对于 SubList 子列表的所有操作最终会反映到原列表上
     *  注意:给subList添加元素后List为:[a, b, d, c]
     */
    @Test
    public void ArrayListSubListTest1(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        List<String> subList = list.subList(1, 2);
        System.out.println(list);
        System.out.println(subList);
        subList.add("d");
        System.out.println(list);
        System.out.println(subList);
        //强转subList1为ArrayList抛出异常
         ArrayList subList1 = (ArrayList) subList;
    }

    /**
     *【强制】在 subList 场景中，高度注意对原集合元素的增加或删除，均会导致子列表的遍
     *历、增加、删除产生 ConcurrentModificationException 异常.
     * 疑问:调用list.subList(1, 2)==>对list做增删操作==>subList不可再用了(类似与过时了???).
     */
    @Test
    public void ArrayListSubListTest2(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        List<String> subList = list.subList(1, 2);
        System.out.println(list);
        System.out.println(subList);
        //调用list.subList(1, 2)后修改list里的元素个数抛出异常
         list.add("d");
        System.out.println(list);
        // 输出subList时报java.util.ConcurrentModificationException错
        System.out.println(subList);
    }

    /**
     * 使用 Map 的方法 keySet()/values()/entrySet()返回集合对象时，
     * 不可以对其进行添加元素操作，否则会抛出 UnsupportedOperationException 异常
     */
    @Test
    public void mapTest(){
        Map<String,String> map = new HashMap();
        map.put("a","a");
        map.put(null,"b");
        Set<String> keys = map.keySet();
        System.out.println(map);
        System.out.println(keys);
        keys.add("c");
    }
    /**
     *【强制】在一个 switch 块内，每个 case 要么通过 break/return 等来终止，要么
     注释说明程序将继续执行到哪一个 case 为止；在一个 switch 块内，都必须包含一个
     default 语句并且放在最后，即使它什么代码也没有。
     说明：注意 break 是退出 switch 语句块，而 return 是退出方法体。区别在于会不会执行switch后面的代码.
     */
    @Test
    public void switchTest1(){
        String param = "str";
//        String param = "hhh";
        switch (param){
            case "str":
                System.out.println("param = str");
                return;
            case "hhh":
                System.out.println("param = hhh");
                break;
            default:
                System.out.println("param为默认值");
                break;
        }
        System.out.println("这里是switch代码块之后的代码...");
    }

    /**
     *【强制】当 switch 括号内的变量类型为 String 并且此变量为外部参数时，必须先进行 null
     * 判断。
     */
    @Test
    public void switchTest(){
//        String param = "null";
        String param = null;
        switch (param){
            case "str":
                System.out.println("param = str");
                break;
            case "null":
                System.out.println("param = null");
                break;
            default:
                System.out.println("param为默认值");
                break;
        }
    }

    /**
     *【强制】注意 Math.random() 这个方法返回是 double 类型，注意取值的范围 0≤x<1（能够
     *取到零值，注意除零异常），如果想获取整数类型的随机数，不要将 x 放大 10 的若干倍然后
     *取整，直接使用 Random 对象的 nextInt 或者 nextLong 方法
     * 注意:nextInt(10)的范围为[0,10),有需要时也要消除0
     */
    @Test
    public void  randomTest(){
        Random random = new Random();
        //[0,10)
        List<Integer> lista = new ArrayList<>();
        // [0,10]
        List<Integer> listb = new ArrayList<>();
        //[0,10)
        List<Integer> listc = new ArrayList<>();
        for (int i = 0;i<30;i++){
            int a = random.nextInt(10);//[0,10)
            lista.add(a);
            int b = random.nextInt(10)+1;//[1,11)即[0,10]
            listb.add(b);
//            方便生成10的整数倍之间的整数,即按照位数算,比如生成[1,5]就有点费劲了
//            long c = Math.round(Math.random() * 10);
            int c = (int) (Math.random() * 10);
            listc.add(c);
        }
        System.out.println(lista);
        System.out.println(listb);
        System.out.println(listc);
    }

    /**
     * 日期格式化时，传入 pattern 中表示年份统一使用小写的 y。
     *说明：日期格式化时，yyyy 表示当天所在的年，而大写的 YYYY 代表是 week in which year
     *（JDK7 之后引入的概念），意思是当天所在的周属于的年份，一周从周日开始，周六结束，
     *只要本周跨年，返回的 YYYY 就是下一年。另外需要注意：
     *⚫ 表示月份是大写的 M
     *⚫ 表示分钟则是小写的 m
     *⚫ 24 小时制的是大写的 H
     *⚫ 12 小时制的则是小写的 h
     * @throws ParseException
     */
    @Test
    public void dateTest() throws ParseException {
        String str = "2019-12-30 22:10:10";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf_avoid = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Date myDate = sdf.parse(str);
        System.out.println(sdf.format(myDate));// 2019-12-30 22:10:10
        System.out.println(sdf_avoid.format(myDate));// 2020-12-30 22:10:10

    }
}
