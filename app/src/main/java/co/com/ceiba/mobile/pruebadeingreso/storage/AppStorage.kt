package co.com.ceiba.mobile.pruebadeingreso.storage

import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.CloudStorage
import co.com.ceiba.mobile.pruebadeingreso.storage.local.LocalStorage
import co.com.ceiba.mobile.pruebadeingreso.storage.models.Post
import co.com.ceiba.mobile.pruebadeingreso.storage.models.Result
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AppStorage {
    private var cloudStorage: CloudStorage = CloudStorage()
    private var localStorage: LocalStorage = LocalStorage()

    suspend fun getUsers(): Result<List<User>> {
        return withContext(Dispatchers.IO) {
            var users: List<User> = localStorage.users
            if (users.isNotEmpty()) {
                return@withContext Result.Success(users)
            } else {
                val call = cloudStorage.users()
                if (call.isSuccessful) {
                    users = call.body()!!
                    localStorage.saveUsers(users)
                    return@withContext Result.Success(users)
                } else {
                    return@withContext Result.Error(Exception(call.message()))
                }
            }

        }
    }

    suspend fun getPostsByUserId(userId: Int): Result<List<Post>> {
        return withContext(Dispatchers.IO) {
            val call = cloudStorage.getPostsByUser(userId)
            if (call.isSuccessful) {
                return@withContext Result.Success(call.body()!!)
            } else {
                return@withContext Result.Error(Exception(call.message()))
            }
        }
    }

    suspend fun getUsersByName(name: String?): List<User> {
        return withContext(Dispatchers.IO) {
            return@withContext localStorage.getUsersByName(name!!)
        }
    }

    suspend fun getUserById(userId: Int): User {
        return withContext(Dispatchers.IO) {
            return@withContext localStorage.getUserById(userId)
        }
    }

}