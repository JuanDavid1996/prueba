package co.com.ceiba.mobile.pruebadeingreso.storage;

import android.content.Context;
import android.util.Log;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.storage.models.Post;
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User;
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.CloudStorage;
import co.com.ceiba.mobile.pruebadeingreso.storage.local.LocalStorage;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppStorage {

    CloudStorage cloudStorage;
    LocalStorage localStorage;

    public AppStorage(Context context) {
        localStorage = new LocalStorage();
        cloudStorage = new CloudStorage(context);
    }

    public Single<List<User>> getUsers() {
        //TODO: Check internet access

        final List<User> users = localStorage.getUsers();
        if (!users.isEmpty()) {
            return Single.create(subscriber -> subscriber.onSuccess(users));
        }

        return cloudStorage
                .getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(userList -> {
                    localStorage.saveUsers(userList);
                    return userList;
                });
    }

    public Single<List<Post>> getPostsByUserId(final int userId) {
        return cloudStorage.getPostsByUser(userId).subscribeOn(Schedulers.io());
    }

    public List<User> getUsersByName(String name) {
        return localStorage.getUsersByName(name);
    }

    public User getUserById(int userId) {
        return localStorage.getUserById(userId);
    }
}
