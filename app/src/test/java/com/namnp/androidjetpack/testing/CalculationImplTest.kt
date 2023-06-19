package com.namnp.androidjetpack.testing

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class CalculationImplTest{
    private lateinit var calculation: CalculationImpl

    @Before
    fun setUp() {
        calculation = CalculationImpl()
    }

    @Test
    fun calculateCircumference_radiusGiven_returnsCorrectResult(){
        val result = calculation.calculateCircumference(2.1)
        assertThat(result).isEqualTo(13.188)

    }

    @Test
    fun calculateCircumference_zeroRadius_returnsCorrectResult(){
        val result = calculation.calculateCircumference(0.0)
        assertThat(result).isEqualTo(0.0)

    }

    @Test
    fun calculateArea_radiusGiven_returnsCorrectResult(){
        val result = calculation.calculateArea(2.1)
        assertThat(result).isEqualTo(13.8474)

    }

    @Test
    fun calculateArea_zeroRadius_returnsCorrectResult(){
        val result = calculation.calculateCircumference(0.0)
        assertThat(result).isEqualTo(0.0)

    }

}