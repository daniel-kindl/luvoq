package dev.danielkindl.luvoq

import org.junit.Assert.assertEquals
import org.junit.Test

class AppSmokeTest {
    @Test
    fun phaseZeroVersionIsStableAndExplicit() {
        assertEquals("0.1.0", BuildConfig.VERSION_NAME)
    }
}
