package com.atguigu.hashcode;

import org.junit.Test;

public class HashCodeTest {

  @Test
  public void Integer_hashCode() {
    Integer one = 20;
    System.out.println(one.hashCode());//20
    /**
     * 可以看源码知道：Integer 的 hashCode 就是它的value
     *     @Override
     *     public int hashCode() {
     *         return Integer.hashCode(value);
     *     }
     *     public static int hashCode(int value) {
     *        return value;
     *     }
     */
  }

  @Test
  public void String_hashCode() {
    String str = "123";
    System.out.println(str.hashCode());//48690

    int h = 3;
    System.out.println(h >>> 16);
    /**
     * 根据源码可得：依次遍历每个字符得到每个字符的ASCII值，
     * 递归的运行 h = 31 * h + val[i]得到最后的HashCode
     *
     * h初始值：0
     * 1  ASCII：49  h=31*0+49=49
     * 2  ASCII：50  h=31*49+50=1569
     * 3  ASCII：51  h=31*1569+1569=48690
     * 源码：
     *     public int hashCode() {
     *         int h = hash;
     *         if (h == 0 && value.length > 0) {
     *             char val[] = value;
     *
     *             for (int i = 0; i < value.length; i++) {
     *                 h = 31 * h + val[i];
     *             }
     *             hash = h;
     *         }
     *         return h;
     *     }
     */
  }
}
