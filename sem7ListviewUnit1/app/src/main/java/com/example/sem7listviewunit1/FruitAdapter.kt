package com.example.sem7listviewunit1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.RoundedCornerCompat.Position

class FruitAdapter(context: Context, private  val fruits: List<Fruit>): ArrayAdapter<Fruit>(context, 0, fruits){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        var listItemView = convertView
        if(listItemView == null){
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_view, parent, false)
        }
        val fruit = fruits[position]
        val imageView: ImageView = listItemView!!.findViewById(R.id.fruitimage)
        val textView: TextView = listItemView.findViewById(R.id.fruitname)

        imageView.setImageResource(fruit.image)
        textView.text = fruit.name

        return listItemView

    }
}