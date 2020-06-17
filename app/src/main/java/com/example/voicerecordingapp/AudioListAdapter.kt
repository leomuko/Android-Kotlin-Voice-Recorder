package com.example.voicerecordingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_item_list.view.*
import java.io.File

class AudioListAdapter(var allFiles: Array<File>): RecyclerView.Adapter<AudioListAdapter.AudioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_item_list, parent, false)
        return AudioViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allFiles.size
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        holder.fileName.setText(allFiles[position].name)
        holder.fileDate.setText(allFiles[position].lastModified().toString())
    }
    class AudioViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var image : ImageView = itemView.list_image_view
        var fileName : TextView = itemView.list_title
        var fileDate : TextView = itemView.list_date

    }
}


