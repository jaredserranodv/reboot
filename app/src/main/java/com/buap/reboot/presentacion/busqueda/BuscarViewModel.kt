package com.buap.reboot.presentacion.busqueda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val MAXIMO_BUSQUEDAS_RECIENTES = 5

class BuscarViewModel : ViewModel() {

    private val _estado = MutableLiveData(EstadoBuscar())
    val estado: LiveData<EstadoBuscar> = _estado

    fun registrarBusqueda(texto: String) {
        if (texto.isBlank()) return
        val actuales = _estado.value?.busquedasRecientes.orEmpty()
        val actualizadas = (listOf(texto) + actuales.filterNot { it == texto })
            .take(MAXIMO_BUSQUEDAS_RECIENTES)
        _estado.value = EstadoBuscar(busquedasRecientes = actualizadas)
    }
}
