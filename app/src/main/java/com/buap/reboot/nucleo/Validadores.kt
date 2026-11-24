package com.buap.reboot.nucleo

/** Validaciones de formato reutilizadas por los casos de uso de Login, Registro y Pago. */
object Validadores {

    private val PATRON_CORREO = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val PATRON_SOLO_DIGITOS = Regex("^[0-9]+$")

    private const val LONGITUD_MINIMA_CONTRASENA = 8 // TODO: definir regla exacta de contrasena (mayus/numero/simbolo)
    private const val LONGITUD_TELEFONO = 10
    private const val LONGITUD_TARJETA = 16
    private const val LONGITUD_CVV = 3

    fun esCorreoValido(correo: String): Boolean = PATRON_CORREO.matches(correo.trim())

    fun esContrasenaValida(contrasena: String): Boolean = contrasena.length >= LONGITUD_MINIMA_CONTRASENA

    fun contrasenasCoinciden(contrasena: String, confirmacion: String): Boolean = contrasena == confirmacion

    fun esTelefonoValido(telefono: String): Boolean =
        telefono.length == LONGITUD_TELEFONO && PATRON_SOLO_DIGITOS.matches(telefono)

    fun esCampoNoVacio(valor: String): Boolean = valor.trim().isNotEmpty()

    fun esNumeroTarjetaValido(numero: String): Boolean =
        numero.length == LONGITUD_TARJETA && PATRON_SOLO_DIGITOS.matches(numero)

    fun esCvvValido(cvv: String): Boolean = cvv.length == LONGITUD_CVV && PATRON_SOLO_DIGITOS.matches(cvv)

    fun esFechaVencimientoValida(fecha: String): Boolean =
        Regex("^(0[1-9]|1[0-2])/[0-9]{2}$").matches(fecha) // TODO: definir si se valida que no este vencida
}
