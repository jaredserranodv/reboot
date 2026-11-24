package com.buap.reboot.presentacion.comun

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.buap.reboot.databinding.ItemArticuloBinding
import com.buap.reboot.dominio.modelo.Articulo

class ArticuloAdapter(
    private val alHacerClic: (Articulo) -> Unit
) : ListAdapter<Articulo, ArticuloAdapter.ArticuloViewHolder>(COMPARADOR) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ArticuloViewHolder {
        val binding = ItemArticuloBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticuloViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticuloViewHolder, position: Int) {
        holder.enlazar(getItem(position))
    }

    inner class ArticuloViewHolder(private val binding: ItemArticuloBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun enlazar(articulo: Articulo) {
            binding.tituloArticulo.text = articulo.titulo
            binding.precioArticulo.text = "$${articulo.precio.toInt()}"
            binding.imagenArticulo.setImageDrawable(null)
            binding.imagenArticulo.cargarFotoArticulo(articulo.rutaImagen)
            binding.root.setOnClickListener { alHacerClic(articulo) }
        }
    }

    companion object {
        private val COMPARADOR = object : DiffUtil.ItemCallback<Articulo>() {
            override fun areItemsTheSame(anterior: Articulo, nuevo: Articulo) = anterior.id == nuevo.id
            override fun areContentsTheSame(anterior: Articulo, nuevo: Articulo) = anterior == nuevo
        }
    }
}
