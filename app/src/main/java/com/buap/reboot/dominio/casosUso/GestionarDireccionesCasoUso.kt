package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.DireccionEnvio
import com.buap.reboot.dominio.repositorio.RepositorioDirecciones
import com.buap.reboot.nucleo.Resultado
import com.buap.reboot.nucleo.Validadores

class ObtenerDireccionesCasoUso(
    private val repositorioDirecciones: RepositorioDirecciones
) {
    fun ejecutar(idUsuario: Long): Resultado<List<DireccionEnvio>> =
        Resultado.Exito(repositorioDirecciones.obtenerPorUsuario(idUsuario))
}

class ObtenerDireccionPorIdCasoUso(
    private val repositorioDirecciones: RepositorioDirecciones
) {
    fun ejecutar(idDireccion: Long): DireccionEnvio? = repositorioDirecciones.obtenerPorId(idDireccion)
}

/** Sirve tanto para alta (direccion.id == 0) como para edicion. */
class GuardarDireccionCasoUso(
    private val repositorioDirecciones: RepositorioDirecciones
) {
    fun ejecutar(direccion: DireccionEnvio): Resultado<Long> {
        if (!Validadores.esCampoNoVacio(direccion.etiqueta) ||
            !Validadores.esCampoNoVacio(direccion.calleYNumero) ||
            !Validadores.esCampoNoVacio(direccion.colonia) ||
            !Validadores.esCampoNoVacio(direccion.ciudad) ||
            !Validadores.esCampoNoVacio(direccion.codigoPostal)
        ) {
            return Resultado.Error("La etiqueta y la direccion completa son obligatorias")
        }
        if (!Validadores.esTelefonoValido(direccion.telefonoContacto)) {
            return Resultado.Error("El telefono debe tener 10 digitos")
        }

        return if (direccion.id == 0L) {
            Resultado.Exito(repositorioDirecciones.registrar(direccion))
        } else {
            repositorioDirecciones.actualizar(direccion)
            Resultado.Exito(direccion.id)
        }
    }
}

class EliminarDireccionCasoUso(
    private val repositorioDirecciones: RepositorioDirecciones
) {
    fun ejecutar(idDireccion: Long) {
        repositorioDirecciones.eliminar(idDireccion)
    }
}

class MarcarDireccionPredeterminadaCasoUso(
    private val repositorioDirecciones: RepositorioDirecciones
) {
    fun ejecutar(idUsuario: Long, idDireccion: Long) {
        repositorioDirecciones.marcarComoPredeterminada(idUsuario, idDireccion)
    }
}
