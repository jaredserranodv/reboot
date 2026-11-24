package com.buap.reboot.dominio.repositorio

/** Guarda que usuario esta logueado localmente; no hay backend ni tokens reales. */
interface RepositorioSesion {
    fun iniciarSesion(idUsuario: Long)
    fun cerrarSesion()
    fun obtenerIdUsuarioActual(): Long?
}
