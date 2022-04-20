package co.com.ceiba.mobile.pruebadeingreso.storage.cloud

import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.services.UserService
import co.com.ceiba.mobile.pruebadeingreso.storage.models.Post
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.services.PostService
import retrofit2.Retrofit
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CloudStorage() {

    suspend fun users(): Response<List<User>> = createRetrofit().create(UserService::class.java).users()

    suspend fun posts(): Response<List<Post>> = createRetrofit().create(PostService::class.java).posts()

    suspend fun getPostsByUser(userId: Int): Response<List<Post>> =
        createRetrofit().create(PostService::class.java).getPostsByUser(userId)

    companion object {
        fun createRetrofit(): Retrofit {
            val client = OkHttpClient.Builder().apply {
                readTimeout(5, TimeUnit.MINUTES)
                writeTimeout(5, TimeUnit.MINUTES)
                connectTimeout(5, TimeUnit.MINUTES)
                addInterceptor { chain ->
                    var request = chain.request()
                    request = request.newBuilder()
                        .build()
                    val response = chain.proceed(request)
                    response
                }
            }

            return Retrofit.Builder()
                .baseUrl(Endpoints.URL_BASE)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}