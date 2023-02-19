package com.example.retrofitdemo.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitdemo.R
import com.example.retrofitdemo.ShowImage
import com.example.retrofitdemo.models.DataClass
import com.example.retrofitdemo.models.PhotosList
import com.squareup.picasso.Picasso

class RVMyDataAdapter(private val mList: DataClass) : RecyclerView.Adapter<RVMyDataAdapter.ViewHolder>() {
    private var _context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_mydata_item, parent, false)
        _context = parent.context
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       holder.textViewDataTitle.text = "Title: " + mList?.get(position)?.title
       holder.textViewDataId.text = "Id: " + mList?.get(position)?.id.toString()
       holder.textViewDataBody.text = "Body: " + mList?.get(position)?.body
    }
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val textViewDataTitle: TextView = itemView.findViewById(R.id.tv_data_title)
        val textViewDataId: TextView = itemView.findViewById(R.id.tv_data_id)
        val textViewDataBody: TextView = itemView.findViewById(R.id.tv_data_body)

    }
}