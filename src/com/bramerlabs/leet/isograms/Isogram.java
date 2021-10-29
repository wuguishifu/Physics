package com.bramerlabs.leet.isograms;

import java.util.HashMap;

public class Isogram {
    public static boolean  isIsogram(String str) {
        HashMap<Integer, Integer> chars = new HashMap<>();
        for (int i = 0; i < 130; i++) {
            chars.put(i, 0);
        }
        str.chars().forEach(c -> {
            if (c >= 'A') {
                c -= ('A'-'a');
            }
            if (chars.containsValue(c)) {
                chars.put(c, chars.get(c) + 1);
            } else {
                chars.put(c, 1);
            }
        });
        for (int i : chars.values()) {
            if (i > 1) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        System.out.println(isIsogram("Dermatoglyphics"));

    }
}