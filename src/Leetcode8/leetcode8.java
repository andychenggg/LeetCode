package Leetcode8;

public class leetcode8 {
    public static void main(String[] args) {

    }

    /**
     * 没什么好说的，题目简单，坑一大堆，又不讲清楚，质量很差<br>
     * 执行效果：<br>
     * 1ms, 41.3MB
     * @param s
     * @return
     */
    public int myAtoi(String s){
        char [] chs = s.toCharArray();
        int sign = 1;
        boolean hasNumber = false, hasSign = false;
        int result = 0, mq = Integer.MAX_VALUE / 10, mRem = Integer.MAX_VALUE % 10;
        for (char ch : chs) {
            if (!hasNumber) {
                if (ch == '-' || ch == '+'){
                    if(hasSign){
                        return 0;
                    }
                    if(ch == '-'){
                        sign = -1;
                    }
                    hasSign = true;
                }
                else if ('0' <= ch && ch <= '9') {
                    result = ch - '0';
                    hasNumber = true;
                }
                else if(hasSign || 'a' <= ch && ch <= 'z' || ch == '.'){
                    return 0;
                }

            } else {
                if ('0' <= ch && ch <= '9') {
                    int digit = ch - '0';
                    if (result > mq || result == mq && digit > mRem) {
                        return sign * Integer.MAX_VALUE + (sign - 1) / 2;
                    } else {
                        result = result * 10 + digit;
                    }
                } else break;
            }
        }
        return sign * result;
    }
}
