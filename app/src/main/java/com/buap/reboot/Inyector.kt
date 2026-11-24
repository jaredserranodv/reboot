package com.buap.reboot

import android.content.Context
import com.buap.reboot.data.bd.RebootDatabase
import com.buap.reboot.data.repositorio.RepositorioArticulosImpl
import com.buap.reboot.data.repositorio.RepositorioDireccionesImpl
import com.buap.reboot.data.repositorio.RepositorioMetodosPagoImpl
import com.buap.reboot.data.repositorio.RepositorioNotificacionesImpl
import com.buap.reboot.data.repositorio.RepositorioResenasImpl
import com.buap.reboot.data.repositorio.RepositorioSesionImpl
import com.buap.reboot.data.repositorio.RepositorioTransaccionesImpl
import com.buap.reboot.data.repositorio.RepositorioUsuariosImpl
import com.buap.reboot.dominio.casosUso.ActualizarPerfilCasoUso
import com.buap.reboot.dominio.casosUso.AgregarMetodoPagoCasoUso
import com.buap.reboot.dominio.casosUso.BuscarArticulosCasoUso
import com.buap.reboot.dominio.casosUso.CambiarDisponibilidadArticuloCasoUso
import com.buap.reboot.dominio.casosUso.CerrarSesionCasoUso
import com.buap.reboot.dominio.casosUso.EditarArticuloCasoUso
import com.buap.reboot.dominio.casosUso.EliminarArticuloCasoUso
import com.buap.reboot.dominio.casosUso.EliminarDireccionCasoUso
import com.buap.reboot.dominio.casosUso.EliminarMetodoPagoCasoUso
import com.buap.reboot.dominio.casosUso.EnviarResenaCasoUso
import com.buap.reboot.dominio.casosUso.GuardarDireccionCasoUso
import com.buap.reboot.dominio.casosUso.IniciarSesionCasoUso
import com.buap.reboot.dominio.casosUso.MarcarDireccionPredeterminadaCasoUso
import com.buap.reboot.dominio.casosUso.MarcarMetodoPagoPredeterminadoCasoUso
import com.buap.reboot.dominio.casosUso.MarcarNotificacionLeidaCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerArticuloPorIdCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerConfirmacionCompraCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerDetalleArticuloCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerDireccionPorIdCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerDireccionPredeterminadaCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerArticulosPublicadosCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerDireccionesCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerMetodosPagoCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerMisComprasCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerMisPublicacionesCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerNotificacionesCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerPerfilCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerSesionActualCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerTransaccionPorArticuloCasoUso
import com.buap.reboot.dominio.casosUso.PublicarArticuloCasoUso
import com.buap.reboot.dominio.casosUso.RealizarCompraCasoUso
import com.buap.reboot.dominio.casosUso.RecuperarContrasenaCasoUso
import com.buap.reboot.dominio.casosUso.RegistrarUsuarioCasoUso

/** Contenedor manual de dependencias (sin Hilt/Dagger): raiz de composicion de la app. */
class Inyector(contexto: Context) {

    private val bd = RebootDatabase.obtenerInstancia(contexto)

    private val repositorioUsuarios = RepositorioUsuariosImpl(bd.usuarioDao())
    private val repositorioArticulos = RepositorioArticulosImpl(bd.articuloDao())
    private val repositorioTransacciones = RepositorioTransaccionesImpl(bd.transaccionDao())
    private val repositorioNotificaciones = RepositorioNotificacionesImpl(bd.notificacionDao())
    private val repositorioResenas = RepositorioResenasImpl(bd.resenaDao())
    private val repositorioDirecciones = RepositorioDireccionesImpl(bd.direccionEnvioDao())
    private val repositorioMetodosPago = RepositorioMetodosPagoImpl(bd.metodoPagoDao())
    private val repositorioSesion = RepositorioSesionImpl(contexto)

