package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = 0;
        for(int i = 2; i < n; i++){
            boolean flag = true;
            for(int j = 2; j * j <= i; j++){
                if(i % j == 0){
                    flag = false;
                    break;
                }
            }
            if(flag){
                if (k != 0) System.out.print(" ");
                k++;
                System.out.print(i);
            }
        }
    }
}
