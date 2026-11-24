package com.buap.reboot.presentacion.direcciones

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.GuardarDireccionCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerDireccionPorIdCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerSesionActualCasoUso
import com.buap.reboot.dominio.modelo.DireccionEnvio
import com.buap.reboot.nucleo.Resultado

class AgregarDireccionViewModel(
    private val obtenerDireccionPorIdCasoUso: ObtenerDireccionPorIdCasoUso,
    private val guardarDireccionCasoUso: GuardarDireccionCasoUso,
    private val obtenerSesionActualCasoUso: ObtenerSesionActualCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoAgregarDireccion())
    val estado: LiveData<EstadoAgregarDireccion> = _estado

    fun cargarParaEditar(idDireccion: Long) {
        _estado.value = EstadoAgregarDireccion(direccionAEditar = obtenerDireccionPorIdCasoUso.ejecutar(idDireccion))
    }

    fun guardar(
        idDireccionEnEdicion: Long,
        etiqueta: String,
        calleYNumero: String,
        colonia: String,
        ciudad: String,
        codigoPostal: String,
        telefonoContacto: String,
        esPredeterminada: Boolean
    ) {
        val idUsuario = obtenerSesionActualCasoUso.ejecutar()?.id
        if (idUsuario == null) {
            _estado.value = (_estado.value ?: EstadoAgregarDireccion()).copy(error = "No hay sesion activa")
            return
        }

        val direccion = DireccionEnvio(
            id = idDireccionEnEdicion,
            idUsuario = idUsuario,
            etiqueta = etiqueta,
            calleYNumero = calleYNumero,
            colonia = colonia,
            ciudad = ciudad,
            codigoPostal = codigoPostal,
            telefonoContacto = telefonoContacto,
            esPredeterminada = esPredeterminada
        )

        _estado.value = (_estado.value ?: EstadoAgregarDireccion()).copy(cargando = true, error = null)
        _estado.value = when (val resultado = guardarDireccionCasoUso.ejecutar(direccion)) {
            is Resultado.Exito -> (_estado.value ?: EstadoAgregarDireccion()).copy(cargando = false, guardadaExitosamente = true)
            is Resultado.Error -> (_estado.value ?: EstadoAgregarDireccion()).copy(cargando = false, error = resultado.mensaje)
        }
    }
}
