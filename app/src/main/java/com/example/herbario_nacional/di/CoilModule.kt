package ni.abril.azb.megaboicotapp.di

import coil.ImageLoader
import com.example.herbario_nacional.imageloader.CoilImageLoader
import org.koin.dsl.module

val coilModule = module {
    factory {
        CoilImageLoader() as ImageLoader
    }
}