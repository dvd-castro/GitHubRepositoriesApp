package br.com.davidcastro.githubrepositories.data.api

import br.com.davidcastro.githubrepositories.domain.model.GitHubRepositoriesListModel
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("")
    suspend fun getRepositories(
        @Query("page") page: Int
    ): Response<GitHubRepositoriesListModel>
}