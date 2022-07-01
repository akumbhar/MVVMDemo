package com.example.paypay

import android.util.ArrayMap
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun findSecondLarge(){

        val myArray = intArrayOf(5, 2, 6, 1, 9)
        for (i in myArray.indices) {
            for (j in i + 1 until myArray.size) {
                val temp = myArray[i]
                myArray[i] = myArray[j]
                myArray[j] = temp
            }
        }

        println(myArray[myArray.size - 2])


        for(i in myArray.indices){

            for (j in  i+1 until  myArray.size){
                
            }
        }

    }
}
