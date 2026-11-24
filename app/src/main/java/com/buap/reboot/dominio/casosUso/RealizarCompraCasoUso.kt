package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.EstadoTransaccion
import com.buap.reboot.dominio.modelo.Notificacion
import com.buap.reboot.dominio.modelo.Transaccion
import com.buap.reboot.dominio.modelo.TipoNotificacion
import com.buap.reboot.dominio.repositorio.RepositorioArticulos
import com.buap.reboot.dominio.repositorio.RepositorioNotificaciones
import com.buap.reboot.dominio.repositorio.RepositorioTransacciones
import com.buap.reboot.nucleo.Resultado
import com.buap.reboot.nucleo.Validadores

/** Pago simulado: no hay procesamiento real, solo validacion de formato y persistencia local. */
class RealizarCompraCasoUso(
    private val repositorioArticulos: RepositorioArticulos,
    private val repositorioTransacciones: RepositorioTransacciones,
    private val repositorioNotificaciones: RepositorioNotificaciones
) {
    fun ejecutar(
        idArticulo: Long,
        idComprador: Long,
        numeroTarjeta: String,
        fechaVencimiento: String,
        cvv: String,
        direccionEnvio: String,
        fecha: String
    ): Resultado<Transaccion> {
        if (!Validadores.esNumeroTarjetaValido(numeroTarjeta)) {
            return Resultado.Error("Numero de tarjeta invalido")
        }
        if (!Validadores.esFechaVencimientoValida(fechaVencimiento)) {
            return Resultado.Error("Fecha de vencimiento invalida")
        }
        if (!Validadores.esCvvValido(cvv)) {
            return Resultado.Error("CVV invalido")
        }
        if (!Validadores.esCampoNoVacio(direccionEnvio)) {
            return Resultado.Error("La direccion de envio es obligatoria")
        }

        val articulo = repositorioArticulos.obtenerPorId(idArticulo)
            ?: return Resultado.Error("El articulo ya no esta disponible")
        if (!articulo.disponible) {
            return Resultado.Error("El articulo ya no esta disponible")
        }

        val transaccion = Transaccion(
            id = 0,
            idArticulo = idArticulo,
            idComprador = idComprador,
            totalPagado = articulo.precio,
            estado = EstadoTransaccion.CONFIRMADA,
            fecha = fecha,
            direccionEnvio = direccionEnvio
        )
        val idTransaccion = repositorioTransacciones.registrar(transaccion)
        repositorioArticulos.actualizar(articulo.copy(disponible = false))

        repositorioNotificaciones.registrar(
            Notificacion(
                id = 0,
                idUsuario = idComprador,
                tipo = TipoNotificacion.PAGO_CONFIRMADO,
                titulo = "Pago confirmado",
                mensaje = "Tu compra de \"${articulo.titulo}\" fue confirmada",
                fecha = fecha,
                leida = false
            )
        )

        return Resultado.Exito(transaccion.copy(id = idTransaccion))
    }
}
