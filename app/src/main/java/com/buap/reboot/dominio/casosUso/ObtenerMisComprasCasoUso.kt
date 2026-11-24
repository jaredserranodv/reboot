package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.CompraConDetalle
import com.buap.reboot.dominio.repositorio.RepositorioArticulos
import com.buap.reboot.dominio.repositorio.RepositorioResenas
import com.buap.reboot.dominio.repositorio.RepositorioTransacciones
import com.buap.reboot.nucleo.Resultado

class ObtenerMisComprasCasoUso(
    private val repositorioTransacciones: RepositorioTransacciones,
    private val repositorioArticulos: RepositorioArticulos,
    private val repositorioResenas: RepositorioResenas
) {
    fun ejecutar(idComprador: Long): Resultado<List<CompraConDetalle>> {
        val compras = repositorioTransacciones.obtenerPorComprador(idComprador).mapNotNull { transaccion ->
            val articulo = repositorioArticulos.obtenerPorId(transaccion.idArticulo) ?: return@mapNotNull null
            CompraConDetalle(
                transaccion = transaccion,
                articulo = articulo,
                tieneResena = repositorioResenas.obtenerPorTransaccion(transaccion.id) != null
            )
        }
        return Resultado.Exito(compras)
    }
}
