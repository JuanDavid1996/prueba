package co.com.ceiba.mobile.pruebadeingreso.presentation.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.presentation.adapters.PostsAdapter.PostViewHolder
import co.com.ceiba.mobile.pruebadeingreso.storage.models.Post
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import co.com.ceiba.mobile.pruebadeingreso.R
import android.widget.TextView
import java.util.ArrayList

class PostsAdapter(private var posts: List<Post> = ArrayList()) :
    RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.post_list_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.title.text = post.title
        holder.body.text = post.body
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var body: TextView = view.findViewById(R.id.body)
    }
}