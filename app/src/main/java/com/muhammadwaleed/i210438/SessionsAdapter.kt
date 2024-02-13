package com.muhammadwaleed.i210438

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muhammadwaleed.i210438.R

class SessionsAdapter(private val sessions: List<SessionDetails>, private val onClick: (SessionDetails) -> Unit) :
    RecyclerView.Adapter<SessionsAdapter.SessionViewHolder>() {

    class SessionViewHolder(view: View, val onClick: (SessionDetails) -> Unit) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.session_name)
        private val professionTextView: TextView = view.findViewById(R.id.session_profession)
        private val dateTextView: TextView = view.findViewById(R.id.session_date)
        private val timeTextView: TextView = view.findViewById(R.id.session_time)
        private val imageView: ImageView = view.findViewById(R.id.session_image)
        private var currentSession: SessionDetails? = null

        init {
            view.setOnClickListener {
                currentSession?.let {
                    onClick(it)
                }
            }
        }

        fun bind(session: SessionDetails) {
            currentSession = session
            nameTextView.text = session.name
            professionTextView.text = session.profession
            dateTextView.text = session.date
            timeTextView.text = session.time
            imageView.setImageResource(session.imageResource)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sessions_item, parent, false)
        return SessionViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = sessions[position]
        holder.bind(session)
    }

    override fun getItemCount() = sessions.size
}
