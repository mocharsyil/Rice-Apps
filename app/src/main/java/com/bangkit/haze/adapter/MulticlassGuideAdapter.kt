package com.bangkit.haze.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.haze.BinaryGuide
import com.bangkit.haze.DashboardActivity
import com.bangkit.haze.MulticlassGuide
import com.bangkit.haze.R
import com.google.android.material.button.MaterialButton

class MulticlassGuideAdapter(private val MulticlassGuide: List<MulticlassGuide>) :
    RecyclerView.Adapter<MulticlassGuideAdapter.MulticlassGuideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MulticlassGuideViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.multiclass_guide, parent, false)
        return MulticlassGuideViewHolder(view)
    }

    override fun onBindViewHolder(holder: MulticlassGuideViewHolder, position: Int) {
        val multiclassGuide = MulticlassGuide[position]
        holder.bind(multiclassGuide)
    }

    override fun getItemCount(): Int {
        return MulticlassGuide.size
    }

    inner class MulticlassGuideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewMulticlassName)
        private val infoTextView: TextView = itemView.findViewById(R.id.textViewMulticlassInfo)
        private val diseaseImageView: ImageView = itemView.findViewById(R.id.imageViewMulticlass)
        val buttonMulticlassPred: MaterialButton = itemView.findViewById(R.id.ButtonMulticlassPred)
        fun bind(MulticlassGuide: MulticlassGuide) {
            nameTextView.text = MulticlassGuide.name
            infoTextView.text = MulticlassGuide.info
            diseaseImageView.setImageResource(MulticlassGuide.imageResId)
            buttonMulticlassPred.setOnClickListener {
                // Call the function to open the camera
                (itemView.context as? DashboardActivity)?.checkCameraPermissionAndOpenCamera()
            }
        }
    }
}