package br.com.davidcastro.githubrepositories.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.davidcastro.githubrepositories.data.model.Items
import br.com.davidcastro.githubrepositories.databinding.FragmentMainBinding
import br.com.davidcastro.githubrepositories.view.adapter.RepositoriesAdapter
import br.com.davidcastro.githubrepositories.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val repositoriesAdapter = RepositoriesAdapter()

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
        viewModel.getRepositories()
    }

    private fun setObservables() {
        viewModel.repositories.observe(viewLifecycleOwner,::setRepositoriesList)
    }

    private fun setRepositoriesList(list: List<Items>){
        repositoriesAdapter.submitList(list)
    }

    private fun setRecyclerView() {
        with(binding.rvRepositories) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repositoriesAdapter
            setHasFixedSize(false)
        }
    }
}