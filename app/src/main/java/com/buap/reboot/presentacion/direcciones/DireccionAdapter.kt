package com.buap.reboot.presentacion.direcciones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.buap.reboot.databinding.ItemDireccionBinding
import com.buap.reboot.dominio.modelo.DireccionEnvio
import com.buap.reboot.dominio.modelo.direccionCompleta

class DireccionAdapter(
    private val alHacerClicEditar: (DireccionEnvio) -> Unit,
    private val alHacerClicEliminar: (DireccionEnvio) -> Unit
) : ListAdapter<DireccionEnvio, DireccionAdapter.DireccionViewHolder>(COMPARADOR) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DireccionViewHolder {
        val binding = ItemDireccionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DireccionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DireccionViewHolder, position: Int) {
        holder.enlazar(getItem(position))
    }

    inner class DireccionViewHolder(private val binding: ItemDireccionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun enlazar(direccion: DireccionEnvio) {
            binding.etiquetaDireccion.text = direccion.etiqueta
            binding.direccionCompleta.text = direccion.direccionCompleta()
            binding.telefonoDireccion.text = direccion.telefonoContacto
            binding.badgePredeterminada.visibility = if (direccion.esPredeterminada) View.VISIBLE else View.GONE
            binding.botonEditar.setOnClickListener { alHacerClicEditar(direccion) }
            binding.botonEliminar.setOnClickListener { alHacerClicEliminar(direccion) }
        }
    }

    companion object {
        private val COMPARADOR = object : DiffUtil.ItemCallback<DireccionEnvio>() {
            override fun areItemsTheSame(anterior: DireccionEnvio, nuevo: DireccionEnvio) = anterior.id == nuevo.id
            override fun areContentsTheSame(anterior: DireccionEnvio, nuevo: DireccionEnvio) = anterior == nuevo
        }
    }
}
