package com.assignment.kotlinmvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.assignment.kotlinmvvm.R
import com.assignment.kotlinmvvm.model.Assignment
import kotlinx.android.synthetic.main.row_assign.view.*

class AssignmentAdapter(private var assignments:List<Assignment>):RecyclerView.Adapter<AssignmentAdapter.MViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_assign, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        //render
        vh.bind(assignments[position])
    }

    override fun getItemCount(): Int {
        return assignments.size
    }

    fun update(data:List<Assignment>){
        assignments= data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val textViewName:TextView = view.textViewName
        private val textViewDescription:TextView = view.textViewDescription
        private val imageView:ImageView = view.imageView
        fun bind(assignment:Assignment){
            textViewName.text = assignment.title
            textViewDescription.text=assignment.description
            Glide.with(imageView.context).load(assignment.imageHref).into(imageView)
        }
    }
}