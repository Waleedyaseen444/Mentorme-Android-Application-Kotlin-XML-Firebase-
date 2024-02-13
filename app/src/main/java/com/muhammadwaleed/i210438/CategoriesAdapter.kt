package com.muhammadwaleed.i210438

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muhammadwaleed.i210438.R

class CategoriesAdapter(
    private val categories: List<Category>,
    private val onCategorySelected: (Category) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    var selectedPosition: Int = 0

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.categoryName)

        fun bind(category: Category, isSelected: Boolean, onCategorySelected: (Category) -> Unit) {
            nameTextView.text = category.name
            itemView.isSelected = isSelected
            nameTextView.setBackgroundResource(
                if (isSelected) R.drawable.selected_category_background
                else R.drawable.unselected_category_background
            )

            itemView.setOnClickListener {
                onCategorySelected(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        val isSelected = position == selectedPosition
        holder.bind(category, isSelected) { selectedCategory ->
            selectedPosition = categories.indexOf(selectedCategory)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = categories.size
}
