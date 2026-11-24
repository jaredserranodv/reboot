package com.buap.reboot.presentacion.dejarResena

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.EnviarResenaCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerDetalleArticuloCasoUso
import com.buap.reboot.nucleo.Resultado
import java.time.LocalDate

class DejarResenaViewModel(
    private val obtenerDetalleArticuloCasoUso: ObtenerDetalleArticuloCasoUso,
    private val enviarResenaCasoUso: EnviarResenaCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoDejarResena())
    val estado: LiveData<EstadoDejarResena> = _estado

    fun cargarArticulo(idArticulo: Long) {
        when (val resultado = obtenerDetalleArticuloCasoUso.ejecutar(idArticulo)) {
            is Resultado.Exito -> _estado.value = EstadoDejarResena(articulo = resultado.datos.articulo)
            is Resultado.Error -> _estado.value = EstadoDejarResena(error = resultado.mensaje)
        }
    }

    fun enviar(
        idTransaccion: Long,
        idVendedor: Long,
        idComprador: Long,
        calificacion: Int,
        comentario: String,
        recomendado: Boolean
    ) {
        _estado.value = (_estado.value ?: EstadoDejarResena()).copy(cargando = true, error = null)
        val resultado = enviarResenaCasoUso.ejecutar(
            idTransaccion = idTransaccion,
            idVendedor = idVendedor,
            idComprador = idComprador,
            calificacion = calificacion,
            comentario = comentario,
            recomendado = recomendado,
            fecha = LocalDate.now().toString()
        )
        _estado.value = when (resultado) {
            is Resultado.Exito -> (_estado.value ?: EstadoDejarResena()).copy(cargando = false, enviadaExitosamente = true)
            is Resultado.Error -> (_estado.value ?: EstadoDejarResena()).copy(cargando = false, error = resultado.mensaje)
        }
    }
}
