package com.example.shoppingcartwithaisuggesttopratedproducts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val items: MutableList<Product>,
    private val onChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvName: TextView = v.findViewById(R.id.tvCartName)
        val tvPrice: TextView = v.findViewById(R.id.tvCartPrice)
        val btnRemove: Button = v.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val p = items[pos]
        h.tvName.text = p.name
        h.tvPrice.text = "₹${"%.2f".format(p.price)}"
        h.btnRemove.setOnClickListener {
            CartManager.remove(p)
            items.removeAt(pos)
            notifyItemRemoved(pos)
            onChanged()
        }
    }

    override fun getItemCount() = items.size
}
