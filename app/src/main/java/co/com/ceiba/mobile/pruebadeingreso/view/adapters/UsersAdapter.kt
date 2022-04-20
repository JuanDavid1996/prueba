package co.com.ceiba.mobile.pruebadeingreso.view.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    List<User> users = new ArrayList<>();
    OnUserPostListener userListener;

    public interface OnUserPostListener {
        void onClick(User user);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);

        holder.name.setText(user.name);
        holder.phone.setText(user.phone);
        holder.email.setText(user.email);
        holder.viewPost.setOnClickListener(view -> {
            userListener.onClick(user);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public void setUserListener(OnUserPostListener userListener) {
        this.userListener = userListener;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView phone;
        public TextView email;
        public Button viewPost;

        public UserViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            email = view.findViewById(R.id.email);
            viewPost = view.findViewById(R.id.btn_view_post);
        }
    }

}
