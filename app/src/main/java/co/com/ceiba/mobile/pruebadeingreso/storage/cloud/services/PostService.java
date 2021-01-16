package co.com.ceiba.mobile.pruebadeingreso.storage.cloud.services;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.storage.models.Post;
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.Endpoints;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Single;

public interface PostService {

    @GET(Endpoints.GET_POST_USER)
    Single<List<Post>> getPosts();

    @GET(Endpoints.GET_POST_USER)
    Single<List<Post>> getPostsByUser(@Query("userId") int userId);

}
