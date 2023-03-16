package Leetcode2;

public class Leetcode2 {

    public static void main(String[] args) {

    }
    /**
     * Definition for singly-linked list.
     * public class Leetcode2.ListNode {
     *     int val;
     *     Leetcode2.ListNode next;
     *     Leetcode2.ListNode() {}
     *     Leetcode2.ListNode(int val) { this.val = val; }
     *     Leetcode2.ListNode(int val, Leetcode2.ListNode next) { this.val = val; this.next = next; }
     * }
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode(), l4 = l3; // 有一个假头
        int carry = 0;
        while(l1 != null || l2 != null){
            int num = 0;
            if(l1 == null){
                num = l2.val+carry;
                l2 = l2.next;
            }
            else if(l2 == null){
                num = l1.val+carry;
                l1 = l1.next;
            }
            else{
                num = l1.val + l2.val + carry;
                l1 = l1.next;
                l2 = l2.next;
            }
            carry = num / 10;
            l3.next = new ListNode(num % 10);
            l3 = l3.next;
        }
        if(carry != 0)
            l3.next = new ListNode(carry);
        return l4.next;
    }
}
class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
