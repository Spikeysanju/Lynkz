package www.sanju.lynkz.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import www.sanju.lynkz.Fragments.ExploreFragment
import www.sanju.lynkz.Models.Category
import www.sanju.lynkz.Models.Subcat
import www.sanju.lynkz.R

class SubcategoryActivity : AppCompatActivity() {


    private lateinit var subCatRV: RecyclerView
    private lateinit var subCatDB: DatabaseReference
   //private lateinit var categoryID: String

    lateinit var firebaseRecyclerAdapter:FirebaseRecyclerAdapter<Subcat, MyViewHolder>


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subcategory)

        //init DB
        subCatDB = FirebaseDatabase.getInstance().reference.child("Subcat")

        //init layouts
        subCatRV = findViewById(R.id.subcat_rv)

        subCatRV.layoutManager = LinearLayoutManager(
            this@SubcategoryActivity!!,
            LinearLayout.VERTICAL, false
        )


        if (intent!=null){

            val id = intent.getStringExtra("categoryID")

            if (!id.isEmpty() && id!=null){

                initSubCategory(id)
            }


        }

    }

    private fun initSubCategory(id: String?) {
        val query: Query = subCatDB.orderByChild("menuID").equalTo(id)


        val option = FirebaseRecyclerOptions.Builder<Subcat>()
            .setQuery(query, Subcat::class.java)
            .setLifecycleOwner(this)
            .build()

         firebaseRecyclerAdapter =
            object : FirebaseRecyclerAdapter<Subcat, MyViewHolder>(option) {


                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): MyViewHolder {
                    val itemView = LayoutInflater.from(this@SubcategoryActivity)
                        .inflate(R.layout.all_category_layout, parent, false)
                    return MyViewHolder(itemView)
                }

                override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Subcat) {

                    val placeid = getRef(position).key.toString()



                    subCatDB.child(placeid).addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(
                                this@SubcategoryActivity!!,
                                "Error Occurred " + p0.toException(),
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        override fun onDataChange(p0: DataSnapshot) {

                            holder.category_title.text = model.name

                            Picasso.get().load(model.image).into(holder.category_image)

                            holder.itemView.setOnClickListener{

                                val intent = Intent(this@SubcategoryActivity,LinksActivity::class.java)
                                intent.putExtra("categoryID",firebaseRecyclerAdapter.getRef(position).key)
                                startActivity(intent)

                            }

                        }
                    })
                }
            }
        subCatRV.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()


    }


    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        internal var category_title: TextView = itemView!!.findViewById<TextView>(R.id.cat_title)
        internal var category_image: ImageView = itemView!!.findViewById<ImageView>(R.id.cat_Img)


    }

}