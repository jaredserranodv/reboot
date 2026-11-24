package com.buap.reboot.presentacion.editarPerfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.ActualizarPerfilCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerSesionActualCasoUso
import com.buap.reboot.nucleo.Resultado

class EditarPerfilViewModel(
    private val obtenerSesionActualCasoUso: ObtenerSesionActualCasoUso,
    private val actualizarPerfilCasoUso: ActualizarPerfilCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoEditarPerfil())
    val estado: LiveData<EstadoEditarPerfil> = _estado

    fun cargar() {
        val usuario = obtenerSesionActualCasoUso.ejecutar()
        if (usuario == null) {
            _estado.value = EstadoEditarPerfil(error = "No hay sesion activa")
            return
        }
        _estado.value = EstadoEditarPerfil(usuario = usuario)
    }

    fun guardar(nombreCompleto: String, telefono: String) {
        val idUsuario = _estado.value?.usuario?.id ?: return
        _estado.value = (_estado.value ?: EstadoEditarPerfil()).copy(cargando = true, error = null)
        _estado.value = when (val resultado = actualizarPerfilCasoUso.ejecutar(idUsuario, nombreCompleto, telefono)) {
            is Resultado.Exito -> (_estado.value ?: EstadoEditarPerfil()).copy(cargando = false, guardadoExitosamente = true)
            is Resultado.Error -> (_estado.value ?: EstadoEditarPerfil()).copy(cargando = false, error = resultado.mensaje)
        }
    }
}
