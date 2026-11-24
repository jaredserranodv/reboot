package com.buap.reboot.presentacion.metodosPago

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.AgregarMetodoPagoCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerSesionActualCasoUso
import com.buap.reboot.nucleo.Resultado
import com.buap.reboot.nucleo.Validadores

private const val PREFIJO_SIGLO = 2000

class AgregarTarjetaViewModel(
    private val agregarMetodoPagoCasoUso: AgregarMetodoPagoCasoUso,
    private val obtenerSesionActualCasoUso: ObtenerSesionActualCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoAgregarTarjeta())
    val estado: LiveData<EstadoAgregarTarjeta> = _estado

    // El CVV solo se valida aqui, nunca se envia al caso de uso ni se persiste.
    fun guardar(numeroTarjeta: String, vencimiento: String, cvv: String, esPredeterminada: Boolean) {
        val idUsuario = obtenerSesionActualCasoUso.ejecutar()?.id
        if (idUsuario == null) {
            _estado.value = EstadoAgregarTarjeta(error = "No hay sesion activa")
            return
        }
        if (!Validadores.esFechaVencimientoValida(vencimiento)) {
            _estado.value = EstadoAgregarTarjeta(error = "Fecha de vencimiento invalida")
            return
        }
        if (!Validadores.esCvvValido(cvv)) {
            _estado.value = EstadoAgregarTarjeta(error = "CVV invalido")
            return
        }

        val (mes, anioCorto) = vencimiento.split("/")
        _estado.value = EstadoAgregarTarjeta(cargando = true)
        val resultado = agregarMetodoPagoCasoUso.ejecutar(
            idUsuario = idUsuario,
            numeroTarjeta = numeroTarjeta,
            mesVencimiento = mes.toInt(),
            anioVencimiento = PREFIJO_SIGLO + anioCorto.toInt(),
            esPredeterminado = esPredeterminada
        )
        _estado.value = when (resultado) {
            is Resultado.Exito -> EstadoAgregarTarjeta(guardadaExitosamente = true)
            is Resultado.Error -> EstadoAgregarTarjeta(error = resultado.mensaje)
        }
    }
}
