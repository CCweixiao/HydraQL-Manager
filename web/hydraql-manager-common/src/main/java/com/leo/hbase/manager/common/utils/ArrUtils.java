package com.leo.hbase.manager.common.utils;

import java.util.Arrays;

/**
 * @author leojie 2020/9/30 12:20 上午
 */
public class ArrUtils {
    public static int[] intersection2(Integer[] nums1, Integer[] nums2) {
        // 确定数组 nums1 的取值范围
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int num : nums1) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
        }
        boolean[] arr = new boolean[max - min + 1];
        for (int num : nums1) {
            arr[num - min] = true;
        }
        // 判断数组 nums2 中的数是否在数组 nums1 中存在，
        // 如果存在保存在数组 tmp 中
        int[] tmp = new int[max - min + 1];
        int idx = 0;
        for (int num : nums2) {
            if (num >= min && num <= max && arr[num - min]) {
                tmp[idx++] = num;
                //保证每个值只存储一次
                arr[num - min] = false;
            }
        }

        // 修剪数组，返回结果
        int[] ret = new int[idx];
        for (int i = 0; i < idx; i++) {
            ret[i] = tmp[i];
        }
        return ret;
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{1, 2, 2, 3, 4};
        Integer[] arr2 = new Integer[]{3, 4, 5};
        Integer[] arr3 = new Integer[]{45};
        System.out.println(Arrays.toString(intersection2(arr, arr2)));

    }
}
