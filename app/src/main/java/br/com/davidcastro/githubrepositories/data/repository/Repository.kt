package br.com.davidcastro.githubrepositories.data.repository

import br.com.davidcastro.githubrepositories.domain.model.GitHubRepositoriesListModel
import retrofit2.Response

interface Repository {
    suspend fun getRepositories(page: Int): Response<GitHubRepositoriesListModel>
}