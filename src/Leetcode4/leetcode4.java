package Leetcode4;

public class leetcode4 {

    public static void main(String[] args) {

    }
    /**
     * 很容易找到O(m+n)的算法：
     * 1. 将两个数组归并
     * 2. 得到新的有序数组
     * 3. 进而得到中位数
     * 基于有序这个特点，我们是不是可以想一下：能不能使用对分进行时间上的优化？
     * 1. 把握对分的一个核心点：每一次排除一半的可能，这是形成log复杂度的核心
     * 2. 以m+n为奇数为例，本质上就是找两个数组中第k小的数(k = (m+n)/2+1),我们不妨以k对分，即要求每次O(1)的操作可以削减k/2个不可能的值
     * 3. 用到的主要变量：m, n, pm, pn, k, m,n是数组长度，pm,pn是数组现在的指针，k是现在还需要排除的数的个数
     * 如果取出nums1[k/2], nums2[k/2]进行比较，小的那个数可以可以断定，在那个数组k/2之前的数都是不需要考虑的，对于这个数组的指针进行更新，k也相应的进行减少
     * 4. 如果情况特别好的话，这样就可以，解决问题了，但是情况可能很极端
     * 4.1. 为了防止数组越界：每次的偏移量必须是min(k/2, m - 1 - pm); 那如何保证复杂度不变呢？保证偏移量为m - 1 - pm的次数是有限次。
     *    具体做法为当pm=m-1时，如果nums1[pm]在比较中还是小的那个数，那说明中位数一定在nums2中，立刻返回nums2[(m+n)/2 - m]，若不是小的那个数，那么另外一个数组指针移动一定是k/2位的，至此能够维持二分的初心。
     * 4.2. 当有一个数组为0时，甚至不能有偏移量，因为0也是会越界的。所以当有一个数组长度为零要单独拿出来讨论
     * 5. 边界条件的处理：
     * 5.1 每次都是指针移动k/2，那么不难发现k=1时指针不在移动了，所以不妨将循环条件设为while(k>1)
     * 5.2 随后对于数组剩下的部分，我们要的处理是：取出最小的两个数，舍弃第一个，返回第二个，这个操作是不是很熟悉，没错，就是归并排序的并（具体过程可以看看代码）这样可以不用写繁杂的if-else
     * 6. 双数的情况同理，但是只能抛弃k-1个数，而且最后要取三个最小的数
     *
     * @param nums1 升序数组1
     * @param nums2 升序数组2
     * @return 合并数组的中位数
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length, k = (m+n)/2;
        int pm = 0, pn = 0;
        if((m+n) % 2 == 0){
            if(m == 0){
                return ((double) nums2[n/2 - 1] + nums2[n/2]) / 2;
            }
            if(n == 0){
                return  ((double)nums1[m/2 - 1] + nums1[m/2]) / 2;
            }
            // 双数的话就只能抛弃k-1个数了
            k--;
            while(k > 1){
                int mShift = Math.min(k/2, m - 1 - pm), nShift = Math.min(k/2, n - 1 - pn);
                if(nums1[pm + mShift] <= nums2[pn + nShift]){
                    // 如果m到达边界而且该值还要被淘汰
                    if(mShift == 0){
                        return ((double) nums2[(m+n)/2 - m - 1] + nums2[(m+n)/2 - m]) / 2;
                    }// 如果该值不被淘汰，会淘汰k/2个数，不会太大的影响复杂度
                    pm += mShift;
                    k -= mShift;
                }
                else {
                    // 如果n到达边界而且该值还要被淘汰
                    if(nShift == 0){
                        return  ((double)nums1[(m+n)/2 - n - 1] + nums1[(m+n)/2 - n])/2;
                    }// 如果该值不被淘汰，会淘汰k/2个数，不会太大的影响复杂度
                    pn += nShift;
                    k -= nShift;
                }
            }
            // k=1,这个时候我们需要取三个最小的数，舍去第一个，对第二第三个球平均
            //
            // 有点像归并排序的“并”的部分
            int [] critical = new int [3];
            int counter = 0;
            while(pm < m && pn < n && counter < 3){
                if(nums1[pm] <= nums2[pn]){
                    critical[counter++] = nums1[pm++];
                }
                else {
                    critical[counter++] = nums2[pn++];
                }
            }
            while(pm < m && counter < 3){
                    critical[counter++] = nums1[pm++];
            }
            while(pn < n && counter < 3){
                critical[counter++] = nums2[pn++];
            }
            return ((double) critical[k] + critical[k + 1]) / 2;
        }
        else{
            if(m == 0){
                return nums2[n/2];
            }
            if(n == 0){
                return nums1[m/2];
            }
            while(k > 1){
                int mShift = Math.min(k/2, m - 1 - pm), nShift = Math.min(k/2, n - 1 - pn);
                if(nums1[pm + mShift] <= nums2[pn + nShift]){
                    // 如果m到达边界而且该值还要被淘汰
                    if(mShift == 0){
                        return nums2[(m+n)/2 - m];
                    }// 如果该值不被淘汰，会淘汰k/2个数，不会太大的影响复杂度
                    pm += mShift;
                    k -= mShift;
                }
                else {
                    // 如果n到达边界而且该值还要被淘汰
                    if(nShift == 0){
                        return nums1[(m+n)/2 - n];
                    }// 如果该值不被淘汰，会淘汰k/2个数，不会太大的影响复杂度
                    pn += nShift;
                    k -= nShift;
                }
            }
            // 当k=1，我们需要取两个最小的数，舍弃第一个，返回第二个
            int [] critical = new int [2];
            int counter = 0;
            while(pm < m && pn < n && counter < 2){
                if(nums1[pm] <= nums2[pn]){
                    critical[counter++] = nums1[pm++];
                }
                else {
                    critical[counter++] = nums2[pn++];
                }
            }
            while(pm < m && counter < 2){
                critical[counter++] = nums1[pm++];
            }
            while(pn < n && counter < 2){
                critical[counter++] = nums2[pn++];
            }
            return critical[1];
        }
    }


}
