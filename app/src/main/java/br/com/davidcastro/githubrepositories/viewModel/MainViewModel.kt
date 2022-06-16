package br.com.davidcastro.githubrepositories.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.githubrepositories.data.model.GitHubRepositoriesItemModel
import br.com.davidcastro.githubrepositories.data.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val api: Repository): ViewModel() {
    var page = 1

    private var _repositories = MutableLiveData<MutableList<GitHubRepositoriesItemModel>>()
    var repositories = _repositories

    fun getRepositories() {
        viewModelScope.launch {
            try {
                val result = api.getRepositories(page)
                _repositories.postValue(result.body()?.items?.toMutableList())
            } catch (ex: Exception) {
                Log.d("ERROR -> ", "${ex.message}")
            }
        }
    }
}