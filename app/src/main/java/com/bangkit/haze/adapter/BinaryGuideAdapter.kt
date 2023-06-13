package com.bangkit.haze.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.haze.BinaryGuide
import com.bangkit.haze.DashboardActivity
import com.bangkit.haze.R
import com.google.android.material.button.MaterialButton


class BinaryGuideAdapter(private val BinaryGuide: List<BinaryGuide>) :
    RecyclerView.Adapter<BinaryGuideAdapter.BinaryGuideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinaryGuideViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.binary_guide, parent, false)
        return BinaryGuideViewHolder(view)
    }

    override fun onBindViewHolder(holder: BinaryGuideViewHolder, position: Int) {

        val binaryGuide = BinaryGuide[position]
        holder.bind(binaryGuide)


    }

    override fun getItemCount(): Int {
        return BinaryGuide.size
    }

    inner class BinaryGuideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewBinaryName)
        val infoTextView: TextView = itemView.findViewById(R.id.textViewBinaryInfo)
        val diseaseImageView: ImageView = itemView.findViewById(R.id.imageViewBinary)
        val buttonBinaryPred: MaterialButton = itemView.findViewById(R.id.ButtonBinaryPred)
        fun bind(BinaryGuide: BinaryGuide) {
            nameTextView.text = BinaryGuide.name
            infoTextView.text = BinaryGuide.info
            diseaseImageView.setImageResource(BinaryGuide.imageResId)
            buttonBinaryPred.setOnClickListener {
                // Call the function to open the camera
                (itemView.context as? DashboardActivity)?.checkCameraPermissionAndOpenCamera()
            }
        }
    }
}
