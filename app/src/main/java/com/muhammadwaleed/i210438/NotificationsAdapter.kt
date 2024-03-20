package com.muhammadwaleed.i210438

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muhammadwaleed.i210438.R

class NotificationsAdapter(
    private var notifications: MutableList<Notification>,
    private val onItemDismiss: (Notification) -> Unit
) : RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(view: View, onItemDismiss: (Notification) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val messageTextView: TextView = view.findViewById(R.id.notification_message)
        private val dismissButton: ImageButton = view.findViewById(R.id.notification_dismiss)
        private var currentNotification: Notification? = null

        init {
            dismissButton.setOnClickListener {
                currentNotification?.let { notification ->
                    onItemDismiss(notification)
                }
            }
        }

        fun bind(notification: Notification) {
            currentNotification = notification
            messageTextView.text = notification.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return NotificationViewHolder(view, onItemDismiss)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    fun setData(newNotifications: MutableList<Notification>) {
        notifications = newNotifications
        notifyDataSetChanged()
    }

    fun removeItem(notification: Notification) {
        val position = notifications.indexOf(notification)
        if (position != -1) {
            notifications.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clearAll() {
        notifications.clear()
        notifyDataSetChanged()
    }
}
