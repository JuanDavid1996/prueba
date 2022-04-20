package co.com.ceiba.mobile.pruebadeingreso.storage.local;

import android.content.Context;

import java.util.List;
import java.util.Objects;

import co.com.ceiba.mobile.pruebadeingreso.storage.local.models.RealmUser;
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmFileUserStore;

import static io.realm.RealmConfiguration.Builder;

public class LocalStorage {

    private static Realm realm;
    Converter converter;

    public LocalStorage() {
        converter = new Converter();
    }

    public static void initDb(Context context) {
        Realm.init(context);

        RealmConfiguration config = new Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public List<User> getUsers() {
        return converter.usersFromRealm(realm.where(RealmUser.class).findAll());
    }

    public void saveUsers(List<User> users) {
        realm.executeTransaction(realm -> {
            for (User user : users) {
                RealmUser realmUser = realm.createObject(RealmUser.class, user.id);
                realmUser.name = user.name;
                realmUser.phone = user.phone;
                realmUser.email = user.email;
                realm.insert(realmUser);
            }
        });
    }

    public List<User> getUsersByName(String name) {
        return converter.usersFromRealm(
                realm
                    .where(RealmUser.class)
                    .contains(RealmUser.RealmUserFields.NAME, name, Case.INSENSITIVE)
                    .findAll()
        );
    }

    public User getUserById(int userId) {
        return converter.userFromRealm(
                Objects.requireNonNull(realm
                        .where(RealmUser.class)
                        .equalTo(RealmUser.RealmUserFields.ID, userId)
                        .findFirst())
        );
    }
}
