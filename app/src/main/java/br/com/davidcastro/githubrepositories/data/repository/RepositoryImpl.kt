package br.com.davidcastro.githubrepositories.data.repository

import br.com.davidcastro.githubrepositories.data.api.Api
import br.com.davidcastro.githubrepositories.data.model.GitHubRepositoriesListModel
import retrofit2.Response

class RepositoryImpl(private val api: Api): Repository {

    override suspend fun getRepositories(page: Int): Response<GitHubRepositoriesListModel> {
        return api.getRepositories(page)
    }
}