package com.buap.reboot.data.repositorio

import com.buap.reboot.data.bd.entidades.DireccionEnvioEntity
import com.buap.reboot.data.dao.DireccionEnvioDao
import com.buap.reboot.dominio.modelo.DireccionEnvio
import com.buap.reboot.dominio.repositorio.RepositorioDirecciones

class RepositorioDireccionesImpl(private val direccionEnvioDao: DireccionEnvioDao) : RepositorioDirecciones {

    override fun obtenerPorUsuario(idUsuario: Long): List<DireccionEnvio> =
        enIO { direccionEnvioDao.obtenerPorUsuario(idUsuario) }.map { it.aDominio() }

    override fun obtenerPredeterminada(idUsuario: Long): DireccionEnvio? =
        enIO { direccionEnvioDao.obtenerPredeterminada(idUsuario) }?.aDominio()

    override fun obtenerPorId(idDireccion: Long): DireccionEnvio? =
        enIO { direccionEnvioDao.obtenerPorId(idDireccion) }?.aDominio()

    override fun registrar(direccion: DireccionEnvio): Long = enIO {
        if (direccion.esPredeterminada) direccionEnvioDao.limpiarPredeterminada(direccion.idUsuario)
        direccionEnvioDao.insertar(direccion.aEntidad())
    }

    override fun actualizar(direccion: DireccionEnvio) {
        enIO {
            if (direccion.esPredeterminada) direccionEnvioDao.limpiarPredeterminada(direccion.idUsuario)
            direccionEnvioDao.actualizar(direccion.aEntidad())
        }
    }

    override fun eliminar(idDireccion: Long) {
        enIO { direccionEnvioDao.eliminar(idDireccion) }
    }

    override fun marcarComoPredeterminada(idUsuario: Long, idDireccion: Long) {
        enIO {
            direccionEnvioDao.limpiarPredeterminada(idUsuario)
            direccionEnvioDao.marcarComoPredeterminada(idDireccion)
        }
    }

    private fun DireccionEnvioEntity.aDominio() = DireccionEnvio(
        id = id,
        idUsuario = idUsuario,
        etiqueta = etiqueta,
        calleYNumero = calleYNumero,
        colonia = colonia,
        ciudad = ciudad,
        codigoPostal = codigoPostal,
        telefonoContacto = telefonoContacto,
        esPredeterminada = esPredeterminada
    )

    private fun DireccionEnvio.aEntidad() = DireccionEnvioEntity(
        id = id,
        idUsuario = idUsuario,
        etiqueta = etiqueta,
        calleYNumero = calleYNumero,
        colonia = colonia,
        ciudad = ciudad,
        codigoPostal = codigoPostal,
        telefonoContacto = telefonoContacto,
        esPredeterminada = esPredeterminada
    )
}
