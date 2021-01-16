package co.com.ceiba.mobile.pruebadeingreso.storage.local;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.storage.models.User;
import co.com.ceiba.mobile.pruebadeingreso.storage.local.models.RealmUser;
import io.realm.RealmResults;

public class Converter {

    public User userFromRealm(RealmUser realmUser) {
        return new User(realmUser.id, realmUser.name, realmUser.phone, realmUser.email);
    }

    public List<User> usersFromRealm(RealmResults<RealmUser> realmUsers) {
        List<User> output = new ArrayList<>();
        for (RealmUser realmUser: realmUsers) {
            output.add(userFromRealm(realmUser));
        }
        return output;
    }

}
