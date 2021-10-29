package com.bramerlabs.math.sieve;

import java.util.ArrayList;

public class SieveOfEratosthenes {

    public static void main(String[] args) {
        ArrayList<Integer> primes = sieve(100);
        primes.forEach(System.out::println);
    }

    public static ArrayList<Integer> sieve(int n) {
        if (n < 2) {
            return new ArrayList<>();
        }

        ArrayList<Integer> comps = new ArrayList<>();
        ArrayList<Integer> primes = new ArrayList<>();

        for (int i = 2; i < n; i++) {
            if (comps.contains(i)) {
                continue;
            }
            for (int j = 2; j < n/i + 1; j++) {
                comps.add(j * i);
            }
            primes.add(i);
        }
        return primes;
    }

}
