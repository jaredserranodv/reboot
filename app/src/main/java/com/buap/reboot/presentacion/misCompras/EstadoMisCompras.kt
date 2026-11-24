package com.buap.reboot.presentacion.misCompras

import com.buap.reboot.dominio.modelo.CompraConDetalle

data class EstadoMisCompras(
    val cargando: Boolean = true,
    val compras: List<CompraConDetalle> = emptyList(),
    val soloEntregadas: Boolean = false,
    val error: String? = null
) {
    val comprasVisibles: List<CompraConDetalle>
        get() = if (soloEntregadas) {
            compras.filter { it.transaccion.estado == com.buap.reboot.dominio.modelo.EstadoTransaccion.ENTREGADA }
        } else {
            compras
        }
}
