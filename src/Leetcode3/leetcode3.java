package Leetcode3;

import java.util.HashSet;
import java.util.Set;

public class leetcode3 {

    public static void main(String[] args) {

    }

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
