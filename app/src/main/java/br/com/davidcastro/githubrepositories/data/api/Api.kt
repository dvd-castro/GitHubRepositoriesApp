package br.com.davidcastro.githubrepositories.data.api

import br.com.davidcastro.githubrepositories.data.model.GitHubRepositoriesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("search/repositories?q=language:kotlin&sort=stars")
    suspend fun getRepositories(
        @Query("page") page: Int
    ): Response<GitHubRepositoriesList>
}