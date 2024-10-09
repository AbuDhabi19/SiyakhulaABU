import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.siyakhula.EditUser
import com.example.siyakhula.R
import com.example.siyakhula.User

class UserAdapter(
    private val userList: List<User>,
    private val onUserAction: (User, String) -> Unit // Lambda for handling actions
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userNameTextView: TextView = view.findViewById(R.id.userNameTextView)
        val userEmailTextView: TextView = view.findViewById(R.id.userEmailTextView)
        val userPasswordTextView: TextView = view.findViewById(R.id.userPasswordTextView)


        val editButton: View = view.findViewById(R.id.editButton)
        val deleteButton: View = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.userNameTextView.text = user.name
        holder.userEmailTextView.text = user.email
        // Masking the password
        holder.userPasswordTextView.text = "********"

        // Handle edit button click
        holder.editButton.setOnClickListener {
            // Start EditUser activity and pass the user data
            val intent = Intent(holder.itemView.context, EditUser::class.java)
            intent.putExtra("User", user) // Pass the User object
            holder.itemView.context.startActivity(intent)
        }

        // Handle delete button click
        holder.deleteButton.setOnClickListener {
            onUserAction(user, "delete")
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
