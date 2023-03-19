package Leetcode11;

public class leetcode11 {
    public static void main(String[] args) {

    }



    /** 双指针：<br>
     * 1. 能用双指针的核心在于每一次当前指针位置的情况审查一定能够得出一个结论：某一根指针移动任何步数都是可以被排除的情况，所以我们要移动另外一根指针
     * 2. 本题的左右指针分别指向最左和最右，且我们发现，由于蓄水高度取决于短指针，那么长指针任何向内移动都是可以被排除的，
     *    因为他及没有办法增加两条边的最小值，反而必然导致蓄水跨度减少，所以只要短的指针进行移动即可，只有它可能带来增益，至于有没有增益移动完之后和现有的最大值判断即可
     * 3. 证明所有情况都被考虑到（要么排除，要么被比较过）：设s[i', j']表示从i板到j板的蓄水高度(i' &lt j')
     * 3.1 不失一般性，我们以s[i', j] -> s[i', j'-1]为例:
     * 3.2 我们排除了s[i'+1, j'], ..., s[j'-1, j']
     * 3.3 s[i', j']进行了比较
     * 3.4 对于s[0, j'], ..., s[i'-1, j'],
     *     当i指针被移动时，如果j指针等于j'，则s[i, j']一定被比较过, i = 0, ..., i'-1
     *     如果j指针不等于j', 则s[i, j']一定被排除过, j' &lt j
     * 3.5 综上所述，如果指针移动了，那么这跟指针与另外一根指针组成的任意的指针对(i', j')要么被比较过，要么被排除。
     * @param height 高度数组
     * @return 最大蓄水
     */
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int max = Math.min(height[left], height[right])*(right - left);
        while(left < right){
            if(height[left] <= height[right]){
                left++;
            }
            else right--;
            max = Math.max(max, Math.min(height[left], height[right])*(right - left));
        }
        return max;
    }
}