    val iniciarSesionCasoUso = IniciarSesionCasoUso(repositorioUsuarios, repositorioSesion)
    val registrarUsuarioCasoUso = RegistrarUsuarioCasoUso(repositorioUsuarios)
    val obtenerSesionActualCasoUso = ObtenerSesionActualCasoUso(repositorioSesion, repositorioUsuarios)
    val cerrarSesionCasoUso = CerrarSesionCasoUso(repositorioSesion)
    val recuperarContrasenaCasoUso = RecuperarContrasenaCasoUso(repositorioUsuarios)

    val buscarArticulosCasoUso = BuscarArticulosCasoUso(repositorioArticulos)
    val obtenerDetalleArticuloCasoUso =
        ObtenerDetalleArticuloCasoUso(repositorioArticulos, repositorioUsuarios, repositorioResenas)
    val publicarArticuloCasoUso = PublicarArticuloCasoUso(repositorioArticulos)
    val editarArticuloCasoUso = EditarArticuloCasoUso(repositorioArticulos)
    val eliminarArticuloCasoUso = EliminarArticuloCasoUso(repositorioArticulos)
    val obtenerArticuloPorIdCasoUso = ObtenerArticuloPorIdCasoUso(repositorioArticulos)
    val cambiarDisponibilidadArticuloCasoUso = CambiarDisponibilidadArticuloCasoUso(repositorioArticulos)
    val obtenerMisPublicacionesCasoUso = ObtenerMisPublicacionesCasoUso(repositorioArticulos)
    val obtenerArticulosPublicadosCasoUso = ObtenerArticulosPublicadosCasoUso(repositorioArticulos)

    val realizarCompraCasoUso = RealizarCompraCasoUso(
        repositorioArticulos, repositorioTransacciones, repositorioNotificaciones
    )
    val obtenerMisComprasCasoUso =
        ObtenerMisComprasCasoUso(repositorioTransacciones, repositorioArticulos, repositorioResenas)
    val obtenerTransaccionPorArticuloCasoUso = ObtenerTransaccionPorArticuloCasoUso(repositorioTransacciones)
    val obtenerConfirmacionCompraCasoUso = ObtenerConfirmacionCompraCasoUso(repositorioTransacciones, repositorioArticulos)
    val enviarResenaCasoUso = EnviarResenaCasoUso(repositorioResenas)

    val obtenerNotificacionesCasoUso = ObtenerNotificacionesCasoUso(repositorioNotificaciones)
    val marcarNotificacionLeidaCasoUso = MarcarNotificacionLeidaCasoUso(repositorioNotificaciones)

    val obtenerPerfilCasoUso = ObtenerPerfilCasoUso(
        repositorioUsuarios, repositorioArticulos, repositorioTransacciones, repositorioResenas
    )
    val actualizarPerfilCasoUso = ActualizarPerfilCasoUso(repositorioUsuarios)

    val obtenerDireccionesCasoUso = ObtenerDireccionesCasoUso(repositorioDirecciones)
    val obtenerDireccionPorIdCasoUso = ObtenerDireccionPorIdCasoUso(repositorioDirecciones)
    val obtenerDireccionPredeterminadaCasoUso = ObtenerDireccionPredeterminadaCasoUso(repositorioDirecciones)
    val guardarDireccionCasoUso = GuardarDireccionCasoUso(repositorioDirecciones)
    val eliminarDireccionCasoUso = EliminarDireccionCasoUso(repositorioDirecciones)
    val marcarDireccionPredeterminadaCasoUso = MarcarDireccionPredeterminadaCasoUso(repositorioDirecciones)

    val obtenerMetodosPagoCasoUso = ObtenerMetodosPagoCasoUso(repositorioMetodosPago)
    val agregarMetodoPagoCasoUso = AgregarMetodoPagoCasoUso(repositorioMetodosPago)
    val eliminarMetodoPagoCasoUso = EliminarMetodoPagoCasoUso(repositorioMetodosPago)
    val marcarMetodoPagoPredeterminadoCasoUso = MarcarMetodoPagoPredeterminadoCasoUso(repositorioMetodosPago)
}
