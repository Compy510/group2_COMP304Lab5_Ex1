package com.arshad.group2_comp304lab5_ex1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LandmarksAdapter(private val landmarks: List<String>) :
    RecyclerView.Adapter<LandmarksAdapter.ViewHolder>()
{

    private var onClick: ((String) -> Unit)? = null

    fun onLandmarkClickListener(listener: (String) -> Unit)
    {
        onClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_landmark, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LandmarksAdapter.ViewHolder, position: Int)
    {
        holder.landmarkName.text = landmarks[position]
    }

    inner class ViewHolder(landmarkItem: View) : RecyclerView.ViewHolder(landmarkItem)
    {
        val landmarkName: TextView = landmarkItem.findViewById(R.id.nameOfLandmark)

        init
        {
            landmarkItem.setOnClickListener {
                onClick?.invoke(landmarks[adapterPosition])
            }
        }
    }


    override fun getItemCount(): Int
    {
        return landmarks.size
    }

}
