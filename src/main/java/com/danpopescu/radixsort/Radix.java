package com.danpopescu.radixsort;

import java.util.*;

public class Radix {

    public static void main(String[] args) {
        System.out.println(sort(List.of(1000, 4, 25, 319, 88, 51, 3430, 8471, 701, 1, 2989, 657, 713)));
    }

    @SuppressWarnings("unchecked")
    public static List<Integer> sort(Collection<Integer> numbers) {
        Queue<Integer> sortedNumbers = new ArrayDeque<>(numbers);
        Queue<Integer>[] queues = (Queue<Integer>[]) new Queue[10];
        for (int i = 0; i < 10; i++) {
            queues[i] = new ArrayDeque<>();
        }

        int iterations = getMaxNumberOfDigits(numbers);

        for (int i = 0; i < iterations; i++) {
            while (!sortedNumbers.isEmpty()) {
                int number = sortedNumbers.poll();
                int digitOnCurrentPosition = getKthDigit(number, i);
                queues[digitOnCurrentPosition].offer(number);
            }
            for (Queue<Integer> queue : queues) {
                while (!queue.isEmpty()) {
                    sortedNumbers.offer(queue.poll());
                }
            }
        }

        return new ArrayList<>(sortedNumbers);
    }

    private static int getKthDigit(int number, int k) {
        for (int i = 0; i < k; i++) {
            number /= 10;
        }
        return number % 10;
    }

    private static int getMaxNumberOfDigits(Collection<Integer> numbers) {
        return numbers.isEmpty()
                ? -1
                : numbers.stream().map(Radix::getNumberOfDigits).max(Integer::compareTo).get();
    }

    private static int getNumberOfDigits(int n) {
        if (n == 0) {
            return 1;
        }
        if (n < 0) {
            n = Math.abs(n);
        }

        int numberOfDigits = 0;
        while (n > 0) {
            n /= 10;
            numberOfDigits++;
        }

        return numberOfDigits;
    }
}
