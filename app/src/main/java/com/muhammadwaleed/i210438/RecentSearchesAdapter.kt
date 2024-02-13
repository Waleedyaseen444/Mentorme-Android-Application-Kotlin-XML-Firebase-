import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.muhammadwaleed.i210438.R

class RecentSearchesAdapter(
    private val recentSearches: MutableList<String>,
    private val onItemClick: (String) -> Unit,
    private val onItemRemoved: (String) -> Unit
) : RecyclerView.Adapter<RecentSearchesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val editTextItem: EditText = itemView.findViewById(R.id.editTextItem)

        init {
            itemView.setOnClickListener {
                onItemClick(recentSearches[adapterPosition])
            }

            itemView.findViewById<View>(R.id.btnRemove).setOnClickListener {
                val removedItem = recentSearches[adapterPosition]
                recentSearches.remove(removedItem)
                notifyDataSetChanged()
                onItemRemoved(removedItem)
            }
        }

        fun bind(searchText: String) {
            editTextItem.setText(searchText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_recent_search, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recentSearches[position])
    }

    override fun getItemCount(): Int = recentSearches.size
}