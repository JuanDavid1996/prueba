package co.com.ceiba.mobile.pruebadeingreso.presentation

import android.widget.RelativeLayout
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.presentation.adapters.UsersAdapter
import android.os.Bundle
import co.com.ceiba.mobile.pruebadeingreso.R
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Intent
import android.text.TextWatcher
import android.text.Editable
import android.view.View
import co.com.ceiba.mobile.pruebadeingreso.constants.Constants.USER_ID_EXTRA_KEY
import androidx.activity.viewModels
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User
import co.com.ceiba.mobile.pruebadeingreso.presentation.viewmodels.MainViewModel

open class MainActivity : BaseActivity() {
    private lateinit var content: RelativeLayout
    private lateinit var editTextSearch: EditText
    private lateinit var userList: RecyclerView
    private lateinit var adapter: UsersAdapter
    private lateinit var emptyView: RelativeLayout
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        content = findViewById(R.id.content)
        editTextSearch = findViewById(R.id.editTextSearch)
        userList = findViewById(R.id.recyclerViewSearchResults)
        userList.layoutManager = LinearLayoutManager(this)
        userList.setHasFixedSize(true)
        adapter = UsersAdapter { user -> toUserPost(user) }
        userList.adapter = adapter
        editTextSearch.isEnabled = false
        emptyView = findViewById(R.id.empty_view)

        viewModel.isLoading.observe(this) {
            if (it) showProgress()
            else hideProgress()
        }

        viewModel.users.observe(this) {
            enableSearch()
            adapter.setUsers(it)

            if (it.isEmpty()) {
                emptyView.visibility = View.VISIBLE
                userList.visibility = View.GONE
            } else {
                emptyView.visibility = View.GONE
                userList.visibility = View.VISIBLE
            }
        }
    }

    private fun toUserPost(user: User) {
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra(USER_ID_EXTRA_KEY, user.id)
        startActivity(intent)
    }

    private fun enableSearch() {
        editTextSearch.isEnabled = true
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel.getUsersByName(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUsers()
    }
}