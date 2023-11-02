package com.example.capsule.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capsule.databinding.ChildItemBinding
import com.example.capsule.datamodel.ChildItem

class ChildAdapter(val context: Context, val chilList:ArrayList<ChildItem>) : RecyclerView.Adapter<ChildAdapter.ViewHolder>() {

    class ViewHolder(val binding: ChildItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ChildItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
        return chilList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chilItem=chilList[position]
        holder.binding.childTitleTv.text=chilItem.note

    }
}