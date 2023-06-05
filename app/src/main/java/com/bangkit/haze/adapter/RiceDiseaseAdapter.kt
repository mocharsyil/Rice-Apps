import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.haze.R
import com.bangkit.haze.RiceDisease


class RiceDiseaseAdapter(private val riceDiseases: List<RiceDisease>) :
    RecyclerView.Adapter<RiceDiseaseAdapter.RiceDiseaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiceDiseaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rice_disease, parent, false)
        return RiceDiseaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: RiceDiseaseViewHolder, position: Int) {
        val riceDisease = riceDiseases[position]
        holder.bind(riceDisease)
    }

    override fun getItemCount(): Int {
        return riceDiseases.size
    }

    inner class RiceDiseaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewDiseaseName)
        private val infoTextView: TextView = itemView.findViewById(R.id.textViewDiseaseInfo)
        private val diseaseImageView: ImageView = itemView.findViewById(R.id.imageViewDisease)

        fun bind(riceDisease: RiceDisease) {
            nameTextView.text = riceDisease.name
            infoTextView.text = riceDisease.info
            diseaseImageView.setImageResource(riceDisease.imageResId)
        }
    }
}
