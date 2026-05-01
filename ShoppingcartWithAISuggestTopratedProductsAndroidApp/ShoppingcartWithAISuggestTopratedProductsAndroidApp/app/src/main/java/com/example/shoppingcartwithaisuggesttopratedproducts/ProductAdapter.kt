package com.example.shoppingcartwithaisuggesttopratedproducts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val items: MutableList<Product>,
    private val onCartChanged: () -> Unit
) : RecyclerView.Adapter<ProductAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvName: TextView = v.findViewById(R.id.tvName)
        val tvDesc: TextView = v.findViewById(R.id.tvDesc)
        val tvRating: TextView = v.findViewById(R.id.tvRating)
        val tvPrice: TextView = v.findViewById(R.id.tvPrice)
        val btnAdd: Button = v.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val p = items[pos]
        h.tvName.text = p.name
        h.tvDesc.text = p.description
        h.tvRating.text = "⭐ ${"%.1f".format(p.rating)}"
        h.tvPrice.text = "₹${"%.2f".format(p.price)}"
        h.btnAdd.setOnClickListener {
            CartManager.add(p)
            onCartChanged()
        }
    }

    override fun getItemCount() = items.size

    fun submit(newItems: List<Product>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
