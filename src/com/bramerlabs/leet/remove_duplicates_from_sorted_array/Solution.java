package com.bramerlabs.leet.remove_duplicates_from_sorted_array;

public class Solution {

    public int removeDuplicates(int[] nums) {
        if (nums.length < 2) return nums.length;
        int i = 0;
        int t = nums[i];
        int n;
        int j = 0;
        while (j < nums.length) {
            if (i == 0) {
                i++;
            }
            n = nums[j];
            if (n <= t) {
                j++;
            } else {
                t = n;
                nums[i] = t;
                i++;
            }
        }
        return i;
    }

}
