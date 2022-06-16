package br.com.davidcastro.githubrepositories.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.davidcastro.githubrepositories.data.model.GitHubRepositoriesItemModel
import br.com.davidcastro.githubrepositories.databinding.RepositoryItemViewBinding

class RepositoriesAdapter: ListAdapter<GitHubRepositoriesItemModel, RepositoriesAdapter.RepositoriesViewHolder>(DiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder =
        RepositoriesViewHolder(RepositoriesViewHolder.inflateViewBinding(parent))

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) =
        holder.bind(getItem(position))

    class RepositoriesViewHolder(private val binding: RepositoryItemViewBinding): RecyclerView.ViewHolder(binding.root) {

        companion object {
            internal fun inflateViewBinding(parent: ViewGroup): RepositoryItemViewBinding {
                return RepositoryItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            }
        }

        fun bind(itemModel: GitHubRepositoriesItemModel) {
            binding.tvName.text = itemModel.name
            binding.tvDescription.text = itemModel.description
        }
    }

    object DiffUtil: androidx.recyclerview.widget.DiffUtil.ItemCallback<GitHubRepositoriesItemModel>() {
        override fun areItemsTheSame(
            oldItem: GitHubRepositoriesItemModel,
            newItem: GitHubRepositoriesItemModel
        ): Boolean =
            oldItem.repositoryUrl == newItem.repositoryUrl


        override fun areContentsTheSame(
            oldItem: GitHubRepositoriesItemModel,
            newItem: GitHubRepositoriesItemModel
        ): Boolean =
           oldItem == newItem
    }
}