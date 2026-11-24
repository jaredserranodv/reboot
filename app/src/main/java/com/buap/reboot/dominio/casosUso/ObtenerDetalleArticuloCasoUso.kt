package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.DetalleArticulo
import com.buap.reboot.dominio.repositorio.RepositorioArticulos
import com.buap.reboot.dominio.repositorio.RepositorioResenas
import com.buap.reboot.dominio.repositorio.RepositorioUsuarios
import com.buap.reboot.nucleo.Resultado

class ObtenerDetalleArticuloCasoUso(
    private val repositorioArticulos: RepositorioArticulos,
    private val repositorioUsuarios: RepositorioUsuarios,
    private val repositorioResenas: RepositorioResenas
) {
    fun ejecutar(idArticulo: Long): Resultado<DetalleArticulo> {
        val articulo = repositorioArticulos.obtenerPorId(idArticulo)
            ?: return Resultado.Error("El articulo ya no esta disponible")
        val vendedor = repositorioUsuarios.buscarPorId(articulo.idVendedor)
            ?: return Resultado.Error("No se encontro al vendedor")

        val calificaciones = repositorioResenas.obtenerPorVendedor(vendedor.id).map { it.calificacion }

        return Resultado.Exito(
            DetalleArticulo(
                articulo = articulo,
                nombreVendedor = vendedor.nombreCompleto,
                ratingVendedor = if (calificaciones.isEmpty()) 0f else calificaciones.average().toFloat()
            )
        )
    }
}
