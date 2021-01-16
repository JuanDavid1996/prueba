package co.com.ceiba.mobile.pruebadeingreso.storage.cloud;

import android.content.Context;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.helpers.InternetAccess;
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.services.PostService;
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.services.UserService;
import co.com.ceiba.mobile.pruebadeingreso.storage.models.Post;
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Single;

public class CloudStorage {

    Context context;

    public CloudStorage(Context context) {
        this.context = context;
    }

    public static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Endpoints.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public Single<List<User>> getUsers() {
        return new InternetAccess<List<User>>(context)
                .check(createRetrofit().create(UserService.class).getUsers());
    }

    public Single<List<Post>> getPosts() {
        return createRetrofit().create(PostService.class).getPosts();
    }

    public Single<List<Post>> getPostsByUser(int userId) {
        return new InternetAccess<List<Post>>(context)
                .check(createRetrofit().create(PostService.class).getPostsByUser(userId));
    }
}
