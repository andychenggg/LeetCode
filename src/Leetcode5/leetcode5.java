package Leetcode5;

import java.util.Arrays;

public class leetcode5 {

    public static void main(String[] args) {
//        System.out.println(check("xaabacxcabaaxcabaax", 13));
    }

    /**
     * Rabin-Karp算法，以整数溢出作为自然取模，字符集取26个小写字母，但是为了选取一个素数，选取了29<br>
     * 1. 本质上就是当成一个进制为29的数去计算十进制表示，<br>
     * 2. 在数据量不大的情况下可以忽略哈希碰撞（至少在测试样例中没碰过），但是hashcode是为了降低哈希碰撞和压缩hashcode的区间做了很多额外的东西，所以还是自己写，不要直接调一个hashcode方法<br>
     * 3. 如果一个字符串有奇数的子串是回文的，那么肯定有比这个奇数小的数，且还是回文的；偶数也是类似的，这里可以使用二分法。<br>
     * 4. 当不用考虑哈希碰撞的之后复杂度是O(n)的，但是如果为了严谨一点，也可以在碰撞时进行检查，虽然说复杂度变成O(mn)，但是碰撞的概率其实很少发生。<br>
     *
     * 5. 执行结果<br>
     * 8ms, 42.1mb<br>
     * @param s
     * @return
     */
    public static String longestPalindrome(String s){
        int length = s.length();
        String maxEven = "", maxOdd = "";
        char [] s1 = s.toCharArray();
        char [] s2 = new StringBuilder(s).reverse().toString().toCharArray();
        if(length % 2 == 0){
            // even number: 2...length, base = 1...length/2
            int left = 1, right = length/2;
            while(left <= right){
                int mid =(left + right) / 2;
                String subStr = check(s, s1, s2,  2 * mid);
                if(subStr.equals("")){
                    right = mid - 1;
                }
                else{
                    maxEven = subStr;
                    left = mid + 1;
                }
            }
            //odd number: 1...length - 1, base = 1...length/2
            left = 1; right = length/2;
            while(left <= right){
                int mid =(left + right) / 2;
                String subStr = check(s, s1, s2,  2 * mid - 1);
                if(subStr.equals("")){
                    right = mid - 1;
                }
                else{
                    maxOdd = subStr;
                    left = mid + 1;
                }
            }
        }
        else { //length % 2 == 1
            // even number: 2...length - 1, base = 1...length/2
            int left = 1, right = length/2;
            while(left <= right){
                int mid =(left + right) / 2;
                String subStr = check(s, s1, s2,  2 * mid);
                if(subStr.equals("")){
                    right = mid - 1;
                }
                else{
                    maxEven = subStr;
                    left = mid + 1;
                }
            }
            //odd number: 1...length, base = 0...length/2
            left = 0; right = length/2;
            while(left <= right){
                int mid =(left + right) / 2;
                String subStr = check(s, s1, s2, 2 * mid + 1);
                if(subStr.equals("")){
                    right = mid - 1;
                }
                else{
                    maxOdd = subStr;
                    left = mid + 1;
                }
            }
        }
        return maxEven.length() > maxOdd.length() ? maxEven : maxOdd;
    }
    public static String check(String s, char [] s1, char [] s2, int checkLength){
        // if we find a palindromic substring, then return it. Else return ""
        //calculate hash by myself
        long [] hash1 = new long[s.length() - checkLength + 1];
        long [] hash2 = new long[s.length() - checkLength + 1];
        long d = 1, sigma = 29;
        for(int i = 0; i< checkLength; i++){
            hash1[0] = (hash1[0] * sigma + s1[i]);
            hash2[0] = (hash2[0] * sigma + s2[i]);
            d *= sigma;
//            System.out.println(d);
        }
        for (int i = 1; i < hash1.length; i++) {
            hash1[i] = (hash1[i - 1] * sigma - s1[i - 1] * d + s1[i - 1 + checkLength] );
            hash2[i] = (hash2[i - 1] * sigma - s2[i - 1] * d + s2[i - 1 + checkLength] );
        }
        // reverse the hash2;
        for(int i = 0; i < hash2.length / 2; i++){
            long c = hash2[i];
            hash2[i] = hash2[hash2.length - i - 1];
            hash2[hash2.length - i - 1] = c;
        }
//        for (int i = 0; i < hash1.length; i++) {
//            System.out.println("hash1:"+hash1[i]+" hash2:"+hash2[i]);
//        }
        for(int i = 0; i< hash1.length; i++){
            if(hash1[i] == hash2[i]){
                return s.substring(i, i+checkLength);
            }
        }
        return "";
    }

