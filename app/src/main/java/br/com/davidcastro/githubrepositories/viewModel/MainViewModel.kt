package br.com.davidcastro.githubrepositories.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.githubrepositories.data.model.GitHubRepositoriesListModel
import br.com.davidcastro.githubrepositories.data.model.Items
import br.com.davidcastro.githubrepositories.data.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val api: Repository): ViewModel() {

    private var _repositories = MutableLiveData<MutableList<Items>>()
    val repositories = _repositories

    private var _page = 1
    val page = _page

    fun getRepositories() {
        viewModelScope.launch {
            try {
                val result = api.getRepositories(_page)
                processResponse(result)
                _page += 1

                Log.d("${result.code()}: RESULT -> ", "${result.body()}")
            } catch (ex: Exception) {
                Log.d("ERROR -> ", "${ex.message}")
            }
        }
    }

    private fun processResponse(result: Response<GitHubRepositoriesListModel>) {
        when(result.code()) {
            200 -> { result.body()?.items?.toMutableList()?.let { _repositories.postValue(it) } }
            422 -> {}
            else -> {}
        }
    }
}