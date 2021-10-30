package com.bramerlabs.leet.roman_to_int;

import java.util.Map;

public class RomanToInt {

    public static Map<Character, Integer> romans;
    static {
        romans = Map.of(
                'I', 1,
                'V', 5,
                'X', 10,
                'L', 50,
                'C', 100,
                'D', 500,
                'M', 1000
        );
    }

    public static void main(String[] args) {
        System.out.println("MMCCCLII, " + 2352 + ", " + romanToInt("MMCCCLII"));
        System.out.println("DCXIV, "    + 614  + ", " + romanToInt("DCXIV"));
        System.out.println("CMLXXXI, "  + 981 + ", " + romanToInt("CMLXXXI"));
    }

    public static int romanToInt(String s) {
        int result = 0;
        int currentRoman;
        for(int i = 0; i < s.length()-1; i++){
            currentRoman = romans.get(s.charAt(i));
            if(currentRoman < romans.get(s.charAt(i+1))){
                result -= currentRoman;
            }else {
                result += currentRoman;
            }
        }
        return result + romans.get(s.charAt(s.length()-1));
    }

}
