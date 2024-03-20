package com.muhammadwaleed.i210438

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FavouriteMentorsAdapter(private val mentors: List<Mentor>) :
    RecyclerView.Adapter<FavouriteMentorsAdapter.MentorViewHolder>() {

    class MentorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.mentor_name)
        val professionTextView: TextView = view.findViewById(R.id.mentor_profession)
        val heartImageView: ImageView = view.findViewById(R.id.mentor_heart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mentor_item, parent, false)
        return MentorViewHolder(view)
    }

    override fun onBindViewHolder(holder: MentorViewHolder, position: Int) {
        val mentor = mentors[position]
        holder.nameTextView.text = mentor.name
        holder.professionTextView.text = mentor.profession
        holder.heartImageView.setImageResource(
            if (mentor.isFavorite) R.drawable.hearts else R.drawable.ic_heart_outline
        )

        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, Mentordetails::class.java)
            intent.putExtra("mentorName", mentor.name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = mentors.size
}