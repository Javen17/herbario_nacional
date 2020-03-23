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
    viewModel { MeViewModel(get()) }
    viewModel { CountryViewModel(get()) }
    viewModel { CityViewModel(get()) }
    viewModel { StateViewModel(get()) }
    viewModel { FamilyViewModel(get()) }
    viewModel { GenusViewModel(get()) }
    viewModel { SpecieViewModel(get()) }
    viewModel { StatusViewModel(get()) }
    viewModel { HabitatViewModel(get()) }
    viewModel { HabitatDescriptionViewModel(get()) }
    viewModel { BiostatusViewModel(get()) }
    viewModel { PlantViewModel(get()) }
    viewModel { FungusViewModel(get()) }
    viewModel { FormTypeViewModel(get()) }
    viewModel { CapTypeViewModel(get()) }
    viewModel { NewPlantViewModel(get()) }
    viewModel { NewFungusViewModel(get()) }

    single<CredentialsRepository> {
        CredentialsRepositoryImpl(get())
    }

    single<RegisterRepository> {
        RegisterRepositoryImpl(get())
    }

    single<CountryRepository> {
        CountryRepositoryImpl(get())
    }

    single<StateRepository> {
        StateRepositoryImpl(get())
    }

    single<CityRepository> {
        CityRepositoryImpl(get())
    }

    single<FamilyRepository> {
        FamilyRepositoryImpl(get())
    }

    single<GenusRepository> {
        GenusRepositoryImpl(get())
    }

    single<FormTypeRepository> {
        FormTypeRepositoryImpl(get())
    }

    single<CapTypeRepository> {
        CapTypeRepositoryImpl(get())
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

    single<FungusRepository> {
        FunghusRepositoryImpl(get())
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl(get())
    }

    single<MeRepository> {
        MeRepositoryImpl(get())
    }
}

val allAppModules = listOf(appModule, remoteDataSourceModule, coilModule)