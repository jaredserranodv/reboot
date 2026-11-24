package com.buap.reboot.presentacion.misCompras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.buap.reboot.databinding.ItemCompraBinding
import com.buap.reboot.dominio.modelo.CompraConDetalle
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class CompraAdapter(
    private val alHacerClicResenar: (CompraConDetalle) -> Unit
) : ListAdapter<CompraConDetalle, CompraAdapter.CompraViewHolder>(COMPARADOR) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CompraViewHolder {
        val binding = ItemCompraBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompraViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {
        holder.enlazar(getItem(position))
    }

    inner class CompraViewHolder(private val binding: ItemCompraBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun enlazar(compra: CompraConDetalle) {
            binding.tituloArticulo.text = compra.articulo.titulo
            binding.precioArticulo.text = "$${compra.articulo.precio.toInt()}"
            binding.fechaRelativa.text = textoFechaRelativa(compra.transaccion.fecha)
            binding.botonResenar.visibility = if (compra.tieneResena) View.GONE else View.VISIBLE
            binding.botonResenar.setOnClickListener { alHacerClicResenar(compra) }
        }

        private fun textoFechaRelativa(fecha: String): String {
            val dias = ChronoUnit.DAYS.between(LocalDate.parse(fecha), LocalDate.now())
            return when {
                dias <= 0 -> "Comprado hoy"
                dias == 1L -> "Comprado hace 1 dia"
                else -> "Comprado hace $dias dias"
            }
        }
    }

    companion object {
        private val COMPARADOR = object : DiffUtil.ItemCallback<CompraConDetalle>() {
            override fun areItemsTheSame(anterior: CompraConDetalle, nuevo: CompraConDetalle) =
                anterior.transaccion.id == nuevo.transaccion.id
            override fun areContentsTheSame(anterior: CompraConDetalle, nuevo: CompraConDetalle) =
                anterior == nuevo
        }
    }
}
