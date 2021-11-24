package com.olamachia.weeksixtaskandroidsq009

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>() {

    private var recyclerViewList = arrayListOf<MyModel>()
    private lateinit var onItemClickListener : OnItemClickListener


    //Determines the layout to inflate for the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.contact_layout, null, false)
        return RecyclerViewViewHolder(inflater)
    }

    //Initializes the views to be bound
        inner class RecyclerViewViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.card_layout_name)
        val phoneNumber = view.findViewById<TextView>(R.id.card_layout_number)

    }

    //Binds the views to the respective positions by the holder and set for Click listening
    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
            holder.name.text =  recyclerViewList[position].name
            holder.phoneNumber.text =  recyclerViewList[position].phone
          holder.itemView.apply {
              setOnClickListener {
                 onItemClickListener.onClick(recyclerViewList[position])
              }
          }
    }


    override fun getItemCount(): Int {
        return recyclerViewList.size
    }


    fun submitList(list: ArrayList<MyModel>){
        this.recyclerViewList = list
    }

//An interface that defines an abstract onClick method
    interface  OnItemClickListener {
        fun onClick(contact:MyModel)
    }


    //
    fun addOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

}