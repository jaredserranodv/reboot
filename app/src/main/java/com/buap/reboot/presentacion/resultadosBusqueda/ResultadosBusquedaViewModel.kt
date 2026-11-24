package com.buap.reboot.presentacion.resultadosBusqueda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.BuscarArticulosCasoUso
import com.buap.reboot.dominio.modelo.FiltrosBusqueda
import com.buap.reboot.nucleo.Resultado

class ResultadosBusquedaViewModel(
    private val buscarArticulosCasoUso: BuscarArticulosCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoResultadosBusqueda())
    val estado: LiveData<EstadoResultadosBusqueda> = _estado

    fun buscar(filtros: FiltrosBusqueda) {
        _estado.value = (_estado.value ?: EstadoResultadosBusqueda()).copy(cargando = true, filtros = filtros)
        when (val resultado = buscarArticulosCasoUso.ejecutar(filtros)) {
            is Resultado.Exito -> _estado.value = EstadoResultadosBusqueda(articulos = resultado.datos, filtros = filtros)
            is Resultado.Error -> _estado.value = EstadoResultadosBusqueda(error = resultado.mensaje, filtros = filtros)
        }
    }
}
