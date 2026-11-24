package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.ConfirmacionCompra
import com.buap.reboot.dominio.repositorio.RepositorioArticulos
import com.buap.reboot.dominio.repositorio.RepositorioTransacciones
import com.buap.reboot.nucleo.Resultado

class ObtenerConfirmacionCompraCasoUso(
    private val repositorioTransacciones: RepositorioTransacciones,
    private val repositorioArticulos: RepositorioArticulos
) {
    fun ejecutar(idTransaccion: Long): Resultado<ConfirmacionCompra> {
        val transaccion = repositorioTransacciones.obtenerPorId(idTransaccion)
            ?: return Resultado.Error("No se encontro la compra")
        val articulo = repositorioArticulos.obtenerPorId(transaccion.idArticulo)
            ?: return Resultado.Error("El articulo ya no esta disponible")

        return Resultado.Exito(
            ConfirmacionCompra(
                articulo = articulo,
                totalPagado = transaccion.totalPagado,
                direccionEnvio = transaccion.direccionEnvio
            )
        )
    }
}
