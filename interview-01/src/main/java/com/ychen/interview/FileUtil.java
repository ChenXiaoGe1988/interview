package com.ychen.interview;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件工具类
 * @author demonlover
 * @date 2022/3/9 10:28
 */
public class FileUtil {

    /**
     * 读取本地文件【单词表】
     * @param url 单词表.txt文件
     * @return 单词集合【去重】
     */
    public static Set<String> readWordList(String url) {
        Set<String> set = new HashSet<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(url), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                // 根据跳格符分割
                String[] ss = line.split("\t");
                set.add(ss[1]);
            }
            bufferedReader.close();
            inputStreamReader.close();
        } catch (Exception e) {
            return null;
        }
        return set;
    }
}
