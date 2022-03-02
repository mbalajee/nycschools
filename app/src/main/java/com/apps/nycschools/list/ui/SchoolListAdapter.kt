package com.apps.nycschools.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apps.nycschools.databinding.ListItemLoadingBinding
import com.apps.nycschools.databinding.ListItemSchoolBinding
import com.apps.nycschools.list.model.School

/**
 * Displays the school list
 *
 * @param onClickSchool Callback to handle the click event on school list item taking [School.id] as a parameter
 */
class SchoolListAdapter(private val onClickSchool: (String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<SchoolItem>()

    fun addSchools(newSchools: List<School>) {
        val insertionIdx = this.items.size
        this.items.addAll(newSchools.map { SchoolItem(it, SchoolListItemType.School) })
        notifyItemRangeInserted(insertionIdx, newSchools.size)
    }

    /**
     * Safely adds a loading list item at the end of the list.
     * by ensuring that maximum of one loading item is present even
     * if this is called with <i>true</i> multiple times.
     */
    fun setLoading(enabled: Boolean) {
        if (enabled) {
            // Add only if the last item type is NOT loading
           if (items.lastOrNull()?.type == SchoolListItemType.School) {
               items.add(SchoolItem(type = SchoolListItemType.Loading))
               notifyItemInserted(items.size - 1)
           }
        } else {
            // Remove only if last item type IS loading
            if (items.lastOrNull()?.type == SchoolListItemType.Loading) {
                items.removeLastOrNull()
                notifyItemRemoved(items.size - 1)
            }
        }
    }

    class SchoolViewHolder(val binding: ListItemSchoolBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: School) {
            binding.schoolName.text = data.name
            binding.schoolDBN.text = data.id.uppercase()
        }
    }

    class LoadingViewHolder(binding: ListItemLoadingBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : RecyclerView.ViewHolder = when (SchoolListItemType.values()[viewType]) {
            SchoolListItemType.School -> SchoolViewHolder(
                ListItemSchoolBinding.inflate(parent.inflater(), parent, false)
            ).also { holder ->
                holder.binding.root.setOnClickListener {
                    onClickSchool(
                        items[holder.adapterPosition].school!!.id // Safe to use !! since a School can never be without ID
                    )
                }
            }
            SchoolListItemType.Loading -> LoadingViewHolder(ListItemLoadingBinding.inflate(parent.inflater(), parent, false))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SchoolViewHolder) {
            items[position].school?.let { holder.bind(it) }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].type.ordinal

    val isLoading: Boolean get() = getItemViewType(itemCount - 1) == SchoolListItemType.Loading.ordinal

    private fun ViewGroup.inflater(): LayoutInflater = LayoutInflater.from(context)
}