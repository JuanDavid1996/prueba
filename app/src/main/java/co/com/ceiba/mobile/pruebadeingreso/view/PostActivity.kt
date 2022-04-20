package co.com.ceiba.mobile.pruebadeingreso.view;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.storage.AppStorage;
import co.com.ceiba.mobile.pruebadeingreso.storage.models.Post;
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User;
import co.com.ceiba.mobile.pruebadeingreso.view.adapters.PostsAdapter;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

import static co.com.ceiba.mobile.pruebadeingreso.constants.Constants.USER_ID_EXTRA_KEY;

public class PostActivity extends BaseActivity {

    TextView name;
    TextView phone;
    TextView email;
    RecyclerView postList;
    PostsAdapter adapter;

    int userId = 0;
    AppStorage appStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        postList = findViewById(R.id.recyclerViewPostsResults);
        postList.setLayoutManager(new LinearLayoutManager(this));
        postList.setHasFixedSize(true);

        adapter = new PostsAdapter();
        postList.setAdapter(adapter);

        userId = Objects.requireNonNull(getIntent().getExtras()).getInt(USER_ID_EXTRA_KEY, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        appStorage = new AppStorage(this);

        User user = appStorage.getUserById(userId);

        name.setText(user.name);
        phone.setText(user.phone);
        email.setText(user.email);

        setTitle(getString(R.string.posts) + user.name);
        showProgress();

        appStorage
                .getPostsByUserId(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<List<Post>>() {
                    @Override
                    public void onSuccess(List<Post> posts) {
                        hideProgress();
                        adapter.setPosts(posts);
                    }

                    @Override
                    public void onError(Throwable error) {
                        hideProgress();
                        PostActivity.this.onError(error);
                    }
                });

    }


}
