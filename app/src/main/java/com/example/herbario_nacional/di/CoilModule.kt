package ni.abril.azb.megaboicotapp.di

import android.app.Application
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import coil.ImageLoader
import com.example.herbario_nacional.R
import okhttp3.OkHttpClient
import org.koin.dsl.module

val coilModule = module {
    single {
        fun provideImageLoader(app: Application, okhttp: OkHttpClient): ImageLoader =
            ImageLoader(
                context = app,
                builder = {
                    crossfade(true)
                    placeholder(ColorDrawable(ContextCompat.getColor(app.applicationContext, R.color.colorPrimary)))
                    //TODO: Add an image to notify the user when an error occurs in the loading of the image.
                    //error(R.drawable.ic_close)
                    okHttpClient { okhttp }
                }
            )
    }
}