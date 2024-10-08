import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.siyakhula.Program
import com.example.siyakhula.R

class ProgramAdapter(
    private val programs: List<Program>,
    private val onActionClick: (Program, String) -> Unit
) : RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_program, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val program = programs[position]
        holder.programName.text = program.name

        holder.editProgram.setOnClickListener {
            onActionClick(program, "edit")
        }

        holder.deleteProgram.setOnClickListener {
            onActionClick(program, "delete")
        }
    }

    override fun getItemCount(): Int = programs.size

    inner class ProgramViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val programName: TextView = view.findViewById(R.id.tv_program_name)
        val editProgram: TextView = view.findViewById(R.id.tv_edit_program)
        val deleteProgram: TextView = view.findViewById(R.id.tv_delete_program)
    }
}
