package br.com.davidcastro.githubrepositories.data.repository

import br.com.davidcastro.githubrepositories.data.api.Api
import br.com.davidcastro.githubrepositories.data.model.GitHubRepositoriesList
import retrofit2.Response

class RepositoryImpl(private val api: Api): Repository {

    override suspend fun getRepositories(page: Int): Response<GitHubRepositoriesList> {
        return api.getRepositories(page)
    }
}