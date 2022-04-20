package co.com.ceiba.mobile.pruebadeingreso.view.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.storage.models.Post;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    List<Post> posts = new ArrayList<>();

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.post_list_item, parent, false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.title.setText(post.title);
        holder.body.setText(post.body);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView body;

        public PostViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            body = view.findViewById(R.id.body);
        }
    }
}
