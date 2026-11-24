package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.repositorio.RepositorioArticulos
import com.buap.reboot.nucleo.Resultado

class CambiarDisponibilidadArticuloCasoUso(
    private val repositorioArticulos: RepositorioArticulos
) {
    fun ejecutar(idArticulo: Long, disponible: Boolean): Resultado<Unit> {
        val articulo = repositorioArticulos.obtenerPorId(idArticulo)
            ?: return Resultado.Error("El articulo ya no existe")
        repositorioArticulos.actualizar(articulo.copy(disponible = disponible))
        return Resultado.Exito(Unit)
    }
}
