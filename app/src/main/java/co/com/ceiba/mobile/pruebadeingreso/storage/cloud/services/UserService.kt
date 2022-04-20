package co.com.ceiba.mobile.pruebadeingreso.storage.cloud.services;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.storage.models.User;
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.Endpoints;
import retrofit2.http.GET;
import rx.Single;

public interface UserService {
    @GET(Endpoints.GET_USERS)
    Single<List<User>> getUsers();
}
