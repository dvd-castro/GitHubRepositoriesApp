package br.com.davidcastro.githubrepositories.data.model

import com.google.gson.annotations.SerializedName

data class GitHubRepositoriesListModel(
    @SerializedName("items") val items: List<GitHubRepositoriesItemModel>
)