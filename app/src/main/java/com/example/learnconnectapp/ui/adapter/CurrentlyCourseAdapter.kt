package com.example.learnconnectapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learnconnectapp.R
import com.example.learnconnectapp.data.entity.CurrentlyCourseList
import com.example.learnconnectapp.databinding.CurrentlyCourseBinding

class CurrentlyCourseAdapter(
    private var courses: List<CurrentlyCourseList>,
    private val context: Context
) : RecyclerView.Adapter<CurrentlyCourseAdapter.CourseViewHolder>() {

    inner class CourseViewHolder(val binding: CurrentlyCourseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding: CurrentlyCourseBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.currently_course, parent, false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.binding.currentlyCourse = course
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun updateCourses(newCourses: List<CurrentlyCourseList>) {
        courses = newCourses
        notifyDataSetChanged()
    }
}
