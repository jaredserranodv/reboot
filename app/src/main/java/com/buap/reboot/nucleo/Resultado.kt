package com.buap.reboot.nucleo

/** Envoltura estandar para el resultado de un caso de uso, evita lanzar excepciones hacia la UI. */
sealed class Resultado<out T> {
    data class Exito<out T>(val datos: T) : Resultado<T>()
    data class Error(val mensaje: String) : Resultado<Nothing>()
}

inline fun <T> Resultado<T>.alExito(accion: (T) -> Unit): Resultado<T> {
    if (this is Resultado.Exito) accion(datos)
    return this
}

inline fun <T> Resultado<T>.alError(accion: (String) -> Unit): Resultado<T> {
    if (this is Resultado.Error) accion(mensaje)
    return this
}
