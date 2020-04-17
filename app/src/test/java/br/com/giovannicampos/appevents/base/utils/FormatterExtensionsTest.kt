package br.com.giovannicampos.appevents.base.utils

import org.junit.Assert
import org.junit.Test

class FormatterExtensionsTest {

    @Test
    fun `To currency, when it passed a number value, then returns a currency string value formatted`() {
        // ARRANGE
        val expectedPattern = "#,###.00"
        val expectedCurrency = "1.530,57"
        val currency = 1530.57

        // ACT
        val actualCurrency = currency.toCurrency(expectedPattern)

        // ASSERT
        Assert.assertEquals(expectedCurrency, actualCurrency)
    }

    @Test
    fun `Timestamp to day of week, when it is passed a timestamp, then returns a day of week`() {
        // ARRANGE
        val expectedDay = "20"
        val expectedTimestamp = 1534784400000

        // ACT
        val actualDayOfWeek = expectedTimestamp.timestampToDayOfMonth()

        // ASSERT
        Assert.assertEquals(expectedDay, actualDayOfWeek)
    }

    @Test
    fun `Timestamp to short month, when it is passed a timestamp, then returns a short month`() {
        // ARRANGE
        val expectedMonth = "AGO"
        val expectedTimestamp = 1534784400000

        // ACT
        val actualMonth = expectedTimestamp.timestampToShortMonth()

        // ASSERT
        Assert.assertEquals(expectedMonth, actualMonth)
    }

    @Test
    fun `Timestamp to year, when it is passed a timestamp, then returns a year`() {
        // ARRANGE
        val expectedYear = "2018"
        val expectedTimestamp = 1534784400000

        // ACT
        val actualYear = expectedTimestamp.timestampToYear()

        // ASSERT
        Assert.assertEquals(expectedYear, actualYear)
    }

    @Test
    fun `Timestamp to date full, when it is passed a timestamp, then returns a date formatted`() {
        // ARRANGE
        val expectedYear = "20 de agosto de 2018"
        val expectedTimestamp = 1534784400000

        // ACT
        val actualYear = expectedTimestamp.timestampToDateFull()

        // ASSERT
        Assert.assertEquals(expectedYear, actualYear)
    }
}