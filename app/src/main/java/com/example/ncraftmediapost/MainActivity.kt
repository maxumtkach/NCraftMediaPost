package com.example.ncraftmediapost

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ncraftmediapost.adapter.postfeed.PostAdapter
import com.example.ncraftmediapost.dto.Post
import com.example.ncraftmediapost.dto.PostType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post_list_item.like_text
import kotlinx.android.synthetic.main.replyt_list_item.*
import java.sql.Timestamp
import java.util.*


class MainActivity : AppCompatActivity() {
    private var lat = ""
    private var lon = ""
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeValue = System.currentTimeMillis()//timespam
        val stamp = Timestamp(timeValue)
        val date: Date = Date(stamp.getTime())

        val meetup = Post(1, "Netology", "First post in our network!", date.toString())

        val list = listOf(
            Post(
                2, "Google",
                "Кто-то ждет дождя а кто-то - солнца. Узнай вероятность дождя на завтра спомощю Google Поиска",
                "www.google.com",
                "Завтра опять будет дождь ?",
                "Рекламная запись", false, PostType.ADVERTISING
            ),
            Post(
                3, "Netology", "First post in our network!", "", date.toString(),
                "", true, PostType.REPOST
            ),
            Post(4, "Netology", "Kotlin", date.toString()),
            Post(5, "Netology", "First post in our network!", date.toString()),
            Post(6, "Netology", "Kotlin", date.toString(), "", "", true,
                PostType.REPOST, meetup)
        )

        with(container) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostAdapter(list)
        }
    }

    fun transitionClick(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
        startActivity(browserIntent)

    }

    fun locationByMe(view: View) {
        val intent = Intent().apply {
            //       val lat = ""
            //     val lon = ""
            data = Uri.parse("geo:$lat,$lon")
            action = Intent.ACTION_VIEW
            putExtra(
                Intent.EXTRA_TEXT, """
                (${latitude_text.text})${longitude_text.text}
            """.trimIndent()
            )
            type = "text/plain"
        }
        startActivity(intent)
    }
}