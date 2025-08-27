package com.example.sem7gridlayoutunit1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class GridAdapter(
    private val context: Context,
    private val items: List<GridItem>
): BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = items[position]
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false)

        val imageView: ImageView = view.findViewById(R.id.grid_image)
        val textView: TextView = view.findViewById(R.id.itemtext)

        imageView.setImageResource(item.imageResId)
        textView.text = item.title

        return view

    }

}

data class GridItem(
    val imageResId: Int,
    val title: String
)