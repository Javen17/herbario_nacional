package ni.abril.azb.megaboicotapp.ui.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.herbario_nacional.BuildConfig
import com.example.herbario_nacional.R
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.models.Plant
import com.example.herbario_nacional.util.ImageSizer
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.plant_content.*

class PlantViewHolder constructor(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(plant: Plant, imageLoader: ImageLoader) {
        plantImage?.let { imageLoader.load("${BuildConfig.HERBARIO_URL}/gallery/${plant.image}", it) }
        plantName.text = plant.name
        plantImage.layoutParams.height = ImageSizer().getRandomIntInRange(250, 180)
    }

    companion object {
        fun create(parent: ViewGroup): PlantViewHolder {
            return PlantViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plant_card, parent, false))
        }
    }
}