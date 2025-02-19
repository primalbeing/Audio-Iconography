package com.audioiconography.app

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test
        val appContext: Context = ApplicationProvider.getApplicationContext()
        assertEquals("com.audioiconography.app", appContext.packageName)
    }
}
