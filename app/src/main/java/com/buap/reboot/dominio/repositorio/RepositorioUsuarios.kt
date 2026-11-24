package com.buap.reboot.dominio.repositorio

import com.buap.reboot.dominio.modelo.Usuario

interface RepositorioUsuarios {
    fun buscarPorCorreo(correo: String): Usuario?
    fun buscarPorId(id: Long): Usuario?
    fun registrar(usuario: Usuario): Long
    fun existeCorreo(correo: String): Boolean
    fun actualizarContrasena(idUsuario: Long, nuevaContrasenaHash: String)
    fun actualizarPerfil(usuario: Usuario)
}
