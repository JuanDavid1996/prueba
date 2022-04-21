package co.com.ceiba.mobile.pruebadeingreso

import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.CloudStorage
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class CloudUnitTest {

    @Test
    fun usersFromCloud() {
        val cloudStorage = CloudStorage()
        runBlocking {
            val call = cloudStorage.users()
            if (call.isSuccessful) {
                val users = call.body()!!.sortedBy { it.id }
                Assert.assertNotSame(users.size, 0)
                var counter = 1
                for (user in users) {
                    user.id
                    Assert.assertEquals(user.id, counter)
                    counter++
                }
            }
        }
    }

    @Test
    fun postsFromCloud() {
        val cloudStorage = CloudStorage()
        runBlocking {
            val call = cloudStorage.getPostsByUser(1)
            if (call.isSuccessful) {
                val posts = call.body()!!
                Assert.assertEquals(posts[0].userId.toLong(), 1)
            }
        }
    }
}