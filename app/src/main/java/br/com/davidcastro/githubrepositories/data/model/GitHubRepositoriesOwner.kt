package br.com.davidcastro.githubrepositories.data.model

import com.google.gson.annotations.SerializedName

data class GitHubRepositoriesOwner(
    @SerializedName("avatar_url") val avatarUrl: String,
)
