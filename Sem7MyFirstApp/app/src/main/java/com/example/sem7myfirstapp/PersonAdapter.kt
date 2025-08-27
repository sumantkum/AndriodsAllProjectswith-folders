package com.example.sem7myfirstapp
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class PersonAdapter(
    private val context: Context,
    private val items: List<Person>
) : BaseAdapter() {

    override fun getCount() = items.size
    override fun getItem(position: Int) = items[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val rowView: View

        if (convertView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.row_person, parent, false)
            holder = ViewHolder(
                rowView.findViewById(R.id.imgAvatar),
                rowView.findViewById(R.id.tvName),
                rowView.findViewById(R.id.tvSubtitle)
            )
            rowView.tag = holder
        } else {
            rowView = convertView
            holder = convertView.tag as ViewHolder
        }

        val item = getItem(position)
        holder.tvName.text = item.name
        holder.tvSubtitle.text = item.SubTitle
        // holder.imgAvatar.setImageResource(...) // set dynamic image if you have

        return rowView
    }

    private data class ViewHolder(
        val imgAvatar: ImageView,
        val tvName: TextView,
        val tvSubtitle: TextView
    )
}
