package com.bramerlabs.leet.sum_of_digits;

public class DRoot {
    public static int digital_root(int n) {
        return (n != 0 && n%9 == 0) ? 9 : n % 9;
    }

    public static int digital_root1(int n) {
        return (1 + (n - 1) % 9);
    }

    public static int digital_root2(int n) {
        return --n % 9 + 1;
    }

    public static void main(String[] args) {
        System.out.println(digital_root1(459)); // 18 -> 9
        System.out.println(digital_root1(9999)); // 36 -> 9
        System.out.println(digital_root1(25255)); // 19 -> 10 -> 1
        System.out.println(digital_root1(26758)); // 28 -> 10 -> 1
        System.out.println(digital_root1(14));
    }
}