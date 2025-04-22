package com.ku.kuhamsappointmentservice.configuration.security;

public class test {
    public static void main(String[] args) {
        int i = 0;
        int n = 0;
        int j = 0;
        int[] A = new int[]{};
        while (i < n-1) {
            j = i + 1;
            while (j < n) {
                int temp = 0;
                if (A[i] < A[j]) {
                     temp = A[i];
                     A[i] = A[j];
                     A[j] = temp;
                }
            }
            i = i + 1;
        }
    }
}
