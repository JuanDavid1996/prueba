package co.com.ceiba.mobile.pruebadeingreso.storage;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.CloudStorage;
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.helpers.InternetAccess;
import co.com.ceiba.mobile.pruebadeingreso.storage.local.LocalStorage;
import co.com.ceiba.mobile.pruebadeingreso.storage.models.Post;
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User;
import rx.SingleSubscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StorageTest {

    Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getContext();
        LocalStorage.initDb(context);
        InternetAccess.checkConnectionStatus(context);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("co.com.ceiba.mobile.pruebadeingreso", appContext.getPackageName());
    }

    @Test
    public void getUsersFromCloud() {
        CloudStorage cloudStorage = new CloudStorage(context);
        cloudStorage.getUsers().subscribe(new SingleSubscriber<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                assertNotSame(users.size(), 0);
            }
            @Override
            public void onError(Throwable error) {
                assertEquals(context.getString(R.string.no_internet), error.getMessage());
            }
        });
    }

    @Test
    public void getPostsFromCloud() {
        CloudStorage cloudStorage = new CloudStorage(context);
        cloudStorage.getPostsByUser(1).subscribe(new SingleSubscriber<List<Post>>() {
            @Override
            public void onSuccess(List<Post> posts) {
                assertEquals(posts.get(0).userId, 1);
            }
            @Override
            public void onError(Throwable error) {
                assertEquals(context.getString(R.string.no_internet), error.getMessage());
            }
        });
    }
}
