package com.example.herbario_nacional.di

import com.example.herbario_nacional.data.network.remoteDataSourceModule
import com.example.herbario_nacional.repo.CredentialsRepository
import com.example.herbario_nacional.repo.CredentialsRepositoryImpl
import com.example.herbario_nacional.ui.viewModels.CredentialsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CredentialsViewModel(get()) }

    single<CredentialsRepository> {
        CredentialsRepositoryImpl(get())
    }
}

val allAppModules = listOf(appModule, remoteDataSourceModule, coilModule)