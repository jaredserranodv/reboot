package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.Articulo
import com.buap.reboot.dominio.modelo.FiltrosBusqueda
import com.buap.reboot.dominio.repositorio.RepositorioArticulos
import com.buap.reboot.nucleo.Resultado

/** Listado completo de articulos disponibles para la pantalla de Inicio, mas reciente primero. */
class ObtenerArticulosPublicadosCasoUso(
    private val repositorioArticulos: RepositorioArticulos
) {
    fun ejecutar(): Resultado<List<Articulo>> =
        Resultado.Exito(repositorioArticulos.buscar(FiltrosBusqueda()))
}
