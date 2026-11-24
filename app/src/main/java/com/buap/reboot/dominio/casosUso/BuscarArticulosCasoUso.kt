package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.Articulo
import com.buap.reboot.dominio.modelo.FiltrosBusqueda
import com.buap.reboot.dominio.repositorio.RepositorioArticulos
import com.buap.reboot.nucleo.Resultado

class BuscarArticulosCasoUso(
    private val repositorioArticulos: RepositorioArticulos
) {
    fun ejecutar(filtros: FiltrosBusqueda): Resultado<List<Articulo>> =
        Resultado.Exito(repositorioArticulos.buscar(filtros))
}
