package com.example.herbario_nacional.di

import com.example.herbario_nacional.data.network.remoteDataSourceModule
import com.example.herbario_nacional.repo.CredentialsRepository
import com.example.herbario_nacional.repo.CredentialsRepositoryImpl
import com.example.herbario_nacional.repo.RegisterRepository
import com.example.herbario_nacional.repo.RegisterRepositoryImpl
import com.example.herbario_nacional.ui.viewModels.CredentialsViewModel
import com.example.herbario_nacional.ui.viewModels.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CredentialsViewModel(get()) }
    viewModel { RegisterViewModel(get()) }

    single<CredentialsRepository> {
        CredentialsRepositoryImpl(get())
    }

    single<RegisterRepository> {
        RegisterRepositoryImpl(get())
    }
}

val allAppModules = listOf(appModule, remoteDataSourceModule, coilModule)