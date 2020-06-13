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
class SurasRecyclerAdapter() : RecyclerView.Adapter<SurasRecyclerAdapter.ViewHolder>() {

    var surasList = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sura, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return surasList.size
    }

    fun changeData(data: List<String>) {
        this.surasList = data
        notifyDataSetChanged()

    }

    interface OnItemClickListener {
        fun onItemClick(pos: Int, item: String)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(surasList.get(position))
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(View.OnClickListener { view ->
                onItemClickListener?.onItemClick(position, surasList.get(position))
            })
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var name: TextView

        init {
            name = itemView.findViewById(R.id.name)
        }

    }

}