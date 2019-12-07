package com.example.ncraftmediapost.adapter.postfeed

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ncraftmediapost.R
import com.example.ncraftmediapost.dto.Post
import kotlinx.android.synthetic.main.advertising_list_item.view.*
import kotlinx.android.synthetic.main.post_list_item.*
import kotlinx.android.synthetic.main.post_list_item.view.*
import kotlinx.android.synthetic.main.post_list_item.view.author_text
import kotlinx.android.synthetic.main.post_list_item.view.btn_image_chat
import kotlinx.android.synthetic.main.post_list_item.view.btn_image_like
import kotlinx.android.synthetic.main.post_list_item.view.chat_text
import kotlinx.android.synthetic.main.post_list_item.view.data_text
import kotlinx.android.synthetic.main.post_list_item.view.like_text
import kotlinx.android.synthetic.main.post_list_item.view.post_text
import kotlinx.android.synthetic.main.post_list_item.view.share_btn
import kotlinx.android.synthetic.main.post_list_item.view.share_text
import kotlinx.android.synthetic.main.reply_list_item.*


class VideoViewHolder(adapter: PostAdapter, view: View) : BaseViewHolder(adapter, view) {
    private var countLike: Int = 0     // счетчики
    private var countChat: Int = 150
    private var countShare: Int = 721
    private var lat = ""
    private var lon = ""

    init {
        with(itemView) {
            btn_image_like.setOnClickListener {
                //LIKE

                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Toast.makeText(context, "like", Toast.LENGTH_SHORT).show()

                    if (countLike != 0) {
                        countLike--
                        like_text.text = countLike.toString()
                        like_text.setTextColor(Color.rgb(255, 255, 255))
                        btn_image_like.setImageResource(R.drawable.ic_favorite_inactive_24dp)

                    } else {
                        btn_image_like.setImageResource(R.drawable.ic_favorite_red_24dp)
                        countLike++
                        like_text.text = countLike.toString()
                        like_text.setTextColor(Color.rgb(255, 0, 0))
                    }
                }
            }



            btn_image_chat.setOnClickListener {
                //CHAT
                if (adapterPosition != RecyclerView.NO_POSITION)
                    Toast.makeText(context, "shat", Toast.LENGTH_SHORT).show()
                btn_image_chat.setImageResource(R.drawable.ic_chat_bubble_24dp)
                countChat++
                chat_text.text = countChat.toString()
            }

            share_btn.setOnClickListener {
                //SHARE
                Toast.makeText(context, "share", Toast.LENGTH_SHORT).show()

                share_btn.setImageResource(R.drawable.ic_share_24dp)
                countShare++
                share_text.text = countShare.toString()
            }
        }
    }

    override fun bind(post: Post) {

        with(itemView) {
            data_text.text = post.created
            author_text.text = post.author
            post_text.text = post.content
            chat_text.text = countShare.toString()
            share_text.text = countChat.toString()
            like_text.text = countLike.toString()

            if (countShare != 0) {
                share_btn.setImageResource(R.drawable.ic_share_24dp)
                share_text.setTextColor(Color.rgb(255, 0, 0))
            }


            if (countChat != 0) {
                btn_image_chat.setImageResource(R.drawable.ic_chat_bubble_24dp)
                chat_text.setTextColor(Color.rgb(255, 0, 0))
            }

            if (post.likedByMe) {

                btn_image_like.setImageResource(R.drawable.ic_favorite_red_24dp)
                countLike++
//                like_text.text = countLike.toString()
            } else {
                btn_image_like.setImageResource(R.drawable.ic_favorite_inactive_24dp)
                like_text.setTextColor(0x999)

            }
        }}
}