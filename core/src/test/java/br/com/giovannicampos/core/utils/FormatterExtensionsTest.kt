package br.com.giovannicampos.core.utils

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
    fun `Timestamp to day of week, when it is passed a zero value, then returns a default day`() {
        // ARRANGE
        val expectedDay = "31"
        val expectedTimestamp = 0L

        // ACT
        val actualDayOfWeek = expectedTimestamp.timestampToDayOfMonth()

        // ASSERT
        Assert.assertEquals(expectedDay, actualDayOfWeek)
    }

    @Test
    fun `Timestamp to day of week, when it is passed a null value, then returns null`() {
        // ARRANGE
        val expectedTimestamp: Long? = null

        // ACT
        val actualDayOfWeek = expectedTimestamp?.timestampToYear()

        // ASSERT
        Assert.assertNull(actualDayOfWeek)
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
    fun `Timestamp to short month, when it is passed a zero value, then returns a default month`() {
        // ARRANGE
        val expectedMonth = "DEZ"
        val expectedTimestamp = 0L

        // ACT
        val actualMonth = expectedTimestamp.timestampToShortMonth()

        // ASSERT
        Assert.assertEquals(expectedMonth, actualMonth)
    }

    @Test
    fun `Timestamp to short month, when it is passed a null value, then returns null`() {
        // ARRANGE
        val expectedTimestamp: Long? = null

        // ACT
        val actualMonth = expectedTimestamp?.timestampToYear()

        // ASSERT
        Assert.assertNull(actualMonth)
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
    fun `Timestamp to year, when it is passed a zero value, then returns a default year`() {
        // ARRANGE
        val expectedYear = "1969"
        val expectedTimestamp = 0L

        // ACT
        val actualYear = expectedTimestamp.timestampToYear()

        // ASSERT
        Assert.assertEquals(expectedYear, actualYear)
    }

    @Test
    fun `Timestamp to year, when it is passed a null value, then returns null`() {
        // ARRANGE
        val expectedTimestamp: Long? = null

        // ACT
        val actualYear = expectedTimestamp?.timestampToYear()

        // ASSERT
        Assert.assertNull(actualYear)
    }

    @Test
    fun `Timestamp to date full, when it is passed a timestamp, then returns a date formatted`() {
        // ARRANGE
        val expectedDateFull = "20 de agosto de 2018"
        val expectedTimestamp = 1534784400000

        // ACT
        val actualDateFull = expectedTimestamp.timestampToDateFull()

        // ASSERT
        Assert.assertEquals(expectedDateFull, actualDateFull)
    }

    @Test
    fun `Timestamp to date full, when it is passed a zero value, then returns a default date`() {
        // ARRANGE
        val expectedDateFull = "31 de dezembro de 1969"
        val expectedTimestamp = 0L

        // ACT
        val actualDateFull = expectedTimestamp.timestampToDateFull()

        // ASSERT
        Assert.assertEquals(expectedDateFull, actualDateFull)
    }

    @Test
    fun `Timestamp to date full, when it is passed a null value, then returns null`() {
        // ARRANGE
        val expectedTimestamp: Long? = null

        // ACT
        val actualDateFull = expectedTimestamp?.timestampToDateFull()

        // ASSERT
        Assert.assertNull(actualDateFull)
    }
}