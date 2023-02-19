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
import coil.load
import coil.transform.CircleCropTransformation

import com.example.retrofitdemo.R
import com.example.retrofitdemo.ShowImage
import com.example.retrofitdemo.models.PhotosList
import com.squareup.picasso.Picasso

class RVPhotoAdapter(private val mList: PhotosList) : RecyclerView.Adapter<RVPhotoAdapter.ViewHolder>() {
    private var _context:Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_image_item, parent, false)
        _context = parent.context
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.textViewPhotoTitle.text = "Title:   ${mList?.get(position)?.title}"
        holder.textViewPhotoId.text = "Id:   ${mList?.get(position)?.id.toString()}"
        holder.imageView.load(mList.get(position).url) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }
        holder.imageView.setOnClickListener{
            val intent = Intent(_context, ShowImage::class.java)
            val bundle = Bundle()
            bundle.putString("image_url", mList?.get(position)?.url)
            intent.putExtras(bundle)
            _context?.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_photo)
        val textViewPhotoTitle: TextView = itemView.findViewById(R.id.tv_photo_title)
        val textViewPhotoId: TextView = itemView.findViewById(R.id.tv_photo_id)
        val textViewThumbUrl: TextView = itemView.findViewById(R.id.tv_photo_title)

    }
}