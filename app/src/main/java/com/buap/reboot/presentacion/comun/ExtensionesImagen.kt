package com.buap.reboot.presentacion.comun

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val alcanceCargaImagenes = CoroutineScope(Dispatchers.Main)

/**
 * Sin esto, el permiso de lectura sobre la foto elegida se pierde al reabrir la app y la
 * Uri guardada en Room queda invalida. El selector de fotos moderno (Photo Picker) ya entrega
 * Uris de lectura duradera por si solas y no admite permiso persistente, de ahi el try/catch.
 */
fun Context.tomarPermisoPersistente(uri: Uri) {
    try {
        contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
    } catch (e: SecurityException) {
        // Uri del Photo Picker: no soporta permiso persistente, no hace falta tomarlo.
    }
}

/**
 * Carga la foto guardada como content:// Uri fuera del hilo principal. Si rutaImagen es null/vacia
 * o la Uri ya no es legible, no se toca el ImageView y queda el placeholder que ya tenia.
 */
fun ImageView.cargarFotoArticulo(rutaImagen: String?) {
    if (rutaImagen.isNullOrBlank()) return
    val contexto = context
    alcanceCargaImagenes.launch {
        val bitmap = withContext(Dispatchers.IO) {
            try {
                contexto.contentResolver.openInputStream(Uri.parse(rutaImagen))?.use { BitmapFactory.decodeStream(it) }
            } catch (e: Exception) {
                null
            }
        }
        if (bitmap != null) setImageBitmap(bitmap)
    }
}
