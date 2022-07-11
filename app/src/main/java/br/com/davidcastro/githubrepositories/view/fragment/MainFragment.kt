package br.com.davidcastro.githubrepositories.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.davidcastro.githubrepositories.R
import br.com.davidcastro.githubrepositories.data.model.GitHubRepositoriesItem
import br.com.davidcastro.githubrepositories.view.model.ShowMoreCallBack
import br.com.davidcastro.githubrepositories.databinding.FragmentMainBinding
import br.com.davidcastro.githubrepositories.view.adapter.RepositoriesAdapter
import br.com.davidcastro.githubrepositories.view.model.LastItemBehaviorEnum
import br.com.davidcastro.githubrepositories.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), ShowMoreCallBack {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val repositoriesAdapter = RepositoriesAdapter(this)

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        setObservables()
        initUi()
    }

    private fun initUi() {
        setRecyclerView()
    }

    private fun getData() {
        showLoader()
        viewModel.getRepositories()
    }

    private fun setObservables() {
        viewModel.errorOnShowMore.observe(viewLifecycleOwner,::setLastItemBehavior)
        viewModel.repositories.observe(viewLifecycleOwner,::setRepositoriesList)
    }

    private fun setRepositoriesList(list: MutableList<GitHubRepositoriesItem>) {
        setLastItemBehavior(LastItemBehaviorEnum.HIDE_LOADER)
        cancelLoader()
        repositoriesAdapter.setList(list)
    }

    private fun setRecyclerView() {
        with(binding.rvRepositories) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repositoriesAdapter
            setHasFixedSize(false)
        }
    }

    private fun setLastItemBehavior(behavior: LastItemBehaviorEnum) {
        val lastItemVisible = binding.rvRepositories.layoutManager
            ?.findViewByPosition(repositoriesAdapter.getLastItemPosition())

        val loaderLayout = lastItemVisible?.findViewById<ProgressBar>(R.id.loader)
        val errorLayout = lastItemVisible?.findViewById<LinearLayout>(R.id.ln_error)
        val textEndList = lastItemVisible?.findViewById<TextView>(R.id.tv_end_list)

        when(behavior) {
            LastItemBehaviorEnum.HIDE_LOADER -> loaderLayout?.visibility = View.GONE
            LastItemBehaviorEnum.HIDE_ERROR -> errorLayout?.visibility = View.GONE

            LastItemBehaviorEnum.SHOW_END -> {
                loaderLayout?.visibility = View.GONE
                textEndList?.visibility = View.VISIBLE
            }

            LastItemBehaviorEnum.SHOW_LOADER -> {
                loaderLayout?.visibility = View.VISIBLE
                errorLayout?.visibility = View.GONE
            }

            LastItemBehaviorEnum.SHOW_ERROR -> {
                loaderLayout?.visibility = View.GONE

                errorLayout?.let {
                    it.visibility = View.VISIBLE

                    it.setOnClickListener {
                        showMore()
                        loaderLayout?.visibility = View.VISIBLE
                        errorLayout.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun showMore() {
        viewModel.showMore()
    }

    private fun showLoader() {
        binding.rvRepositories.visibility = View.GONE
        binding.loaderShimmer.visibility = View.VISIBLE
        binding.loaderShimmer.startShimmer()
    }

    private fun cancelLoader() {
        if(binding.loaderShimmer.isShimmerVisible) {
            binding.rvRepositories.visibility = View.VISIBLE
            binding.loaderShimmer.visibility = View.GONE
            binding.loaderShimmer.hideShimmer()
        }
    }
}