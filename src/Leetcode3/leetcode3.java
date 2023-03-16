package Leetcode3;

import java.util.HashSet;
import java.util.Set;

public class leetcode3 {

    public static void main(String[] args) {

    }

    /**
     * 滑块问题一般有以下几个条件：
     * 1. 问题存在或可简化成一个序列
     * 2. 求满足某种要求的最长子序列
     * 3. 如果一个子序列满足，则子序列的子序列无需查看
     * 滑块问题的一般解决方法：
     * 1. 初始时左右两根指针都在序列头部
     * 2. 右指针不断往后寻找，直至不满足要求，此时左指针往后移，直至满足要求
     * 3. 重复第二步直至右指针走到最后，需要左指针移动的时候要记得维护最长子序列是什么
     * 4. 跳出循环后将原有最长子序列和现在左右指针形成的子序列进行判断，
     *    决定谁才是最终的最长子序列（防止左指针少了一次移动，右指针就出界了的情况）
     *
     * @param s 字符串
     * @return 最长序列的长度
     */

    public int lengthOfLongestSubstring(String s) {
        Set<Character> hs = new HashSet<>();
        char [] str = s.toCharArray();
        int left = 0, right = 0;
        int max = 0;
        while(right != str.length){
            if(hs.contains(str[right])){
                max = Math.max(max, right - left);
                hs.remove(str[left++]);
            }
            else{
                hs.add(str[right++]);
            }
        }
        max = Math.max(max, right - left);
        return max;
    }
}
