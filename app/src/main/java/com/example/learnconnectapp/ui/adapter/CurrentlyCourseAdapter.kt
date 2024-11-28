package com.example.learnconnectapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.learnconnectapp.R
import com.example.learnconnectapp.data.entity.CurrentlyCourseList
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.databinding.CurrentlyCourseBinding
import com.example.learnconnectapp.ui.fragment.ProfileFragmentDirections
import com.example.learnconnectapp.ui.viewmodel.ProfileViewModel
import com.example.learnconnectapp.util.gecisYap

class CurrentlyCourseAdapter(
    private var courses: List<CurrentlyCourseList>,
    private val context: Context,
    private var viewModel: ProfileViewModel
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
        val t = holder.binding

        t.currentlyCourse = course

        holder.binding.textViewCurrentlyAdi.text = course.currently_kurs_isim

        holder.binding.imageViewCurrently.setImageResource(course.imageLinks)


        t.cardViewDown.setOnClickListener {

            val kurslar = Kurslar(
                kurs_isim = course.currently_kurs_isim,
                kurs_gorsel = course.imageLinks,
                kurs_id = course.currently_kurs_id)



            val gecis = ProfileFragmentDirections.actionProfileFragment2ToCourseDetailFragment(
                kurs = kurslar
            )

            if (gecis != null) {
                Navigation.gecisYap(it, gecis)
            }
        }

    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun updateCourses(newCourses: List<CurrentlyCourseList>) {
        courses = newCourses
        notifyDataSetChanged()
    }
}
