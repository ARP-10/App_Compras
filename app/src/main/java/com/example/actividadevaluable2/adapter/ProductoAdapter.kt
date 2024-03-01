package com.example.actividadevaluable2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.actividadevaluable2.R
import com.example.actividadevaluable2.SecondActivity
import com.example.actividadevaluable2.model.Producto

class ProductoAdapter(var contexto: Context, var btnCarro: ImageButton, var lista:ArrayList<Producto>, var carro:ArrayList<Producto>): RecyclerView.Adapter<ProductoAdapter.MyHolder>() {

    init {
        lista = ArrayList()
        carro = ArrayList()
    }
    private var listaCompleta: ArrayList<Producto> = lista
    private var posicion = 1




    class MyHolder(item: View) : RecyclerView.ViewHolder(item){
        var nombre : TextView
        var precio : TextView
        var imagen : ImageView
        var boton : Button

        init {
            nombre = item.findViewById(R.id.txt_nombre_recycler_producto)
            precio = item.findViewById(R.id.txt_precio_recycler_producto)
            imagen = item.findViewById(R.id.img_recycler_producto)
            boton = item.findViewById(R.id.btn_agregar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoAdapter.MyHolder {

        val vista:View = LayoutInflater.from(contexto).inflate(R.layout.item_recycler_producto,parent,false)
        return MyHolder(vista)
    }

    override fun onBindViewHolder(holder: ProductoAdapter.MyHolder, position: Int) {
        val producto = lista[position]
        holder.nombre.text = producto.nombre
        holder.precio.text = producto.precio.toString() + "€"
        Glide.with(contexto).load(producto.imagen).into(holder.imagen)
        holder.boton.setOnClickListener {
            añadirCarro(position)

        }

        btnCarro.setOnClickListener {
            val intent = Intent(contexto, SecondActivity::class.java)
            if(!carro.equals(null)) {
                intent.putExtra("carro", carro)
                contexto.startActivity(intent)
            } else{

            }
        }


    }


    override fun getItemCount(): Int {
        return lista.size
    }

    fun añadirCarro(position: Int){
        carro.add(lista[position])
    }

    fun devolverCarro() : ArrayList<Producto>{
        return carro
    }

    fun addProducto(producto: Producto){
        this.lista.add(producto)
        notifyItemInserted(lista.size-1)
    }

    fun filtrarLista(categoria: String){
        if(categoria.equals("Todos")){
            this.lista = listaCompleta
        } else {
            this.lista = listaCompleta.filter {
                it.categoria.equals(categoria,true)
            } as ArrayList<Producto>
        }
        notifyDataSetChanged()
    }

}