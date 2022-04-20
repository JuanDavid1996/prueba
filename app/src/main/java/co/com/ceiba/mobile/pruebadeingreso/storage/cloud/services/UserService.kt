package co.com.ceiba.mobile.pruebadeingreso.storage.cloud.services

import retrofit2.http.GET
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.Endpoints
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User
import retrofit2.Response

interface UserService {
    @GET(Endpoints.GET_USERS)
    suspend fun users(): Response<List<User>>
}