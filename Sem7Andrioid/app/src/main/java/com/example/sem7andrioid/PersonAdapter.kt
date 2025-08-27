package com.example.sem7andrioid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class PersonAdapter(private val context: Context, private val people: List<Person>) : BaseAdapter() {

    override fun getCount(): Int = people.size
    override fun getItem(position: Int): Any = people[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_item_peroson, parent, false)
        val img = view.findViewById<ImageView>(R.id.imgPerson)
        val name = view.findViewById<TextView>(R.id.txtName)
        val phone = view.findViewById<TextView>(R.id.txtPhone)

        val person = people[position]
        name.text = person.name
        phone.text = person.phone
        if (person.imageUri != null)
            img.setImageURI(person.imageUri)
        else
            img.setImageResource(R.drawable.ic_person)

        return view
    }
}
