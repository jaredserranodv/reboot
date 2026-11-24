package com.buap.reboot.presentacion.editarArticulo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.EditarArticuloCasoUso
import com.buap.reboot.dominio.casosUso.EliminarArticuloCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerArticuloPorIdCasoUso
import com.buap.reboot.dominio.modelo.EstadoArticulo
import com.buap.reboot.nucleo.Resultado

class EditarArticuloViewModel(
    private val obtenerArticuloPorIdCasoUso: ObtenerArticuloPorIdCasoUso,
    private val editarArticuloCasoUso: EditarArticuloCasoUso,
    private val eliminarArticuloCasoUso: EliminarArticuloCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoEditarArticulo())
    val estado: LiveData<EstadoEditarArticulo> = _estado

    fun cargar(idArticulo: Long) {
        when (val resultado = obtenerArticuloPorIdCasoUso.ejecutar(idArticulo)) {
            is Resultado.Exito -> _estado.value = EstadoEditarArticulo(articulo = resultado.datos)
            is Resultado.Error -> _estado.value = EstadoEditarArticulo(error = resultado.mensaje)
        }
    }

    fun guardar(
        idArticulo: Long,
        titulo: String,
        descripcion: String,
        categoria: String,
        precioTexto: String,
        estadoArticulo: EstadoArticulo,
        ciudad: String,
        rutaImagen: String?
    ) {
        val precio = precioTexto.toDoubleOrNull()
        if (precio == null) {
            _estado.value = (_estado.value ?: EstadoEditarArticulo()).copy(error = "El precio no es valido")
            return
        }

        _estado.value = (_estado.value ?: EstadoEditarArticulo()).copy(cargando = true, error = null)
        val resultado = editarArticuloCasoUso.ejecutar(
            idArticulo = idArticulo,
            titulo = titulo,
            descripcion = descripcion,
            categoria = categoria,
            precio = precio,
            estadoArticulo = estadoArticulo,
            ciudad = ciudad,
            rutaImagen = rutaImagen
        )
        _estado.value = when (resultado) {
            is Resultado.Exito -> (_estado.value ?: EstadoEditarArticulo()).copy(cargando = false, guardadoExitosamente = true)
            is Resultado.Error -> (_estado.value ?: EstadoEditarArticulo()).copy(cargando = false, error = resultado.mensaje)
        }
    }

    fun eliminar(idArticulo: Long) {
        eliminarArticuloCasoUso.ejecutar(idArticulo)
        _estado.value = (_estado.value ?: EstadoEditarArticulo()).copy(eliminadoExitosamente = true)
    }
}
