package com.buap.reboot.presentacion.detalleArticulo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.ObtenerDetalleArticuloCasoUso
import com.buap.reboot.nucleo.Resultado

class DetalleArticuloViewModel(
    private val obtenerDetalleArticuloCasoUso: ObtenerDetalleArticuloCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoDetalleArticulo())
    val estado: LiveData<EstadoDetalleArticulo> = _estado

    fun cargar(idArticulo: Long) {
        when (val resultado = obtenerDetalleArticuloCasoUso.ejecutar(idArticulo)) {
            is Resultado.Exito -> _estado.value = EstadoDetalleArticulo(cargando = false, detalle = resultado.datos)
            is Resultado.Error -> _estado.value = EstadoDetalleArticulo(cargando = false, error = resultado.mensaje)
        }
    }
}
