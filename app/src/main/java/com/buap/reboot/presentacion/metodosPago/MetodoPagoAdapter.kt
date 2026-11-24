package com.buap.reboot.presentacion.metodosPago

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.buap.reboot.databinding.ItemMetodoPagoBinding
import com.buap.reboot.dominio.modelo.MetodoPago
import com.buap.reboot.dominio.modelo.TipoTarjeta

class MetodoPagoAdapter(
    private val alHacerClicEliminar: (MetodoPago) -> Unit
) : ListAdapter<MetodoPago, MetodoPagoAdapter.MetodoPagoViewHolder>(COMPARADOR) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MetodoPagoViewHolder {
        val binding = ItemMetodoPagoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MetodoPagoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MetodoPagoViewHolder, position: Int) {
        holder.enlazar(getItem(position))
    }

    inner class MetodoPagoViewHolder(private val binding: ItemMetodoPagoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun enlazar(metodoPago: MetodoPago) {
            val nombreTarjeta = if (metodoPago.tipoTarjeta == TipoTarjeta.VISA) "Visa" else "Mastercard"
            binding.descripcionTarjeta.text = "$nombreTarjeta •••• ${metodoPago.ultimosCuatroDigitos}"
            binding.vencimientoTarjeta.text =
                "Vence %02d/%d".format(metodoPago.mesVencimiento, metodoPago.anioVencimiento)
            binding.badgePredeterminado.visibility = if (metodoPago.esPredeterminado) View.VISIBLE else View.GONE
            binding.botonEliminar.setOnClickListener { alHacerClicEliminar(metodoPago) }
        }
    }

    companion object {
        private val COMPARADOR = object : DiffUtil.ItemCallback<MetodoPago>() {
            override fun areItemsTheSame(anterior: MetodoPago, nuevo: MetodoPago) = anterior.id == nuevo.id
            override fun areContentsTheSame(anterior: MetodoPago, nuevo: MetodoPago) = anterior == nuevo
        }
    }
}
