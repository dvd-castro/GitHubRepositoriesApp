package br.com.davidcastro.githubrepositories.data.model

import com.google.gson.annotations.SerializedName

data class GitHubRepositoriesList(
    @SerializedName("items") val items: List<GitHubRepositoriesItem>
)