package co.com.ceiba.mobile.pruebadeingreso.storage.local.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmUser extends RealmObject {
    @PrimaryKey
    public int id;
    public String name;
    public String phone;
    public String email;

    public static class RealmUserFields {
        public static final String ID = "id";
        public static final String NAME = "name";
    }
}
