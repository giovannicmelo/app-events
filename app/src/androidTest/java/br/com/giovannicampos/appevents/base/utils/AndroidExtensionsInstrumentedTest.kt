package br.com.giovannicampos.appevents.base.utils

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test

class AndroidExtensionsInstrumentedTest {

    @Test
    fun dpToPx_returnConversionDpToPixel() {
        // ASSERT
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedPx = 21.0f

        // ACT
        val actualPx = appContext.dpToPx(8)

        // ASSERT
        Assert.assertEquals(expectedPx, actualPx)
    }

    @Test
    fun isConnected_checksIfHasConnection() {
        // ASSERT
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        // ACT
        val isConnected = appContext.isConnected()

        // ASSERT
        Assert.assertTrue(isConnected)
    }

    @Test
    fun isConnected_checksIfHasNoConnection() {
        // ASSERT
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        // ACT
        val isConnected = !appContext.isConnected()

        // ASSERT
        Assert.assertFalse(isConnected)
    }
}