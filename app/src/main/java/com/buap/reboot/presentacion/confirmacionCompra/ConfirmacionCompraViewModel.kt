package com.buap.reboot.presentacion.confirmacionCompra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.ObtenerConfirmacionCompraCasoUso
import com.buap.reboot.nucleo.Resultado

class ConfirmacionCompraViewModel(
    private val obtenerConfirmacionCompraCasoUso: ObtenerConfirmacionCompraCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoConfirmacionCompra())
    val estado: LiveData<EstadoConfirmacionCompra> = _estado

    fun cargar(idTransaccion: Long) {
        _estado.value = when (val resultado = obtenerConfirmacionCompraCasoUso.ejecutar(idTransaccion)) {
            is Resultado.Exito -> EstadoConfirmacionCompra(confirmacion = resultado.datos)
            is Resultado.Error -> EstadoConfirmacionCompra(error = resultado.mensaje)
        }
    }
}
