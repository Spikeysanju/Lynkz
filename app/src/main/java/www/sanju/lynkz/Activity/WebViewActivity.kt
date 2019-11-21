package www.sanju.lynkz.Activity

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import www.sanju.lynkz.R

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)


        webView = findViewById(R.id.webView)

        if (intent != null) {

            val id = intent.getStringExtra("key")

            if (!(id.isEmpty() && id != null)) {

                loadUrl(id)
            }


        }
    }

    private fun loadUrl(id: String?) {

        webView.loadUrl(id)
    }
}
