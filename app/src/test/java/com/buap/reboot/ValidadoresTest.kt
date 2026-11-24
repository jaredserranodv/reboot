package com.buap.reboot.nucleo

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidadoresTest {
    @Test
    fun validaFormatoDeCorreo() {
        assertTrue(Validadores.esCorreoValido("alguien@correo.com"))
        assertFalse(Validadores.esCorreoValido("alguien-arroba-correo.com"))
    }

    @Test
    fun validaLongitudDeTarjeta() {
        assertTrue(Validadores.esNumeroTarjetaValido("1234567890123456"))
        assertFalse(Validadores.esNumeroTarjetaValido("123"))
    }

    @Test
    fun validaCoincidenciaDeContrasenas() {
        assertTrue(Validadores.contrasenasCoinciden("clave1234", "clave1234"))
        assertFalse(Validadores.contrasenasCoinciden("clave1234", "otraClave"))
    }
}
