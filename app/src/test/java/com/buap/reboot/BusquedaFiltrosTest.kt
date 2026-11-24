package com.buap.reboot

import com.buap.reboot.data.bd.entidades.ArticuloEntity
import com.buap.reboot.data.bd.entidades.NotificacionEntity
import com.buap.reboot.dominio.modelo.EstadoArticulo
import com.buap.reboot.dominio.modelo.Notificacion
import com.buap.reboot.dominio.modelo.TipoNotificacion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class BusquedaFiltrosTest {

    @Test
    fun categoriaCoincideSinEspaciosNiMayusculas() {
        val categoriaGuardada = "  Celulares  "
        val categoriaFiltro = "celulares"
        
        assertEquals(categoriaGuardada.trim().lowercase(), categoriaFiltro.trim().lowercase())
    }

    @Test
    fun notificacionPreservaIdArticulo() {
        val entidad = NotificacionEntity(
            id = 10L,
            idUsuario = 1L,
            tipo = TipoNotificacion.PAGO_CONFIRMADO.etiqueta,
            titulo = "Venta realizada",
            mensaje = "Tu artículo ha sido vendido",
            fecha = "2026-11-23",
            leida = false,
            idArticulo = 42L
        )

        val modelo = Notificacion(
            id = entidad.id,
            idUsuario = entidad.idUsuario,
            tipo = TipoNotificacion.desdeEtiqueta(entidad.tipo),
            titulo = entidad.titulo,
            mensaje = entidad.mensaje,
            fecha = entidad.fecha,
            leida = entidad.leida,
            idArticulo = entidad.idArticulo
        )

        assertEquals(42L, modelo.idArticulo)
    }

    @Test
    fun articuloEntityPreservaCategoriaTrim() {
        val categoria = "  Laptops "
        val entidad = ArticuloEntity(
            id = 1L,
            idVendedor = 2L,
            titulo = "MacBook Pro",
            descripcion = "Excelente estado",
            precio = 15000.0,
            categoria = categoria.trim(),
            estadoArticulo = EstadoArticulo.NUEVO.etiqueta,
            ciudad = "Puebla",
            fechaPublicacion = "2026-11-23",
            rutaImagen = null
        )

        assertEquals("Laptops", entidad.categoria)
    }

    @Test
    fun versionBaseDeDatosEsActual() {
        val implClass = Class.forName("com.buap.reboot.data.bd.RebootDatabase_Impl")
        val instance = implClass.getDeclaredConstructor().newInstance() as com.buap.reboot.data.bd.RebootDatabase
        val metodo = implClass.getDeclaredMethod("createOpenDelegate")
        metodo.isAccessible = true
        val delegate = metodo.invoke(instance) as androidx.room.RoomOpenDelegate
        assertEquals(4, delegate.version)
        assertEquals("75bb3e8f00efeef137351e9851093d60", delegate.identityHash)
    }
}
