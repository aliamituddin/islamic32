package com.route.islami.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.islami.R


/**
 * Created by Mohamed Nabil Mohamed on 5/12/2020.
 * m.nabil.fci2015@gmail.com
 */
class VersesRecyclerAdapter() : RecyclerView.Adapter<VersesRecyclerAdapter.ViewHolder>() {

    var versesList = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_verse, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return versesList.size
    }

    fun changeData(data: List<String>) {
        this.versesList = data
        notifyDataSetChanged()

    }

    interface OnItemClickListener {
        fun onItemClick(pos: Int, item: String)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.content.setText(versesList.get(position) + " {" + (position + 1) + "}")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var content: TextView

        init {
            content = itemView.findViewById(R.id.content)
        }

    }

}