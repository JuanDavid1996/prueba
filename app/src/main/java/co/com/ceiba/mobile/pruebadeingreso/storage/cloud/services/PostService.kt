package co.com.ceiba.mobile.pruebadeingreso.storage.cloud.services

import retrofit2.http.GET
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.Endpoints
import co.com.ceiba.mobile.pruebadeingreso.storage.models.Post
import retrofit2.Response
import retrofit2.http.Query

interface PostService {
    @GET(Endpoints.GET_POST_USER)
    suspend fun posts(): Response<List<Post>>

    @GET(Endpoints.GET_POST_USER)
    suspend fun getPostsByUser(@Query("userId") userId: Int): Response<List<Post>>
}