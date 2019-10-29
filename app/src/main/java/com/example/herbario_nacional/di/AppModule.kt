package ni.abril.azb.megaboicotapp.di

import ni.abril.azb.megaboicotapp.data.network.remoteDataSourceModule
import ni.abril.azb.megaboicotapp.repo.BusinessRepository
import ni.abril.azb.megaboicotapp.repo.BusinessRepositoryImpl
import ni.abril.azb.megaboicotapp.repo.SessionTokenRepository
import ni.abril.azb.megaboicotapp.repo.SessionTokenRepositoryImpl
import ni.abril.azb.megaboicotapp.ui.BusinessViewModel
import ni.abril.azb.megaboicotapp.ui.SessionTokenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { SessionTokenViewModel(get()) }
    viewModel { BusinessViewModel(get()) }

    single<SessionTokenRepository> {
        SessionTokenRepositoryImpl(get())
    }

    single<BusinessRepository> {
        BusinessRepositoryImpl(get())
    }
}

val allAppModules = listOf(appModule, remoteDataSourceModule, coilModule, roomModule)