package Leetcode9;

public class leetcode9 {
    public static void main(String[] args) {

    }

    public boolean isPalindrome(int x) {
        char [] chs = String.valueOf(x).toCharArray();
        int left = 0, right = chs.length - 1;
        while (left < right){
            if(chs[left++] != chs[right--]){
                return false;
            }
        }
        return true;
    }
}
