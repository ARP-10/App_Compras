package com.example.actividadevaluable2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.actividadevaluable2.R
import com.example.actividadevaluable2.model.Producto

class CarritoAdapter (var contexto: Context, var carrito: ArrayList<Producto>): RecyclerView.Adapter<CarritoAdapter.MyHolder>() {
    class MyHolder(item: View) : RecyclerView.ViewHolder(item){
        var nombre : TextView
        var precio : TextView
        var imagen : ImageView
        init {
            nombre = item.findViewById(R.id.txt_nombre_recycler_producto)
            precio = item.findViewById(R.id.txt_precio_recycler_producto)
            imagen = item.findViewById(R.id.img_recycler_producto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val vista:View = LayoutInflater.from(contexto).inflate(R.layout.item_recycler_carrito,parent,false)
        return MyHolder(vista)
    }

    override fun getItemCount(): Int {
        return carrito.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val producto = carrito[position]
        holder.nombre.text = producto.nombre
        holder.precio.text = producto.precio.toString() + "€"
        Glide.with(contexto).load(producto.imagen).into(holder.imagen)
    }

    fun calcularTotal(): Int {
        var total = 0
        for (producto in carrito) {
            total += producto.precio
        }
        return total
    }

}