package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.Articulo
import com.buap.reboot.dominio.repositorio.RepositorioArticulos
import com.buap.reboot.nucleo.Resultado

class ObtenerMisPublicacionesCasoUso(
    private val repositorioArticulos: RepositorioArticulos
) {
    fun ejecutar(idVendedor: Long): Resultado<List<Articulo>> =
        Resultado.Exito(repositorioArticulos.obtenerPorVendedor(idVendedor))
}
