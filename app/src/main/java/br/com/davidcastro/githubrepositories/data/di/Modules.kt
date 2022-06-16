package br.com.davidcastro.githubrepositories.data.di

import br.com.davidcastro.githubrepositories.data.api.Api
import br.com.davidcastro.githubrepositories.data.api.RetrofitClient
import br.com.davidcastro.githubrepositories.data.repository.Repository
import br.com.davidcastro.githubrepositories.data.repository.RepositoryImpl
import br.com.davidcastro.githubrepositories.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single { RetrofitClient.getRetrofitInstance(Api::class.java, "https://api.github.com/search/") }

    single<Repository> { RepositoryImpl(get()) }

    viewModel {
        MainViewModel(get())
    }
}