package com.muhammadwaleed.i210438

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ReviewsAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reviewerNameTextView: TextView = view.findViewById(R.id.reviewer_name)
        val reviewTextView: TextView = view.findViewById(R.id.review_text)
        val ratingBar: RatingBar = view.findViewById(R.id.rating_bar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.reviewerNameTextView.text = review.reviewerName
        holder.reviewTextView.text = review.reviewText
        holder.ratingBar.rating = review.rating

        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, Feedback::class.java)
            intent.putExtra("reviewerName", review.reviewerName)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = reviews.size
}
