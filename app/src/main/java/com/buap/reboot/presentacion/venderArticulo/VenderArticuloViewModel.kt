package com.buap.reboot.presentacion.venderArticulo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.ObtenerSesionActualCasoUso
import com.buap.reboot.dominio.casosUso.PublicarArticuloCasoUso
import com.buap.reboot.dominio.modelo.EstadoArticulo
import com.buap.reboot.nucleo.Resultado
import java.time.LocalDate

class VenderArticuloViewModel(
    private val publicarArticuloCasoUso: PublicarArticuloCasoUso,
    private val obtenerSesionActualCasoUso: ObtenerSesionActualCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoVenderArticulo())
    val estado: LiveData<EstadoVenderArticulo> = _estado

    fun publicar(
        titulo: String,
        descripcion: String,
        categoria: String,
        precioTexto: String,
        estadoArticulo: EstadoArticulo,
        ciudad: String,
        rutaImagen: String?
    ) {
        val idVendedor = obtenerSesionActualCasoUso.ejecutar()?.id
        if (idVendedor == null) {
            _estado.value = EstadoVenderArticulo(error = "No hay sesion activa")
            return
        }

        val precio = precioTexto.toDoubleOrNull()
        if (precio == null) {
            _estado.value = EstadoVenderArticulo(error = "El precio no es valido")
            return
        }

        _estado.value = EstadoVenderArticulo(cargando = true)
        val resultado = publicarArticuloCasoUso.ejecutar(
            idVendedor = idVendedor,
            titulo = titulo,
            descripcion = descripcion,
            categoria = categoria,
            precio = precio,
            estadoArticulo = estadoArticulo,
            ciudad = ciudad,
            fechaPublicacion = LocalDate.now().toString(),
            rutaImagen = rutaImagen
        )
        _estado.value = when (resultado) {
            is Resultado.Exito -> EstadoVenderArticulo(publicadoExitosamente = true)
            is Resultado.Error -> EstadoVenderArticulo(error = resultado.mensaje)
        }
    }
}
