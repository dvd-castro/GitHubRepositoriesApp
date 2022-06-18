package br.com.davidcastro.githubrepositories.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.davidcastro.githubrepositories.R
import br.com.davidcastro.githubrepositories.data.model.Items
import br.com.davidcastro.githubrepositories.data.model.ShowMoreCallBack
import br.com.davidcastro.githubrepositories.databinding.RepositoryItemViewBinding
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class RepositoriesAdapter(private val showMoreCallBack: ShowMoreCallBack): ListAdapter<Items, RepositoriesAdapter.RepositoriesViewHolder>(DiffUtil) {

    fun setList(items: List<Items>) {
        val list = currentList.toMutableList()
        list.addAll(items)
        this.submitList(list)
    }

    fun getLastItemPosition(): Int = itemCount - 1

    private fun isLastItem(position: Int): Boolean = position == itemCount - 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder =
        RepositoriesViewHolder(RepositoriesViewHolder.inflateViewBinding(parent), showMoreCallBack)

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        holder.bind(getItem(position), isLastItem(position))
    }

    class RepositoriesViewHolder(
        private val binding: RepositoryItemViewBinding,
        private val showMoreCallBack: ShowMoreCallBack
    ): RecyclerView.ViewHolder(binding.root) {

        companion object {
            internal fun inflateViewBinding(parent: ViewGroup): RepositoryItemViewBinding {
                return RepositoryItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            }
        }

        fun bind(itemModel: Items, lastItem: Boolean) {
            binding.tvName.text = itemModel.name
            binding.tvDescription.text = itemModel.description
            binding.tvForks.text = itemModel.forksCount.toString()
            binding.tvStars.text = itemModel.starsCount.toString()
            binding.tvLastCommit.text = fillDateWithStringFormat(itemModel.lastUpdate)

            fillImageWithPicasso(itemModel.owner.avatarUrl)
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

        private fun fillImageWithPicasso(url: String) {
            Picasso.get().load(url).placeholder(R.drawable.ic_image).into(binding.ivIcon)
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