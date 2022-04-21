package co.com.ceiba.mobile.pruebadeingreso.presentation.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.presentation.adapters.UsersAdapter.UserViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import co.com.ceiba.mobile.pruebadeingreso.R
import android.widget.TextView
import co.com.ceiba.mobile.pruebadeingreso.storage.models.User

class UsersAdapter(
    private var users: List<User> = emptyList(),
    private var userListener: ((user: User) -> Unit)
) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.user_list_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.name.text = user.name
        holder.phone.text = user.phone
        holder.email.text = user.email
        holder.viewPost.setOnClickListener { userListener(user) }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
        var phone: TextView = view.findViewById(R.id.phone)
        var email: TextView = view.findViewById(R.id.email)
        var viewPost: Button = view.findViewById(R.id.btn_view_post)
    }
}