package Leetcode7;

public class leetcode7 {
    public static void main(String[] args) {

    }

    /**
     * 省时间的一点办法：将Integer.MAX_VALUE / 10，Integer.MAX_VALUE % 10先存起来<br>
     * 运行结果：<br>
     * 1ms, 38.3MB
     * @param x 整数
     * @return 整数倒序形式
     */
    public int reverse(int x) {
        int sign = x >= 0 ? 1: -1;
        x = Math.abs(x);
        int result = 0, mq = Integer.MAX_VALUE / 10, mRem = Integer.MAX_VALUE % 10;
        while(x > 0){
            int rem = x % 10;
            if(result > mq || result == mq && rem > mRem)
                return 0;
            result =  result * 10 + rem;
            x /= 10;
        }
        return sign * result;
    }

}
