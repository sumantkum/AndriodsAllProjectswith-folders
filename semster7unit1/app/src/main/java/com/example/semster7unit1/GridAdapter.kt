import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.semster7unit1.GridItem
import com.example.semster7unit1.R

class GridAdapter(
    private val items: List<GridItem>,
    private val onClick: (GridItem) -> Unit
) : RecyclerView.Adapter<GridAdapter.VH>() {

    inner class VH(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.icon)
        private val title: TextView = itemView.findViewById(R.id.title)

        fun bind(item: GridItem) {
            icon.setImageResource(item.iconRes)
            title.text = item.title
            itemView.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grid, parent, false)   // Inflate as View, not GridItem
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
