package com.muhammadwaleed.i210438

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RecentAdapter(private val dataList: List<Recentdata>) : RecyclerView.Adapter<RecentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileIcon: ImageView = itemView.findViewById(R.id.profileIcon)
        val sampleText: TextView = itemView.findViewById(R.id.sampleText)
        val occupationText: TextView = itemView.findViewById(R.id.occupationText)
        val statusText: TextView = itemView.findViewById(R.id.statusText)
        val priceText: TextView = itemView.findViewById(R.id.priceText)
        val heartIcon: ImageView = itemView.findViewById(R.id.heartIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.sampleText.text = currentItem.sampleText
        holder.occupationText.text = currentItem.occupationText
        holder.statusText.text = currentItem.statusText
        holder.priceText.text = currentItem.priceText

        // Load mentor image using Glide
        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.profileIcon)

        holder.heartIcon.setImageResource(currentItem.heartIconRes)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
