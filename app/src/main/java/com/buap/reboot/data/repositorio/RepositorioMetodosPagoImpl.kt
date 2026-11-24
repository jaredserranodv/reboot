package com.buap.reboot.data.repositorio

import com.buap.reboot.data.bd.entidades.MetodoPagoEntity
import com.buap.reboot.data.dao.MetodoPagoDao
import com.buap.reboot.dominio.modelo.MetodoPago
import com.buap.reboot.dominio.modelo.TipoTarjeta
import com.buap.reboot.dominio.repositorio.RepositorioMetodosPago

class RepositorioMetodosPagoImpl(private val metodoPagoDao: MetodoPagoDao) : RepositorioMetodosPago {

    override fun obtenerPorUsuario(idUsuario: Long): List<MetodoPago> =
        enIO { metodoPagoDao.obtenerPorUsuario(idUsuario) }.map { it.aDominio() }

    override fun obtenerPredeterminado(idUsuario: Long): MetodoPago? =
        enIO { metodoPagoDao.obtenerPredeterminado(idUsuario) }?.aDominio()

    override fun registrar(metodoPago: MetodoPago): Long = enIO {
        if (metodoPago.esPredeterminado) metodoPagoDao.limpiarPredeterminado(metodoPago.idUsuario)
        metodoPagoDao.insertar(metodoPago.aEntidad())
    }

    override fun eliminar(idMetodoPago: Long) {
        enIO { metodoPagoDao.eliminar(idMetodoPago) }
    }

    override fun marcarComoPredeterminado(idUsuario: Long, idMetodoPago: Long) {
        enIO {
            metodoPagoDao.limpiarPredeterminado(idUsuario)
            metodoPagoDao.marcarComoPredeterminado(idMetodoPago)
        }
    }

    private fun MetodoPagoEntity.aDominio() = MetodoPago(
        id = id,
        idUsuario = idUsuario,
        tipoTarjeta = TipoTarjeta.desdeEtiqueta(tipoTarjeta),
        ultimosCuatroDigitos = ultimosCuatroDigitos,
        mesVencimiento = mesVencimiento,
        anioVencimiento = anioVencimiento,
        esPredeterminado = esPredeterminado
    )

    private fun MetodoPago.aEntidad() = MetodoPagoEntity(
        id = id,
        idUsuario = idUsuario,
        tipoTarjeta = tipoTarjeta.etiqueta,
        ultimosCuatroDigitos = ultimosCuatroDigitos,
        mesVencimiento = mesVencimiento,
        anioVencimiento = anioVencimiento,
        esPredeterminado = esPredeterminado
    )
}
