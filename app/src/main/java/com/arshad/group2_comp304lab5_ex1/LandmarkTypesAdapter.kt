package com.arshad.group2_comp304lab5_ex1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LandmarkTypesAdapter(private val landmarkTypes: List<String>) :
    RecyclerView.Adapter<LandmarkTypesAdapter.ViewHolder>()
{

    private var onClick: ((String) -> Unit)? = null

    fun onLandmarkTypeClickListener(listener: (String) -> Unit)
    {
        onClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_landmark_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.landmarkType.text = landmarkTypes[position]
    }

    inner class ViewHolder(landmarkTypeItem: View) : RecyclerView.ViewHolder(landmarkTypeItem)
    {
        val landmarkType: TextView = landmarkTypeItem.findViewById(R.id.typeOfLandmark)

        init
        {
            landmarkTypeItem.setOnClickListener {
                onClick?.invoke(landmarkTypes[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int
    {
        return landmarkTypes.size
    }

}
