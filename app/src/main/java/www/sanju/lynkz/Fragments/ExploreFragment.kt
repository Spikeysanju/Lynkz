package www.sanju.lynkz.Fragments


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import www.sanju.lynkz.Activity.SubcategoryActivity
import www.sanju.lynkz.Models.Category

import www.sanju.lynkz.R

/**
 * A simple [Fragment] subclass.
 */
class ExploreFragment : Fragment() {

    private lateinit var categoryRV: RecyclerView
    lateinit var categoryDB: DatabaseReference

    lateinit var firebaseRecyclerAdapter:FirebaseRecyclerAdapter<Category,MyViewHolder>

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore, container, false)


        //init DB
        categoryDB = FirebaseDatabase.getInstance().reference.child("Category")

        //init layouts
        categoryRV = view.findViewById(R.id.category_rv)

        categoryRV.layoutManager = LinearLayoutManager(
            context!!,
            LinearLayout.VERTICAL, false
        )

        initCategory()

        return view
    }

    private fun initCategory() {


        val option = FirebaseRecyclerOptions.Builder<Category>()
            .setQuery(categoryDB, Category::class.java)
            .setLifecycleOwner(this)
            .build()

         firebaseRecyclerAdapter =
            object : FirebaseRecyclerAdapter<Category, MyViewHolder>(option) {


                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): MyViewHolder {
                    val itemView = LayoutInflater.from(context!!)
                        .inflate(R.layout.all_category_layout, parent, false)
                    return MyViewHolder(itemView)
                }

                override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Category) {

                    val placeid = getRef(position).key.toString()



                    categoryDB.child(placeid).addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(
                                context!!,
                                "Error Occurred " + p0.toException(),
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        override fun onDataChange(p0: DataSnapshot) {

                            holder.category_title.text = model.name

                            Picasso.get().load(model.image).into(holder.category_image)

                            holder.itemView.setOnClickListener{

                                val intent = Intent(context!!,SubcategoryActivity::class.java)
                                intent.putExtra("categoryID",firebaseRecyclerAdapter.getRef(position).key)
                                startActivity(intent)

                            }


                        }
                    })
                }
            }
        categoryRV.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()


    }


    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        internal var category_title: TextView = itemView!!.findViewById<TextView>(R.id.cat_title)
        internal var category_image: ImageView = itemView!!.findViewById<ImageView>(R.id.cat_Img)


    }

}