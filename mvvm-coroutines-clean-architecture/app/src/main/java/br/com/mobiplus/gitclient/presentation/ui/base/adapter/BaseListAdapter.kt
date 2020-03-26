package br.com.mobiplus.gitclient.presentation.ui.base.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseListAdapter<T>(private val callback: (T) -> Unit) :
    RecyclerView.Adapter<BaseListAdapter<T>.ViewHolder>() {

    private var items = ArrayList<T>()

    abstract fun getListItemView(context: Context): BaseViewHolder<T>

    fun clearItem() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addItems(itemsToAdd: List<T>) {
        items.addAll(itemsToAdd)
        notifyDataSetChanged()
    }

    fun isEmpty() = items.isEmpty()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(getListItemView(parent.context))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]) {
            holder.view.bind(items[position])
            holder.view.setOnClickListener { callback(this) }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val view: BaseViewHolder<T>) : RecyclerView.ViewHolder(view)

}