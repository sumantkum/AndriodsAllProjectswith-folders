package com.example.cse226unit1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat

class ContactAdapter(private val context: Context, private val contacts: List<Contact>) :
    BaseAdapter() {

    override fun getCount(): Int = contacts.size

    override fun getItem(position: Int): Any = contacts[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder
        val rowView: View

        if (convertView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.list_item_contact, parent, false)
            viewHolder = ViewHolder()
            viewHolder.nameText = rowView.findViewById(R.id.textName)
            viewHolder.phoneText = rowView.findViewById(R.id.textPhone)
            viewHolder.callBtn = rowView.findViewById(R.id.btnCall)
            rowView.tag = viewHolder
        } else {
            rowView = convertView
            viewHolder = rowView.tag as ViewHolder
        }

        val contact = contacts[position]
        viewHolder.nameText?.text = contact.name
        viewHolder.phoneText?.text = contact.phone

        viewHolder.callBtn?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${contact.phone}")
            ContextCompat.startActivity(context, intent, null)
        }

        return rowView
    }

    private class ViewHolder {
        var nameText: TextView? = null
        var phoneText: TextView? = null
        var callBtn: ImageButton? = null
    }
}
