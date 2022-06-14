package com.example.rest


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class CustomAdapter(context: Food, food: ArrayList<Foodmenu>)
    :ArrayAdapter<Foodmenu>(context,0,food){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listitem=LayoutInflater.from(context).inflate(R.layout.list_row,parent,false)

        val item=getItem(position)
        listitem.findViewById<TextView>(R.id.textView).text=item!!.name
        listitem.findViewById<ImageView>(R.id.imageView).setImageResource(item.image)
        return listitem
    }
}