    /**
     * 动态规划：创建一个二维数组isP[][]，表示isP[i][j]表示s[i, j]是不是回文子串。<br>
     * 1. 我们只需要讨论j >= i即可，初始条件为：<br>
     * 1.1 当i == j时，isP[i][j]一定是true<br>
     * 1.2 当i == j-1时，isP[i][j]等价于s[i] == s[j]<br>
     * 2. 状态方程：<br>
     * 2.1 当字符串长度为奇数时((i+j) % 2 == 1 && j-i >= 2)，它为回文串当且仅当[i+1, j-1]是回文串且s[i]等于s[j]<br>
     * 2.2 当字符串长度为偶数时((i+j) % 2 == 0 && j-i >= 3)，它为回文串当且仅当[i+1, j-1]是回文串且s[i]等于s[j]<br>
     * 3. 执行结果<br>
     * 148ms, 44.6mb<br>
     * @param s 字符串
     * @return 最长回文子串
     */
    public static String longestPalindrome1(String s){
        boolean [][] isP = new boolean[s.length()][s.length()];
        char [] chs = s.toCharArray();
        int begin = 0, end = 0;
        for(int i = 0; i < s.length(); i++){
            isP[i][i] = true;
            if(i + 1 < s.length()){
                isP[i][i+1] = chs[i] == chs[i+1];
                if(isP[i][i+1]){
                    begin = i;
                    end = i+1;
                }
            }
        }
        for(int k = 2; k < s.length(); k++){
            for(int i = 0; i < s.length(); i++){
                int j = i + k;
                if(j < s.length()){
                    isP[i][j] = isP[i+1][j-1] && chs[i] == chs[j];
                    if(isP[i][j]){
                        begin = i;
                        end = j;
                    }

                }
            }
        }
        return s.substring(begin, end+1);
    }

    /**
     * 中心扩散<br>
     * 1. 尝试以每一个点为中心，然后往左右扩散，遇到不相同就退出（奇数）<br>
     *    如果这个点和右边的点相同，也可以在偶数范围内尝试扩散<br>
     * 2. 在回文串占实际字符串长度很大的时候，会做了很多无用功，不如动态规划；但是如果回文串很短而字符串很长，及时退出反而使得时间花费更少<br>
     * 3. 执行结果<br>
     * 7ms, 41.2mb<br>
     * @param s 字符串
     * @return 最长回文子串
     */
    public static String longestPalindrome2(String s){
        char [] chs = s.toCharArray();
        int maxLeft = 0, maxRight = 0;
        for(int i = 0; i < s.length(); i++){ // 计算以i为中心的最长回文子串
            int left = i, right = i;
            // 先对奇数操作
            while(left > -1 && right < s.length() && chs[left] == chs[right]) {
                left--;
                right++;
            }
            if(right - left - 2 > maxRight - maxLeft){
                maxLeft = left + 1;
                maxRight = right - 1;
            }
            // 处理偶数
            if(i+1 < s.length() && chs[i] == chs[i+1]){
                left = i;
                right = i + 1;
                while(left > -1 && right < s.length() && chs[left] == chs[right]) {
                    left--;
                    right++;
                }
                if(right - left - 2 > maxRight - maxLeft){
                    maxLeft = left + 1;
                    maxRight = right - 1;
                }
            }
        }

        return s.substring(maxLeft, maxRight+1);
    }
}
