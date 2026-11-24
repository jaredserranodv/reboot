package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.repositorio.RepositorioArticulos

class EliminarArticuloCasoUso(
    private val repositorioArticulos: RepositorioArticulos
) {
    fun ejecutar(idArticulo: Long) {
        repositorioArticulos.eliminar(idArticulo)
    }
}
