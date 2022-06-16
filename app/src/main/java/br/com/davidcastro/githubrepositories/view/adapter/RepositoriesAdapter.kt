package br.com.davidcastro.githubrepositories.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.davidcastro.githubrepositories.data.model.Items
import br.com.davidcastro.githubrepositories.databinding.RepositoryItemViewBinding
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class RepositoriesAdapter: ListAdapter<Items, RepositoriesAdapter.RepositoriesViewHolder>(DiffUtil) {

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

        fun bind(itemModel: Items) {
            binding.tvName.text = itemModel.name
            binding.tvDescription.text = itemModel.description
            binding.tvForks.text = itemModel.forksCount.toString()
            binding.tvStars.text = itemModel.starsCount.toString()
            binding.tvLastCommit.text = fillDateWithStringFormat(itemModel.lastUpdate)
            fillImageWithPicasso(itemModel.owner.avatarUrl)
            fillChipGroupTopics(itemModel.topics)
        }

        private fun fillDateWithStringFormat(dateString: String): String {
            val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
            val date = DateTime.parse(dateString, formatter)
            return date.toString("dd/MM/yyyy")
        }

        private fun fillChipGroupTopics(topics: List<String>) {
            topics.forEach { item ->
                val chip = Chip(binding.root.context)
                chip.text = item
                binding.cgTopics.addView(chip)
            }
        }

        private fun fillImageWithPicasso(url: String) {
            Picasso.get().load(url).into(binding.ivIcon)
        }
    }

    object DiffUtil: androidx.recyclerview.widget.DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(
            oldItem: Items,
            newItem: Items
        ): Boolean =
            oldItem.repositoryUrl == newItem.repositoryUrl


        override fun areContentsTheSame(
            oldItem: Items,
            newItem: Items
        ): Boolean =
           oldItem == newItem
    }
}