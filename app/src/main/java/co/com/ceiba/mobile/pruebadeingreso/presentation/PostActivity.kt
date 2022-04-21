package co.com.ceiba.mobile.pruebadeingreso.presentation

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.presentation.adapters.PostsAdapter
import android.os.Bundle
import androidx.activity.viewModels
import co.com.ceiba.mobile.pruebadeingreso.R
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.mobile.pruebadeingreso.constants.Constants.USER_ID_EXTRA_KEY
import co.com.ceiba.mobile.pruebadeingreso.presentation.viewmodels.PostViewModel

class PostActivity : BaseActivity() {
    private lateinit var name: TextView
    private lateinit var phone: TextView
    private lateinit var email: TextView
    private lateinit var postList: RecyclerView
    private lateinit var adapter: PostsAdapter
    var userId = 0

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        name = findViewById(R.id.name)
        phone = findViewById(R.id.phone)
        email = findViewById(R.id.email)
        postList = findViewById(R.id.recyclerViewPostsResults)
        postList.layoutManager = LinearLayoutManager(this)
        postList.setHasFixedSize(true)
        adapter = PostsAdapter()
        postList.adapter = adapter
        userId = intent.extras?.getInt(USER_ID_EXTRA_KEY, 0) ?: 0

        viewModel.user.observe(this) { user ->
            if (user != null) {
                name.text = user.name
                phone.text = user.phone
                email.text = user.email
                title = getString(R.string.posts) + user.name
                showProgress()

                viewModel.getPostByUserId(userId)
            }
        }

        viewModel.posts.observe(this) {
            hideProgress()
            adapter.setPosts(it)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUser(userId)
    }
}