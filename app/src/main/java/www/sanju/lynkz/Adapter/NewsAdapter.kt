package www.sanju.lynkz.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_rv_layout.view.*
import www.sanju.lynkz.Activity.WebViewActivity
import www.sanju.lynkz.Models.News
import www.sanju.lynkz.R

class NewsAdapter (val context: Context?, private val news: ArrayList<News>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.newsTitle.text = news[position].title
        holder.itemView.newsAuthor.text = news[position].author
        holder.itemView.newsDescription.text = news[position].url
        holder.itemView.newsTime.text = news[position].publishedAt
        holder.itemView.newsImg.clipToOutline = true
        Picasso.get().load(news[position].urlToImage).into(holder.itemView.newsImg)


        //Image Animation
        //  holder.newsImageUrl.animation = AnimationUtils.loadAnimation(context,R.anim.fade_image)


        //Card Animation
        // holder.card.animation = AnimationUtils.loadAnimation(context,R.anim.card_fade)    }



    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_rv_layout, parent, false)
        return ViewHolder(v)


    }

    override fun getItemCount(): Int {
        return news.size
    }

}

class ViewHolder(v: View?) : RecyclerView.ViewHolder(v!!),View.OnClickListener {
    override fun onClick(v: View?) {

        val intent = Intent(v!!.context, WebViewActivity::class.java)
        intent.putExtra("key", newsDesc.text)
        v.context.startActivity(intent)


    }

    init {
        itemView.setOnClickListener(this)
    }

    val newsTitle = itemView.newsTitle!!
    val newsAuthor = itemView.newsAuthor!!
    val newsDesc = itemView.newsDescription!!
    val newsTime = itemView.newsTime!!
    val newsImageUrl = itemView.newsImg!!



}
