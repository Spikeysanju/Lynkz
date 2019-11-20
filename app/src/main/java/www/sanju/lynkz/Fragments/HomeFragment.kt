package www.sanju.lynkz.Fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONObject
import www.sanju.lynkz.Adapter.NewsAdapter
import www.sanju.lynkz.Models.News

import www.sanju.lynkz.R

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {


    val news = ArrayList<News>()


    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val  newsRecyclerView = view.findViewById(R.id.news_rv)  as RecyclerView

        AndroidNetworking.initialize(context)

        newsRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayout.VERTICAL, false
        )

        initNews()


        return  view

    }

    private fun initNews() {

        news.clear()

        AndroidNetworking.get("https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=de8404bdeffd4ab39f03d8864d672a3c")
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(object : StringRequestListener{
                override fun onResponse(response: String?) {

                    Log.e("news :", response.toString())


                  //  val launch = JSONArray(response)
                    val res = JSONObject(response)

                        val articles = res.optJSONArray("articles")

                        for(i in 0 until articles.length()){

                            val article = articles.optJSONObject(i)


                            news.add(
                                News(
                                    article.getString("author"),
                                    article.optString("title"),
                                    article.getString("description"),
                                    article.getString("url"),
                                    article.getString("urlToImage"),
                                    article.getString("publishedAt"),
                                    article.getString("content")



                                )
                            )
                        }




                        news_rv.adapter = NewsAdapter(context, news)



                }

                override fun onError(anError: ANError?) {
                    Log.i("_err", anError.toString())

                }


            })

    }


}
