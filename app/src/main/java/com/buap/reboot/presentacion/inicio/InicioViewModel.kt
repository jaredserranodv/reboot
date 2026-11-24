package com.buap.reboot.presentacion.inicio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.ObtenerArticulosPublicadosCasoUso
import com.buap.reboot.nucleo.Resultado

class InicioViewModel(
    private val obtenerArticulosPublicadosCasoUso: ObtenerArticulosPublicadosCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoInicio())
    val estado: LiveData<EstadoInicio> = _estado

    fun cargar() {
        when (val resultado = obtenerArticulosPublicadosCasoUso.ejecutar()) {
            is Resultado.Exito -> _estado.value = EstadoInicio(cargando = false, articulos = resultado.datos)
            is Resultado.Error -> _estado.value = EstadoInicio(cargando = false, error = resultado.mensaje)
        }
    }
}
