package com.buap.reboot.data.repositorio

import com.buap.reboot.data.bd.entidades.UsuarioEntity
import com.buap.reboot.data.dao.UsuarioDao
import com.buap.reboot.dominio.modelo.Usuario
import com.buap.reboot.dominio.repositorio.RepositorioUsuarios

class RepositorioUsuariosImpl(private val usuarioDao: UsuarioDao) : RepositorioUsuarios {

    override fun buscarPorCorreo(correo: String): Usuario? =
        enIO { usuarioDao.buscarPorCorreo(correo) }?.aDominio()

    override fun buscarPorId(id: Long): Usuario? =
        enIO { usuarioDao.buscarPorId(id) }?.aDominio()

    override fun registrar(usuario: Usuario): Long =
        enIO { usuarioDao.insertar(usuario.aEntidad()) }

    override fun existeCorreo(correo: String): Boolean =
        buscarPorCorreo(correo) != null

    override fun actualizarContrasena(idUsuario: Long, nuevaContrasenaHash: String) {
        enIO { usuarioDao.actualizarContrasena(idUsuario, nuevaContrasenaHash) }
    }

    override fun actualizarPerfil(usuario: Usuario) {
        enIO { usuarioDao.actualizar(usuario.aEntidad()) }
    }

    private fun UsuarioEntity.aDominio() = Usuario(
        id = id,
        nombreCompleto = nombreCompleto,
        correo = correo,
        telefono = telefono,
        contrasenaHash = contrasenaHash,
        preguntaSeguridad = preguntaSeguridad,
        respuestaSeguridad = respuestaSeguridad,
        fechaRegistro = fechaRegistro,
        rutaFotoPerfil = rutaFotoPerfil
    )

    private fun Usuario.aEntidad() = UsuarioEntity(
        id = id,
        nombreCompleto = nombreCompleto,
        correo = correo,
        telefono = telefono,
        contrasenaHash = contrasenaHash,
        preguntaSeguridad = preguntaSeguridad,
        respuestaSeguridad = respuestaSeguridad,
        fechaRegistro = fechaRegistro,
        rutaFotoPerfil = rutaFotoPerfil
    )
}
