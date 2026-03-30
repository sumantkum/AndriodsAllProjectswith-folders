package com.example.cse226unit1
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerContactAdapter (private val context: Context, private val contactList: List<Contact>) :
    RecyclerView.Adapter<RecyclerContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.textName)
        val phoneText: TextView = itemView.findViewById(R.id.textPhone)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                val contact = contactList[position]
                Toast.makeText(context, "Clicked: ${contact.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.nameText.text = contact.name
        holder.phoneText.text = contact.phone
    }

    override fun getItemCount(): Int = contactList.size
}