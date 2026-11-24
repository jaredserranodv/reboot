package com.buap.reboot.presentacion.misPublicaciones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.buap.reboot.R
import com.buap.reboot.databinding.ItemPublicacionBinding
import com.buap.reboot.dominio.modelo.Articulo
import com.buap.reboot.presentacion.comun.cargarFotoArticulo

class PublicacionAdapter(
    private val alHacerClicEditar: (Articulo) -> Unit,
    private val alHacerClicReactivar: (Articulo) -> Unit,
    private val alHacerClicResenarComprador: (Articulo) -> Unit
) : ListAdapter<Articulo, PublicacionAdapter.PublicacionViewHolder>(COMPARADOR) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PublicacionViewHolder {
        val binding = ItemPublicacionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PublicacionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PublicacionViewHolder, position: Int) {
        holder.enlazar(getItem(position))
    }

    inner class PublicacionViewHolder(private val binding: ItemPublicacionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun enlazar(articulo: Articulo) {
            binding.tituloArticulo.text = articulo.titulo
            binding.precioArticulo.text = "$${articulo.precio.toInt()}"
            binding.imagenArticulo.setImageDrawable(null)
            binding.imagenArticulo.cargarFotoArticulo(articulo.rutaImagen)

            if (articulo.disponible) {
                binding.badgeEstado.setText(R.string.publicaciones_badge_activo)
                binding.botonAccion.setText(R.string.publicaciones_boton_editar)
                binding.botonAccion.setOnClickListener { alHacerClicEditar(articulo) }
                binding.botonResenarComprador.visibility = View.GONE
            } else {
                binding.badgeEstado.setText(R.string.publicaciones_badge_vendido)
                binding.botonAccion.setText(R.string.publicaciones_boton_reactivar)
                binding.botonAccion.setOnClickListener { alHacerClicReactivar(articulo) }
                binding.botonResenarComprador.visibility = View.VISIBLE
                binding.botonResenarComprador.setOnClickListener { alHacerClicResenarComprador(articulo) }
            }
        }
    }

    companion object {
        private val COMPARADOR = object : DiffUtil.ItemCallback<Articulo>() {
            override fun areItemsTheSame(anterior: Articulo, nuevo: Articulo) = anterior.id == nuevo.id
            override fun areContentsTheSame(anterior: Articulo, nuevo: Articulo) = anterior == nuevo
        }
    }
}
