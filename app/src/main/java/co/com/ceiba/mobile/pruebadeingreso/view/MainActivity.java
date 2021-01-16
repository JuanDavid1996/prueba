package co.com.ceiba.mobile.pruebadeingreso.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.storage.AppStorage;
import co.com.ceiba.mobile.pruebadeingreso.storage.cloud.helpers.InternetAccess;
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User;
import co.com.ceiba.mobile.pruebadeingreso.view.adapters.UsersAdapter;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

import static co.com.ceiba.mobile.pruebadeingreso.constants.Constants.USER_ID_EXTRA_KEY;

public class MainActivity extends BaseActivity {

    RelativeLayout content;
    EditText editTextSearch;
    RecyclerView userList;
    UsersAdapter adapter;
    AppStorage appStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            InternetAccess.checkConnectionStatus(this);
        }

        content = findViewById(R.id.content);
        editTextSearch = findViewById(R.id.editTextSearch);
        userList = findViewById(R.id.recyclerViewSearchResults);
        userList.setLayoutManager(new LinearLayoutManager(this));
        userList.setHasFixedSize(true);

        adapter = new UsersAdapter();
        userList.setAdapter(adapter);

        adapter.setUserListener(user -> {
            Intent intent = new Intent(this, PostActivity.class);
            intent.putExtra(USER_ID_EXTRA_KEY, user.id);
            startActivity(intent);
        });

        editTextSearch.setEnabled(false);
    }

    void enableSearch() {
        editTextSearch.setEnabled(true);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<User> users = appStorage.getUsersByName(charSequence.toString());
                adapter.setUsers(users);
                RelativeLayout emptyView = findViewById(R.id.empty_view);
                if (users.isEmpty()) {
                    emptyView.setVisibility(View.VISIBLE);
                    userList.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    userList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        showProgress();
        appStorage = new AppStorage(this);
        appStorage
                .getUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<List<User>>() {
                    @Override
                    public void onSuccess(List<User> users) {
                        adapter.setUsers(users);
                        enableSearch();
                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable error) {
                        hideProgress();
                        MainActivity.this.onError(error);
                    }
                });
    }


}