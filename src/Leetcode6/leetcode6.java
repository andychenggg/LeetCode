package Leetcode6;

public class leetcode6 {
    public static void main(String[] args) {
        System.out.println(convert1("PAYPALISHIRING", 4));
    }

    /**
     * 用一个string builder数组每一个存一行的字符，让指针在string builder数组中上下浮动，指到哪一个就在哪一个那里连接字符串<br>
     * 运行表现:<br>
     * 4ms, 41.8MB<br>
     *
     * @param s 字符串
     * @param numRows 行数
     * @return 新字符串
     */
    public static String convert(String s, int numRows) {
        if(numRows == 1){
            System.out.println(s);
            return s;
        }
        char [] chs = s.toCharArray();
        StringBuilder [] stringBuilders = new StringBuilder[numRows];
        for (int i = 0; i < stringBuilders.length; i++) {
            stringBuilders[i] = new StringBuilder();
        }
        int direction = -1;
        int sPointer = 0;
        for(char c: chs){
            stringBuilders[sPointer].append(c);
//            System.out.println(sPointer);
            if(sPointer == numRows-1 || sPointer == 0){
                direction *= -1;
            }
            sPointer += direction;
        }
        StringBuilder result = new StringBuilder();
        for(StringBuilder str: stringBuilders){
            result.append(str);
        }
        return result.toString();
    }

    /**
     * 1. 对2n-2取余的值作为循环的迭代值，余数为某个数的时候的字符全部堆成一列，由于按顺序可以直接放在结果中<br>
     * 2. 当余数不是0或者n-1时需要注意要添加两个字符，而且要保证下标不访问越界
     * 运行表现:<br>
     * 3ms, 41.2MB<br>
     *
     * @param s 字符串
     * @param numRows 行数
     * @return 新字符串
     */
    public static String convert1(String s, int numRows) {
        if(numRows == 1){
            System.out.println(s);
            return s;
        }
        char [] chs = s.toCharArray();
        StringBuilder str = new StringBuilder();
        for(int rem = 0; rem < numRows; rem++){
            int index = rem;
            while(index < chs.length){
                str.append(chs[index]);
                int opposite = index - rem + 2 * numRows - 2 - rem;
                if(rem != 0 && rem != numRows-1 && opposite < chs.length)
                    str.append(chs[opposite]);
                index+=2 * numRows - 2;

            }
        }
        return str.toString();
    }


}
