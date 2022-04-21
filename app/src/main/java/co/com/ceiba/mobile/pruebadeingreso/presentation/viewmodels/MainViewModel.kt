package co.com.ceiba.mobile.pruebadeingreso.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.ceiba.mobile.pruebadeingreso.storage.AppStorage
import co.com.ceiba.mobile.pruebadeingreso.storage.models.Result
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val storage = AppStorage()

    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var users: MutableLiveData<List<User>> = MutableLiveData();

    fun getUsers() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = storage.getUsers()
            if (result is Result.Success<List<User>>) {
                users.postValue(result.data)
            } else if (result is Result.Error) {
                print("Error ${result.exception.message}")
            }
            isLoading.postValue(false)
        }
    }

    fun getUsersByName(name: String) {
        viewModelScope.launch {
            users.postValue(storage.getUsersByName(name))
        }
    }
}