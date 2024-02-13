package com.muhammadwaleed.i210438

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EducationMentorAdapter(
    private val mentors: List<EducationMentor>,
    private val onClick: (EducationMentor) -> Unit
) : RecyclerView.Adapter<EducationMentorAdapter.MentorViewHolder>() {

    inner class MentorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.mentorNameTextView)
        private val occupationTextView: TextView = view.findViewById(R.id.mentorOccupationTextView)
        private val statusTextView: TextView = view.findViewById(R.id.mentorStatusTextView)
        private val priceTextView: TextView = view.findViewById(R.id.mentorPriceTextView)
        private val favoriteImageView: ImageView = view.findViewById(R.id.favoriteImageView)

        fun bind(mentor: EducationMentor) {
            nameTextView.text = mentor.name
            occupationTextView.text = mentor.profession
            statusTextView.text = mentor.status
            priceTextView.text = mentor.pricePerSession
            favoriteImageView.setImageResource(
                if (mentor.isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
            )
            itemView.setOnClickListener { onClick(mentor) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.education_mentor_card_item, parent, false)
        return MentorViewHolder(view)
    }

    override fun onBindViewHolder(holder: MentorViewHolder, position: Int) {
        val mentor = mentors[position]
        holder.bind(mentor)
    }

    override fun getItemCount(): Int = mentors.size
}
