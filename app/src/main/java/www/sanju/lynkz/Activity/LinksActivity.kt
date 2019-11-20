package www.sanju.lynkz.Activity

import android.annotation.SuppressLint
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
import io.github.ponnamkarthik.richlinkpreview.RichLinkView
import io.github.ponnamkarthik.richlinkpreview.ViewListener
import www.sanju.lynkz.Models.Links
import www.sanju.lynkz.Models.Subcat
import www.sanju.lynkz.R

class LinksActivity : AppCompatActivity() {

    private lateinit var linksRV: RecyclerView
    private lateinit var linksDB: DatabaseReference
    private lateinit var richLinkView: RichLinkView


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_links)


        //de8404bdeffd4ab39f03d8864d672a3c


        //init DB
        linksDB = FirebaseDatabase.getInstance().reference.child("Links")

        //init layouts
        linksRV = findViewById(R.id.links_rv)

        linksRV.layoutManager = LinearLayoutManager(
            this@LinksActivity,
            LinearLayout.VERTICAL, false
        )


        if (intent!=null){

            val id = intent.getStringExtra("categoryID")

            if (!id.isEmpty() && id!=null){

                initLinks(id)
            }


        }

    }

    private fun initLinks(id: String?) {

        val query: Query = linksDB.orderByChild("menuID").equalTo(id)


        val option = FirebaseRecyclerOptions.Builder<Links>()
            .setQuery(query, Links::class.java)
            .setLifecycleOwner(this)
            .build()

        val firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Links, MyViewHolder>(option) {


                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

                    val itemView = LayoutInflater.from(this@LinksActivity)
                        .inflate(R.layout.links_layout_rv, parent, false)
                    return MyViewHolder(itemView)
                }

                override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Links) {

                    val placeid = getRef(position).key.toString()




                    linksDB.child(placeid).addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(
                                this@LinksActivity,
                                "Error Occurred " + p0.toException(),
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        override fun onDataChange(p0: DataSnapshot) {

                            holder.url.text = model.url

                            richLinkView = holder.itemView.findViewById(R.id.richLinkView)

                            richLinkView = holder.itemView.findViewById(R.id.richLinkView)


                            richLinkView.setLink(
                                model.url,
                                object : ViewListener {
                                    override fun onSuccess(status: Boolean) {

                                        Toast.makeText(this@LinksActivity,"Success",Toast.LENGTH_SHORT).show()
                                    }
                                    override fun onError(e: Exception) {
                                        Toast.makeText(this@LinksActivity,"Failed",Toast.LENGTH_SHORT).show()

                                    }
                                })

                        }
                    })
                }
            }
        linksRV.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()


    }


    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        internal var url: TextView = itemView!!.findViewById<TextView>(R.id.url_links)



    }

}