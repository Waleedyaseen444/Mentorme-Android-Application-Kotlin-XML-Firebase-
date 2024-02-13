package com.muhammadwaleed.i210438

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class CommunityAdapter(
    private val communityMembers: List<CommunityMember>,
    private val onClick: (CommunityMember) -> Unit
) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileImage: ImageView = itemView.findViewById(R.id.community_profile_image)

        fun bind(communityMember: CommunityMember) {
            profileImage.setImageResource(communityMember.profileImageResource)
            itemView.setOnClickListener { onClick(communityMember) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.community_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(communityMembers[position])
    }

    override fun getItemCount() = communityMembers.size
}
