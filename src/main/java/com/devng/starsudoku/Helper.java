package com.devng.starsudoku;

import java.util.Random;

public class Helper {

    public static Random random = new Random();

    public static void randomizeArray(int[] a) {
        int tmp = 0;
        int rV = 0;
        for (int i = 0; i < a.length; i++) {
            rV = random.nextInt(a.length - i);
            tmp = a[a.length - i - 1];
            a[a.length - i - 1] = a[rV];
            a[rV] = tmp;
        }
    }
}
