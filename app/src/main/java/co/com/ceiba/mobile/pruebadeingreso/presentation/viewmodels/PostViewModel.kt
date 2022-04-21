package co.com.ceiba.mobile.pruebadeingreso.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.ceiba.mobile.pruebadeingreso.storage.AppStorage
import co.com.ceiba.mobile.pruebadeingreso.storage.models.Post
import co.com.ceiba.mobile.pruebadeingreso.storage.models.Result
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    val storage = AppStorage()
    val user: MutableLiveData<User?> = MutableLiveData(null)
    val posts: MutableLiveData<List<Post>> = MutableLiveData()

    fun getUser(id: Int) {
        viewModelScope.launch {
            user.postValue(storage.getUserById(id))
        }
    }

    fun getPostByUserId(userId: Int) {
        viewModelScope.launch {
            val result = storage.getPostsByUserId(userId)
            if (result is Result.Success) {
                posts.postValue(result.data)
            }
        }
    }
}