package lesson5;

import java.util.Arrays;

public class MainThread {
    static final int Size = 10_000_000;
    static final int Half = Size / 2;

    public static void main(String[] args) {
        float[] arr = new float[Size];
        fillArr(arr);
        formula1(arr);

        fillArr(arr);
        formula2(arr);
    }

    public static void fillArr(float[] arr) {
        Arrays.fill(arr, 1);
    }

    public static void formula1(float[] arr) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long endTime = System.currentTimeMillis();
            System.out.println("Formula1 Total execution time" + (endTime - startTime) + "ms");
            System.out.println(arr[Half]);
    }

    public static void formula2(float[] arr) {
        long startTime = System.currentTimeMillis();
        float[] a1 = new float[Half];
        float[] a2 = new float[Half];

        System.arraycopy(arr,0,a1, 0, Half);
        System.arraycopy(arr, Half, a2, 0, Half);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float) (a2[i] * Math.sin(0.2f + (i+Half) / 5) * Math.cos(0.2f + (i+Half) / 5) * Math.cos(0.4f + (i+Half) / 2));
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();

        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1,0,arr, 0, Half);
        System.arraycopy(a2,0,arr, Half, Half);

        long endTime = System.currentTimeMillis();
        System.out.println("Formula2 Total execution time" + (endTime - startTime) + "ms");
        System.out.println(arr[Half]);
    }
}
