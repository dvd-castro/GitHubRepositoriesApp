package br.com.davidcastro.githubrepositories.domain.model

import com.google.gson.annotations.SerializedName

data class GitHubRepositoriesItemModel(
    @SerializedName("name") val name: String,
    @SerializedName("avatar_url") val urlIcon: String,
    @SerializedName("description") val description: String,
    @SerializedName("stargazers_count") val starsCount: Int,
    @SerializedName("forks") val forksCount: Int,
    @SerializedName("svn_url") val repositoryUrl: String,
    @SerializedName("updated_at") val lastUpdate: String,
    @SerializedName("topics") val topics: String
)