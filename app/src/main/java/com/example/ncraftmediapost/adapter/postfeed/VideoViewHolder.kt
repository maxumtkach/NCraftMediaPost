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
    override fun bind(post: Post) {

        with(itemView) {
            data_text.text = post.created
            author_text.text = post.author
            // post_text.text = post.content
            wet_text.text = post.wet
            wet_res_text.text = post.resource
            //  chat_text.text = countShare.toString()
            //share_text.text = countChat.toString()


            val favoriteRed = btn_image_like.setImageResource(R.drawable.ic_favorite_red_24dp)
            val likeTextRed = like_text.setTextColor(Color.rgb(255, 0, 0))

            btn_image_like.setOnClickListener() {
                post.countLike++
                like_text.text = post.countLike.toString()
                likeTextRed.toString()
                favoriteRed.toString()
            }

            if (post.likedByMe && post.countLike == 0) {
                post.countLike++
                favoriteRed.toString()
                likeTextRed.toString()

                like_text.text = post.countLike.toString()

            } else if (!post.likedByMe && post.countLike != 0 || post.likedByMe && post.countLike != 0) {
                like_text.text = post.countLike.toString()
                favoriteRed.toString()
                likeTextRed.toString()
            } else {
                btn_image_like.setImageResource(R.drawable.ic_favorite_inactive_24dp)
            }

            btn_image_chat.setOnClickListener() {
                post.countChat++
                chat_text.text = post.countChat.toString()
                chat_text.setTextColor(Color.rgb(255, 0, 0))
                btn_image_chat.setImageResource(R.drawable.ic_chat_bubble_24dp)
            }
            if (post.countChat != 0) {
                chat_text.text = post.countChat.toString()
                chat_text.setTextColor(Color.rgb(255, 0, 0))
                btn_image_chat.setImageResource(R.drawable.ic_chat_bubble_24dp)
            } else {
                btn_image_chat.setImageResource(R.drawable.ic_chat_bubble_black_24dp)
            }

            share_btn.setOnClickListener() {
                post.countCommmit++
                share_text.text = post.countCommmit.toString()
                share_text.setTextColor(Color.rgb(255, 0, 0))
                share_btn.setImageResource(R.drawable.ic_share_24dp)
            }
            if (post.countCommmit != 0) {
                share_text.text = post.countCommmit.toString()
                share_text.setTextColor(Color.rgb(255, 0, 0))
                share_btn.setImageResource(R.drawable.ic_share_24dp)
            } else {
                share_btn.setImageResource(R.drawable.ic_share_black_24dp)
            }
        }
    }
}