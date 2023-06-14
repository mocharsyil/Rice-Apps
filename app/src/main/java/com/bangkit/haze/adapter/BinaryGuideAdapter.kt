package com.bangkit.haze.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.haze.BinaryGuide
import com.bangkit.haze.R

class BinaryGuideAdapter(
    private val binaryGuideList: List<BinaryGuide>,
    private val predictButtonClickListener: View.OnClickListener
) : RecyclerView.Adapter<BinaryGuideAdapter.BinaryGuideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinaryGuideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.binary_guide, parent, false)
        return BinaryGuideViewHolder(view)
    }

    override fun onBindViewHolder(holder: BinaryGuideViewHolder, position: Int) {
        val binaryGuide = binaryGuideList[position]
        holder.bind(binaryGuide)
    }

    override fun getItemCount(): Int {
        return binaryGuideList.size
    }

    inner class BinaryGuideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        private val titleTextView: TextView = itemView.findViewById(R.id.textViewBinaryName)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textViewBinaryInfo)
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewBinary)
        private val predictButton: Button = itemView.findViewById(R.id.buttonBinaryPred)

        fun bind(binaryGuide: BinaryGuide) {
            titleTextView.text = binaryGuide.name
            descriptionTextView.text = binaryGuide.info
            imageView.setImageResource(binaryGuide.imageResId)

        }
    }
}
