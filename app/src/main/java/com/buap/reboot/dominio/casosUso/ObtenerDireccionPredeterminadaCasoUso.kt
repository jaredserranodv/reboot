package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.DireccionEnvio
import com.buap.reboot.dominio.repositorio.RepositorioDirecciones

class ObtenerDireccionPredeterminadaCasoUso(
    private val repositorioDirecciones: RepositorioDirecciones
) {
    fun ejecutar(idUsuario: Long): DireccionEnvio? = repositorioDirecciones.obtenerPredeterminada(idUsuario)
}
