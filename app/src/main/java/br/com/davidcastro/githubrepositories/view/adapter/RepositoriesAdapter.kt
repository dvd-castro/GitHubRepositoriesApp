package br.com.davidcastro.githubrepositories.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.davidcastro.githubrepositories.R
import br.com.davidcastro.githubrepositories.data.model.GitHubRepositoriesItem
import br.com.davidcastro.githubrepositories.data.model.ShowMoreCallBack
import br.com.davidcastro.githubrepositories.databinding.RepositoryItemViewBinding
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class RepositoriesAdapter(private val showMoreCallBack: ShowMoreCallBack): ListAdapter<GitHubRepositoriesItem, RepositoriesAdapter.RepositoriesViewHolder>(DiffUtil) {

    fun setList(items: MutableList<GitHubRepositoriesItem>) =
        this.submitList(items.toList())

    fun getLastItemPosition(): Int = itemCount - 1

    private fun isLastItem(position: Int): Boolean = position == getLastItemPosition()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder =
        RepositoriesViewHolder(RepositoriesViewHolder.inflateViewBinding(parent), showMoreCallBack)

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) =
        holder.bind(getItem(position), isLastItem(position))

    class RepositoriesViewHolder(
        private val binding: RepositoryItemViewBinding,
        private val showMoreCallBack: ShowMoreCallBack
    ): RecyclerView.ViewHolder(binding.root) {

        companion object {
            internal fun inflateViewBinding(parent: ViewGroup): RepositoryItemViewBinding =
                RepositoryItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        }

        fun bind(itemModel: GitHubRepositoriesItem, lastItem: Boolean) {
            binding.tvName.text = itemModel.name
            binding.tvDescription.text = itemModel.description
            binding.tvForks.text = itemModel.forksCount.toString()
            binding.tvStars.text = itemModel.starsCount.toString()
            binding.tvLastCommit.text = fillDateWithStringFormat(itemModel.lastUpdate)

            fillImageWithPicasso(itemModel.gitHubRepositoriesOwner.avatarUrl)
            fillChipGroupTopics(itemModel.topics)
            showMore(lastItem)
        }

        private fun showMore(lastItem: Boolean) {
            if(lastItem) {
                binding.loader.visibility = View.VISIBLE
                showMoreCallBack.showMore()
            } else{
                binding.loader.visibility = View.GONE
            }
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

        private fun fillImageWithPicasso(url: String) =
            Picasso.get().load(url).placeholder(R.drawable.ic_image).into(binding.ivIcon)
    }

    object DiffUtil: androidx.recyclerview.widget.DiffUtil.ItemCallback<GitHubRepositoriesItem>() {
        override fun areItemsTheSame(
            oldItem: GitHubRepositoriesItem,
            newItem: GitHubRepositoriesItem
        ): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: GitHubRepositoriesItem,
            newItem: GitHubRepositoriesItem
        ): Boolean =
           oldItem == newItem
    }
}