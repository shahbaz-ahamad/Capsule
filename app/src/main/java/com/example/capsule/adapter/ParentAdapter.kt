package com.example.capsule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capsule.databinding.ParentItemBinding
import com.example.capsule.datamodel.ParentItem


class ParentAdapter (val context: Context, val parentList:ArrayList<ParentItem>): RecyclerView.Adapter<ParentAdapter.ViewHolder>() {


    class ViewHolder(val binding: ParentItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ParentItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
        return parentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parentItem=parentList[position]


        holder.binding.parentTitleTv.text=parentItem.title
        holder.binding.noticTopicRecyclerView.setHasFixedSize(true)
        holder.binding.noticTopicRecyclerView.layoutManager= LinearLayoutManager(context)

        //adpater
        val childAdapter=ChildAdapter(context,parentItem.childItemList)
        holder.binding.noticTopicRecyclerView.adapter=childAdapter

        //expandable functionality
        val isExpandable=parentItem.isExpandable
        holder.binding.noticTopicRecyclerView.visibility= if(isExpandable){
            View.VISIBLE
        }else{
            View.GONE
        }

        holder.binding.constraintLayout.setOnClickListener {

            isAnyItemExpanded(position)
            parentItem.isExpandable=!parentItem.isExpandable
            notifyItemChanged(position)
        }
    }



    private fun isAnyItemExpanded(position: Int) {

        val temp=parentList.indexOfFirst {
            it.isExpandable
        }

        if(temp>=0 && temp!=position){
            parentList[temp].isExpandable=false
            notifyItemChanged(temp)
        }
    }
}
