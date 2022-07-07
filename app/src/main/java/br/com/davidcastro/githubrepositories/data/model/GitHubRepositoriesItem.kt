package br.com.davidcastro.githubrepositories.data.model

import com.google.gson.annotations.SerializedName

data class GitHubRepositoriesItem(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val gitHubRepositoriesOwner: GitHubRepositoriesOwner,
    @SerializedName("description") val description: String,
    @SerializedName("stargazers_count") val starsCount: Int,
    @SerializedName("forks") val forksCount: Int,
    @SerializedName("svn_url") val repositoryUrl: String,
    @SerializedName("updated_at") val lastUpdate: String,
    @SerializedName("topics") val topics: List<String>
)