package co.com.ceiba.mobile.pruebadeingreso.storage.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    var id: Int,
    var name: String,
    var phone: String,
    var email: String
) {
    override fun toString(): String {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}'
    }
}