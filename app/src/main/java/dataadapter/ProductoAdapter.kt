package dataadapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import dataclass.Producto
import ni.edu.uca.listadoprod.databinding.ActivityMainBinding
import ni.edu.uca.listadoprod.databinding.ItemlistaBinding


class ProductoAdapter(
    val listProd : List<Producto>,
    private val onClickListener: (Producto) -> Unit,
    private val deleteClickListener: (Producto) -> Unit
    ): RecyclerView.Adapter<ProductoAdapter.ProductoHolder>(){


    inner class ProductoHolder(val binding: ItemlistaBinding) : RecyclerView.ViewHolder(binding.root){
        fun cargar(
            producto: Producto,
            onClickListener: (Producto) -> Unit,
            deleteClickListener: (Producto) -> Unit
        ){
            with(binding){
                tvCodProd.text = producto.id.toString()
                tvNombreProd.text = producto.nombre
                tvPrecioProd.text = "C$ ${producto.precio.toString()}"

                btnEditar.setOnClickListener {
                    onClickListener(producto)
                }

                btnEliminar.setOnClickListener {
                    deleteClickListener(producto)
                }
            }
        }
    }

    override fun getItemCount(): Int = listProd.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val binding = ItemlistaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        holder.cargar(listProd[position],onClickListener,deleteClickListener)
    }

}

