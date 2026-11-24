package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.Transaccion
import com.buap.reboot.dominio.repositorio.RepositorioTransacciones

class ObtenerTransaccionPorArticuloCasoUso(
    private val repositorioTransacciones: RepositorioTransacciones
) {
    fun ejecutar(idArticulo: Long): Transaccion? = repositorioTransacciones.obtenerPorArticulo(idArticulo)
}
