package br.com.davidcastro.githubrepositories.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.davidcastro.githubrepositories.data.model.GitHubRepositoriesList
import br.com.davidcastro.githubrepositories.data.model.GitHubRepositoriesItem
import br.com.davidcastro.githubrepositories.data.repository.Repository
import br.com.davidcastro.githubrepositories.view.model.LastItemBehaviorEnum
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val api: Repository): ViewModel() {

    private val GITHUB_REPOSITORIES_IN_CACHE = "github_repositories_in_cache"
    private val CURRENT_PAGE = "current_page"

    private var _repositories = MutableLiveData<MutableList<GitHubRepositoriesItem>>()
    val repositories = _repositories

    private var _errorOnShowMore = MutableLiveData(LastItemBehaviorEnum.DEFAULT)
    val errorOnShowMore = _errorOnShowMore

    private var page = getCurrentPageInCache()

    fun getRepositories() {
        viewModelScope.launch {
            if(!containsListInCache()) {
                try {
                    if(repositories.value.isNullOrEmpty()) {
                        val result = api.getRepositories(page)
                        processResponse(result)

                        Log.d("${result.code()}: RESULT -> ", "${result.body()}")
                    }
                } catch (ex: Exception) {
                    Log.d("ERROR -> ", "${ex.message}")
                }
            } else {
                if(isFirstRequest()) {
                    postValueRepositories(getListInCache())
                }
            }
        }
    }

    fun showMore() {
        viewModelScope.launch {
            try {
                page += 1
                val result = api.getRepositories(page)
                processResponse(result)
                Log.d("${result.code()}: RESULT -> ", "$result")

            } catch (ex: Exception) {
                Log.d("ERROR -> ", "${ex.message}")
            }
        }
    }

    private fun processResponse(result: Response<GitHubRepositoriesList>) {
        when(result.code()) {
            200 -> { result.body()?.items?.toMutableList()
                ?.let {
                    postValueRepositories(it)
                    addListInCache(it)
                }
            }
            422 -> _errorOnShowMore.postValue(LastItemBehaviorEnum.SHOW_END)
            in 500 .. 599 -> _errorOnShowMore.postValue(LastItemBehaviorEnum.SHOW_ERROR)
        }
    }

    private fun postValueRepositories(list: MutableList<GitHubRepositoriesItem>) {
        if(!isFirstRequest()) {
            val repositories = _repositories.value
            repositories?.let {
                it.addAll(list)
                _repositories.postValue(it)
            }
        } else {
            _repositories.postValue(list)
        }
    }

    private fun addListInCache(obj: MutableList<GitHubRepositoriesItem>) {
        if(containsListInCache()) {
            val list = getListInCache()
            list.addAll(obj)
            Hawk.put(GITHUB_REPOSITORIES_IN_CACHE, list)
        } else {
            Hawk.put(GITHUB_REPOSITORIES_IN_CACHE, obj)
        }

        addCurrentPageInCache()
    }

    private fun isFirstRequest(): Boolean = _repositories.value.isNullOrEmpty()

    private fun getListInCache(): MutableList<GitHubRepositoriesItem> =
        Hawk.get(GITHUB_REPOSITORIES_IN_CACHE) ?: mutableListOf()

    private fun containsListInCache() =
        Hawk.contains(GITHUB_REPOSITORIES_IN_CACHE)

    private fun addCurrentPageInCache() =
        Hawk.put(CURRENT_PAGE, page)

    private fun getCurrentPageInCache() =
        if(Hawk.contains(CURRENT_PAGE)) Hawk.get(CURRENT_PAGE) else 1
}