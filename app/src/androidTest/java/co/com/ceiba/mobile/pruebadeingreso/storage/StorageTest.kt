package co.com.ceiba.mobile.pruebadeingreso.storage

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import co.com.ceiba.mobile.pruebadeingreso.storage.local.LocalStorage.Companion.initDb
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.helpers.InternetAccess.Companion.checkConnectionStatus
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.CloudStorage
import co.com.ceiba.mobile.pruebadeingreso.R
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class StorageTest {
    var context: Context? = null

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().context
        initDb(context)
        checkConnectionStatus(context!!)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("co.com.ceiba.mobile.pruebadeingreso", appContext.packageName)
    }

    @Test
    fun usersFromCloud() {
        val cloudStorage = CloudStorage()
        runBlocking {
            val call = cloudStorage.users()
            if (call.isSuccessful) {
                val users = call.body()!!
                Assert.assertNotSame(users.size, 0)
            } else {
                val error = call.message()
                Assert.assertEquals(context!!.getString(R.string.no_internet), error)
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
            } else {
                val error = call.message()
                Assert.assertEquals(context!!.getString(R.string.no_internet), error)
            }
        }
    }
}