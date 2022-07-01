package com.example.paypay;

import android.util.ArrayMap;

public class MySortArrary {

    public static void main(String[] args) {

        int[] myArray = new int[]{
            5,2,6,1,9
        };


        for (int i = 0; i < myArray.length; i++) {

            for (int j = i+1; j < myArray.length; j++) {

                int temp = myArray[0];
                myArray[0] = myArray[1];
                myArray[1] = temp;


            }

        }

        System.out.println(myArray[myArray.length-1]);


    }
}
