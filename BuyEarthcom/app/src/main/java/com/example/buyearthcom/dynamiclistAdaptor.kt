package com.example.buyearthcom

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class dynamiclistAdaptor (
    private val context : Context,
    private val itme : List<dynamiclistdata>

) : BaseAdapter(){
    override fun getCount() : Int = itme.size
    override fun getItem(position: Int): Any = itme(position)
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, counterView : View?, parent: ViewGroup): View{
        val view = counterView ? LayoutInflater.from(context).inflate(R.layout.dynamiclistitem, parent, false) : counterView

    }


}