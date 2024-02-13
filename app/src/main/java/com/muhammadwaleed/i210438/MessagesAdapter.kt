package com.muhammadwaleed.i210438

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessagesAdapter(
    private val persons: List<Person>,
    private val onClick: () -> Unit
) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val personImageView: ImageView = itemView.findViewById(R.id.person_image)
        private val personNameView: TextView = itemView.findViewById(R.id.person_name)
        private val messagePreviewView: TextView = itemView.findViewById(R.id.message_preview)

        fun bind(person: Person) {
            personImageView.setImageResource(person.imageResource)
            personNameView.text = person.name
            messagePreviewView.text = person.messagePreview
            itemView.setOnClickListener { onClick() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(persons[position])
    }

    override fun getItemCount() = persons.size
}
