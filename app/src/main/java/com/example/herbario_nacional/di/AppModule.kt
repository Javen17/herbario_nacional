package com.example.herbario_nacional.di

import com.example.herbario_nacional.data.network.remoteDataSourceModule
import com.example.herbario_nacional.repo.*
import com.example.herbario_nacional.ui.viewModels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CredentialsViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { CountryViewModel(get()) }
    viewModel { FamilyViewModel(get()) }
    viewModel { GenusViewModel(get()) }
    viewModel { SpecieViewModel(get()) }
    viewModel { StatusViewModel(get()) }
    viewModel { HabitatViewModel(get()) }
    viewModel { HabitatDescriptionViewModel(get()) }
    viewModel { BiostatusViewModel(get()) }
    viewModel { PlantViewModel(get()) }

    single<CredentialsRepository> {
        CredentialsRepositoryImpl(get())
    }

    single<RegisterRepository> {
        RegisterRepositoryImpl(get())
    }

    single<CountryRepository> {
        CountryRepositoryImpl(get())
    }

    single<FamilyRepository> {
        FamilyRepositoryImpl(get())
    }

    single<GenusRepository> {
        GenusRepositoryImpl(get())
    }

    single<SpecieRepository> {
        SpecieRepositoryImpl(get())
    }

    single<StatusRepository> {
        StatusRepositoryImpl(get())
    }

    single<HabitatRepository> {
        HabitatRepositoryImpl(get())
    }

    single<HabitatDescriptionRepository> {
        HabitatDescriptionRepositoryImpl(get())
    }

    single<BiostatusRepository> {
        BiostatusRepositoryImpl(get())
    }

    single<PlantRepository> {
        PlantRepositoryImpl(get())
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl(get())
    }
}

val allAppModules = listOf(appModule, remoteDataSourceModule, coilModule)