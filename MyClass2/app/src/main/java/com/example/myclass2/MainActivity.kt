import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import com.example.myclass2.R

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var carButton: Button
    private lateinit var bikeButton: Button

    private val cars = listOf("Tesla Model 3", "Nissan Leaf", "Hyundai Kona", "BMW i4", "Audi e-Tron")
    private val bikes = listOf("Revolt RV400", "Ola S1 Pro", "Ather 450X", "Hero Electric Photon", "Bajaj Chetak")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        carButton = findViewById(R.id.carButton)
        bikeButton = findViewById(R.id.bikeButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        loadList(cars) // Default view: Cars

        carButton.setOnClickListener { loadList(cars) }
        bikeButton.setOnClickListener { loadList(bikes) }
    }

    private fun loadList(items: List<String>) {
        recyclerView.adapter = VehicleAdapter(items)
    }
}
