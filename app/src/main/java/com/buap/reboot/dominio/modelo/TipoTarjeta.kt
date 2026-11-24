package com.buap.reboot.dominio.modelo

enum class TipoTarjeta(val etiqueta: String) {
    VISA("visa"),
    MASTERCARD("mastercard");

    companion object {
        fun desdeEtiqueta(etiqueta: String): TipoTarjeta =
            entries.firstOrNull { it.etiqueta == etiqueta } ?: VISA

        // TODO: definir regla real de deteccion de marca; por ahora se infiere por el primer digito
        fun desdeNumero(numeroTarjeta: String): TipoTarjeta =
            if (numeroTarjeta.startsWith("4")) VISA else MASTERCARD
    }
}
