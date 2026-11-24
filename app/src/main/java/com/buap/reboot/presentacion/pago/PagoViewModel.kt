package com.buap.reboot.presentacion.pago

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.ObtenerDetalleArticuloCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerDireccionPredeterminadaCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerSesionActualCasoUso
import com.buap.reboot.dominio.casosUso.RealizarCompraCasoUso
import com.buap.reboot.dominio.modelo.direccionCompleta
import com.buap.reboot.nucleo.Resultado
import java.time.LocalDate

class PagoViewModel(
    private val obtenerDetalleArticuloCasoUso: ObtenerDetalleArticuloCasoUso,
    private val obtenerSesionActualCasoUso: ObtenerSesionActualCasoUso,
    private val obtenerDireccionPredeterminadaCasoUso: ObtenerDireccionPredeterminadaCasoUso,
    private val realizarCompraCasoUso: RealizarCompraCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoPago())
    val estado: LiveData<EstadoPago> = _estado

    fun cargarArticulo(idArticulo: Long) {
        val idUsuario = obtenerSesionActualCasoUso.ejecutar()?.id
        val direccionPredeterminada = idUsuario?.let { obtenerDireccionPredeterminadaCasoUso.ejecutar(it)?.direccionCompleta() }

        when (val resultado = obtenerDetalleArticuloCasoUso.ejecutar(idArticulo)) {
            is Resultado.Exito -> _estado.value =
                EstadoPago(articulo = resultado.datos.articulo, direccionPredeterminada = direccionPredeterminada)
            is Resultado.Error -> _estado.value = EstadoPago(error = resultado.mensaje)
        }
    }

    fun confirmarPago(
        idArticulo: Long,
        numeroTarjeta: String,
        fechaVencimiento: String,
        cvv: String,
        direccionEnvio: String
    ) {
        val idComprador = obtenerSesionActualCasoUso.ejecutar()?.id
        if (idComprador == null) {
            _estado.value = (_estado.value ?: EstadoPago()).copy(error = "No hay sesion activa")
            return
        }

        _estado.value = (_estado.value ?: EstadoPago()).copy(cargando = true, error = null)
        val resultado = realizarCompraCasoUso.ejecutar(
            idArticulo = idArticulo,
            idComprador = idComprador,
            numeroTarjeta = numeroTarjeta,
            fechaVencimiento = fechaVencimiento,
            cvv = cvv,
            direccionEnvio = direccionEnvio,
            fecha = LocalDate.now().toString()
        )
        _estado.value = when (resultado) {
            is Resultado.Exito -> (_estado.value ?: EstadoPago())
                .copy(cargando = false, pagoConfirmado = true, idTransaccionConfirmada = resultado.datos.id)
            is Resultado.Error -> (_estado.value ?: EstadoPago()).copy(cargando = false, error = resultado.mensaje)
        }
    }
}
