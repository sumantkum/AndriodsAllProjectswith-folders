class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: PersonAdapter
    private val people = mutableListOf<Person>()
    private val PICK_IMAGE = 100
    private var tempName = ""
    private var tempPhone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        adapter = PersonAdapter(this, people)
        listView.adapter = adapter

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val nameInput = EditText(this)
        nameInput.hint = "Enter Name"
        layout.addView(nameInput)

        val phoneInput = EditText(this)
        phoneInput.hint = "Enter Phone"
        layout.addView(phoneInput)

        AlertDialog.Builder(this)
            .setTitle("New Contact")
            .setView(layout)
            .setPositiveButton("Select Image") { _, _ ->
                tempName = nameInput.text.toString()
                tempPhone = phoneInput.text.toString()
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, PICK_IMAGE)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            people.add(Person(tempName, tempPhone, uri))
            adapter.notifyDataSetChanged()
        }
    }
}
