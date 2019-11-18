package com.example.herbario_nacional.di

import com.example.herbario_nacional.imageloader.CoilImageLoader
import com.example.herbario_nacional.imageloader.ImageLoader
import org.koin.dsl.module

val coilModule = module {
    factory {
        CoilImageLoader() as ImageLoader
    }
}