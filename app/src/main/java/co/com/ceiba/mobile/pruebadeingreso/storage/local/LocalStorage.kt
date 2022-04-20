package co.com.ceiba.mobile.pruebadeingreso.storage.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

class LocalStorage {
    val users: List<User>
        get() = db.userDao().getAll()

    fun saveUsers(users: List<User>) {
        db.userDao().insertAll(users)
    }

    fun getUsersByName(name: String): List<User> {
        return db.userDao().getByName(name)
    }

    fun getUserById(userId: Int): User {
        return db.userDao().getById(userId)
    }

    companion object {
        lateinit var db: AppDatabase

        @JvmStatic
        fun initDb(context: Context?) {
            if (context == null) return
            db = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "prueba"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}