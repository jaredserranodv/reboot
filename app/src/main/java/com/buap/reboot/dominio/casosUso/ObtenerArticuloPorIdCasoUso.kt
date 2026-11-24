package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.Articulo
import com.buap.reboot.dominio.repositorio.RepositorioArticulos
import com.buap.reboot.nucleo.Resultado

class ObtenerArticuloPorIdCasoUso(
    private val repositorioArticulos: RepositorioArticulos
) {
    fun ejecutar(idArticulo: Long): Resultado<Articulo> {
        val articulo = repositorioArticulos.obtenerPorId(idArticulo)
            ?: return Resultado.Error("El articulo ya no existe")
        return Resultado.Exito(articulo)
    }
}
