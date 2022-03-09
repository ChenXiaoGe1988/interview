package com.ychen.interview;

import java.util.*;

/**
 * @author demonlover
 * @date 2022/3/9 10:25
 */
public class HashCode {

    /**
     * 生成 hashCode
     * @param str
     * @param multiplier
     * @return
     */
    public static Integer hashCode(String str, Integer multiplier) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = multiplier * hash + str.charAt(i);
        }
        return hash;
    }

    /**
     * 计算 Hash 碰撞概率
     * @param multiplier
     * @param hashCodeList
     * @return
     */
    public static RateInfo hashCollisionRate(Integer multiplier, List<Integer> hashCodeList) {
        Integer maxHash = hashCodeList.stream().max(Integer::compareTo).get();
        Integer minHash = hashCodeList.stream().min(Integer::compareTo).get();

        long count = hashCodeList.stream().distinct().count();
        // 碰撞数
        int collisionCount = (int) (hashCodeList.size() - count);
        // 碰撞比率
        double collisionRate = (collisionCount * 1.0) / hashCodeList.size();
        return new RateInfo(maxHash, minHash, multiplier, collisionCount, collisionRate);
    }

    /**
     * 碰撞集合
     * @param strList
     * @param multipliers
     * @return
     */
    public static List<RateInfo> collisionRateList(Set<String> strList, Integer... multipliers) {
        List<RateInfo> rateInfoList = new ArrayList<>();
        for (Integer multiplier : multipliers) {
            List<Integer> hashCodeList = new ArrayList<>();
            for (String str : strList) {
                Integer hashCode = hashCode(str, multiplier);
                hashCodeList.add(hashCode);
            }
            rateInfoList.add(hashCollisionRate(multiplier, hashCodeList));
        }
        return rateInfoList;
    }

    public static Map<Integer, Integer> hashArea(List<Integer> hashCodeList) {
        Map<Integer, Integer> statistics = new LinkedHashMap<>();
        int start = 0;
        for (long i = 0x80000000; i <= 0x7fffffff; i += 67108864) {
            long min = i;
            long max = min + 67108864;
            // 筛选出每个格子里的哈希值数量，java8流统计；https://bugstack.cn/itstack-demo-any/2019/12/10/%E6%9C%89%E7%82%B9%E5%B9%B2%E8%B4%A7-Jdk1.8%E6%96%B0%E7%89%B9%E6%80%A7%E5%AE%9E%E6%88%98%E7%AF%87(41%E4%B8%AA%E6%A1%88%E4%BE%8B).html
            int num = (int) hashCodeList.parallelStream().filter(x -> x >= min && x < max).count();
            statistics.put(start++, num);
        }
        return statistics;
    }

    public static Map<Integer, Integer> hashArea(Set<String> strList, Integer multiplier){
        List<Integer> hashCodeList = new ArrayList<>();
        for (String str : strList) {
            Integer hashCode = hashCode(str, multiplier);
            hashCodeList.add(hashCode);
        }
        return hashArea(hashCodeList);
    }

    public static void main(String[] args) {
        String str = "bc";
        char c = str.charAt(0);
        System.out.println(c);
    }
}