package co.feip.fefu2025

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import android.util.Log

import androidx.core.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

class MainActivity : ComponentActivity() {
    private var counter = 0

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (isInternetAvailable(context)) {
                Log.d("Internet Connection", "Интернет: подключен\n")
            } else {
                Log.d("Internet Connection", "Интернет: отключен\n")
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textViewClick)
        textView.textSize = 24f
        val counterColor = ContextCompat.getColor(this, R.color.purple_500)

        counter = savedInstanceState?.getInt("counter", 0) ?: 0
        updateCounterText(textView, counter, counterColor)

        textView.setOnClickListener {
            counter++
            updateCounterText(textView, counter, counterColor)
        }

        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun updateCounterText(textView: TextView, counter: Int, color: Int) {
        val text = "Кликните! $counter"
        val spannable = SpannableString(text)

        val startIndex = text.indexOf(counter.toString())
        val endIndex = startIndex + counter.toString().length

        spannable.setSpan(
            ForegroundColorSpan(color),
            startIndex, endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannable
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter", counter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
    }

    private fun isInternetAvailable(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}