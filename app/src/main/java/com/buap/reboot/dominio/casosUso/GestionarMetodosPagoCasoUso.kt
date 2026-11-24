package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.MetodoPago
import com.buap.reboot.dominio.modelo.TipoTarjeta
import com.buap.reboot.dominio.repositorio.RepositorioMetodosPago
import com.buap.reboot.nucleo.Resultado
import com.buap.reboot.nucleo.Validadores

private const val LONGITUD_ULTIMOS_DIGITOS = 4

class ObtenerMetodosPagoCasoUso(
    private val repositorioMetodosPago: RepositorioMetodosPago
) {
    fun ejecutar(idUsuario: Long): Resultado<List<MetodoPago>> =
        Resultado.Exito(repositorioMetodosPago.obtenerPorUsuario(idUsuario))
}

// Nunca recibe el numero completo de tarjeta ni el CVV: solo los ultimos 4 digitos para mostrar.
class AgregarMetodoPagoCasoUso(
    private val repositorioMetodosPago: RepositorioMetodosPago
) {
    fun ejecutar(
        idUsuario: Long,
        numeroTarjeta: String,
        mesVencimiento: Int,
        anioVencimiento: Int,
        esPredeterminado: Boolean
    ): Resultado<Long> {
        if (!Validadores.esNumeroTarjetaValido(numeroTarjeta)) {
            return Resultado.Error("Numero de tarjeta invalido")
        }

        val metodoPago = MetodoPago(
            id = 0,
            idUsuario = idUsuario,
            tipoTarjeta = TipoTarjeta.desdeNumero(numeroTarjeta),
            ultimosCuatroDigitos = numeroTarjeta.takeLast(LONGITUD_ULTIMOS_DIGITOS),
            mesVencimiento = mesVencimiento,
            anioVencimiento = anioVencimiento,
            esPredeterminado = esPredeterminado
        )
        return Resultado.Exito(repositorioMetodosPago.registrar(metodoPago))
    }
}

class EliminarMetodoPagoCasoUso(
    private val repositorioMetodosPago: RepositorioMetodosPago
) {
    fun ejecutar(idMetodoPago: Long) {
        repositorioMetodosPago.eliminar(idMetodoPago)
    }
}

class MarcarMetodoPagoPredeterminadoCasoUso(
    private val repositorioMetodosPago: RepositorioMetodosPago
) {
    fun ejecutar(idUsuario: Long, idMetodoPago: Long) {
        repositorioMetodosPago.marcarComoPredeterminado(idUsuario, idMetodoPago)
    }
}
