package com.buap.reboot.data.repositorio

import android.content.Context
import com.buap.reboot.dominio.repositorio.RepositorioSesion

class RepositorioSesionImpl(contexto: Context) : RepositorioSesion {

    private val preferencias = contexto.getSharedPreferences(PREFS_SESION, Context.MODE_PRIVATE)

    override fun iniciarSesion(idUsuario: Long) {
        preferencias.edit().putLong(CLAVE_ID_USUARIO, idUsuario).apply()
    }

    override fun cerrarSesion() {
        preferencias.edit().remove(CLAVE_ID_USUARIO).apply()
    }

    override fun obtenerIdUsuarioActual(): Long? {
        val id = preferencias.getLong(CLAVE_ID_USUARIO, SIN_SESION)
        return if (id == SIN_SESION) null else id
    }

    companion object {
        private const val PREFS_SESION = "reboot_sesion"
        private const val CLAVE_ID_USUARIO = "id_usuario_actual"
        private const val SIN_SESION = -1L
    }
}
