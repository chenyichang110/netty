package com.poker;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class PokerTest {

  static String str = "XX23456789TJQKA";

  public static void main(String[] args) {
    System.out.println(str.indexOf("A"));
  }


  public void sort(List<Poker> a, List<Poker> b) {

  }

  /**
   * 是否是同花，即花色都一样
   */
  public boolean isFlush(List<Poker> pokers) {
    int color = pokers.get(0).color;
    for (int i = 1; i < pokers.size(); i++) {
      if (color != pokers.get(i).color) {
        return false;
      }
    }
    return true;
  }

  /**
   * 是否是同花 AKQJT
   * @param pokers 牌
   */
  public boolean isRoyalFlush(List<Poker> pokers) {

    //同花
    if (!isFlush(pokers)) {
      return false;
    }

    //看数字是否是AKQJT
    List<Integer> number = pokers.stream().map(poker -> poker.number).collect(Collectors.toList());
    if (!number.contains(10) ||
        !number.contains(11) ||
        !number.contains(12) ||
        !number.contains(13) ||
        !number.contains(14)) {
      return false;
    }
    return true;

  }


  /**
   * 是否是同花顺，如果是同花顺返回顺子最大的数字
   * @param pokers
   * @return 0:代表不是同花顺
   */
  public int isFlushStaight(List<Poker> pokers) {
    //是否同花
    if (!isFlush(pokers)) {
      return 0;
    }
    //是否是顺子
    List<Integer> number = pokers.stream().map(poker -> poker.number).collect(Collectors.toList());
    //排序
    Collections.sort(number);
    if (number.get(4) == number.get(0) + 4) {
      return number.get(4);//返回最大数字
    }
    //还有一种 最小的顺子 需要单独考虑
    //number:  2 3 4 5 14(A)
    if (number.get(0) == 2 && number.get(4) == 14) {
      return 5;//最大数字5，此时A已被当做最小
    }
    return 0;
  }

  /**
   * 是否是4张一样的牌
   * AAAAB
   * @param pokers
   * @return 0:不是，其他返回值代表是有4张得值的大小
   */
  public int isFourOfAKind(List<Poker> pokers) {
    List<Integer> number = pokers.stream().map(poker -> poker.number).collect(Collectors.toList());

    //是否是4张一样
    int num = number.get(0);
    Set<Integer> set = new HashSet();
    for (int i : number) {
      if (!set.add(number.get(i))) {
        num = number.get(i);
      }
    }
    if (set.size() != 2){
      return 0;
    }
    //返回4张的值,还未实现
    return 0;
  }

  /**
   * 是否 3张一样，2张一样
   * @param pokers
   */
  public void isFullHouse(List<Poker> pokers) {

  }


}

class A {

  List<Poker> list;

}

class Poker {

  /**
   * 1:红桃
   * 2:黑桃
   * 3:方片
   * 4:梅花
   */
  int color;

  /**
   *  String str = "XX23456789TJQKA";
   *  通过indexof位置来转换成int 好比较大小
   *  2:2 .......T:10  J:11  Q:12  K:13 A:14
   */
  int number;
}
