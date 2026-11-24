package com.buap.reboot.presentacion.comun

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.buap.reboot.databinding.ItemNotificacionBinding
import com.buap.reboot.dominio.modelo.Notificacion
import com.buap.reboot.dominio.modelo.TipoNotificacion

class NotificacionAdapter(
    private val alHacerClic: (Notificacion) -> Unit
) : ListAdapter<Notificacion, NotificacionAdapter.NotificacionViewHolder>(COMPARADOR) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NotificacionViewHolder {
        val binding = ItemNotificacionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificacionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificacionViewHolder, position: Int) {
        holder.enlazar(getItem(position))
    }

    inner class NotificacionViewHolder(private val binding: ItemNotificacionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun enlazar(notificacion: Notificacion) {
            binding.tituloNotificacion.text = notificacion.titulo
            binding.mensajeNotificacion.text = notificacion.mensaje
            binding.fechaNotificacion.text = notificacion.fecha
            binding.iconoTipoNotificacion.setImageResource(iconoPara(notificacion.tipo))
            binding.root.alpha = if (notificacion.leida) ALPHA_LEIDA else ALPHA_NO_LEIDA
            binding.root.setOnClickListener { alHacerClic(notificacion) }
        }

        private fun iconoPara(tipo: TipoNotificacion): Int = when (tipo) {
            TipoNotificacion.MENSAJE -> android.R.drawable.ic_dialog_email
            TipoNotificacion.OFERTA -> android.R.drawable.star_big_on
            TipoNotificacion.PAGO_CONFIRMADO -> android.R.drawable.checkbox_on_background
            TipoNotificacion.RECORDATORIO -> android.R.drawable.ic_popup_reminder
        }
    }

    companion object {
        private const val ALPHA_LEIDA = 0.5f
        private const val ALPHA_NO_LEIDA = 1.0f

        private val COMPARADOR = object : DiffUtil.ItemCallback<Notificacion>() {
            override fun areItemsTheSame(anterior: Notificacion, nuevo: Notificacion) = anterior.id == nuevo.id
            override fun areContentsTheSame(anterior: Notificacion, nuevo: Notificacion) = anterior == nuevo
        }
    }
}
