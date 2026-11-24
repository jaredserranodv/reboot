package com.buap.reboot

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class PruebaInstrumentadaEjemplo {
    @Test
    fun elPaqueteDeLaAppEsCorrecto() {
        val contexto = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.buap.reboot", contexto.packageName)
    }
}
