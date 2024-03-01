package com.example.actividadevaluable2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actividadevaluable2.adapter.CarritoAdapter
import com.example.actividadevaluable2.databinding.ActivitySecondBinding
import com.example.actividadevaluable2.model.Producto
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var adaptadorCarrito: CarritoAdapter
    private lateinit var carrito: ArrayList<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carrito = ArrayList()
        carrito = intent.getSerializableExtra("carro") as ArrayList<Producto>
        completarRecyclerView()
        binding.btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)
        }

        binding.btnFinalizarCompra.setOnClickListener {
            finalizarCompra(carrito)
        }

        binding.btnCarrovacio.setOnClickListener {
            vaciarCarrito()
        }
    }

    fun completarRecyclerView(){
        binding.recyclerItemsCarrito.layoutManager = LinearLayoutManager(this)
        adaptadorCarrito = CarritoAdapter(this, carrito)
        binding.recyclerItemsCarrito.adapter = adaptadorCarrito
        adaptadorCarrito.notifyDataSetChanged()

        // Calcular y mostrar el total de precios
        val total = adaptadorCarrito.calcularTotal()
        binding.txtMostrarPrecio.text = "Total: $total€"
    }

    fun finalizarCompra(carro: ArrayList<Producto>){
        var precioTotal = 0
        for (i in 0 until carro.size){
            precioTotal += carro[i].precio
        }
        Snackbar.make(binding.root, "Enhorabuena, compra por valor de ${precioTotal}€ realizada", Snackbar.LENGTH_SHORT).show()
    }

    private fun vaciarCarrito() {
        carrito.clear()
        adaptadorCarrito.notifyDataSetChanged()
        binding.recyclerItemsCarrito.removeAllViews()

        Snackbar.make(binding.root, "Carrito vaciado", Snackbar.LENGTH_SHORT).show()
    }
}