package com.example.ncraftmediapost.adapter.postfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ncraftmediapost.R
import com.example.ncraftmediapost.dto.Post
import com.example.ncraftmediapost.dto.PostType

const val VIEW_TYPE_POST = 1
const val VIEW_TYPE_REPOST = 2
const val VIEW_TYPE_REPLY = 3
const val VIEW_TYPE_VIDEO = 4
const val VIEW_TYPE_ADVERTISING = 5

fun viewTypeToPostType(viewType: Int) = when (viewType) {
    VIEW_TYPE_POST -> PostType.POST
    VIEW_TYPE_REPOST -> PostType.REPOST
    VIEW_TYPE_REPLY -> PostType.REPLY
    else -> TODO()
}

class PostAdapter(val list: List<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (viewType) {
            VIEW_TYPE_POST -> PostViewHolder(
                this, LayoutInflater.from(parent.context).inflate(
                    R.layout.post_list_item,
                    parent,
                    false
                )
            )
            VIEW_TYPE_REPOST -> VideoViewHolder(
            this, LayoutInflater.from(parent.context).inflate(
                R.layout.repost_list_item,
                parent,
                false
            )
        )
            VIEW_TYPE_REPLY -> RepostViewHolder(
                this, LayoutInflater.from(parent.context).inflate(
                    R.layout.reply_list_item,
                    parent,
                    false
                )
            )
            VIEW_TYPE_VIDEO -> ReplyViewHolder(
                this, LayoutInflater.from(parent.context).inflate(
                    R.layout.video_youtube_item,
                    parent,
                    false
                )
            )
            VIEW_TYPE_ADVERTISING -> AdertisingViewHolder(
                this, LayoutInflater.from(parent.context).inflate(
                    R.layout.advertising_list_item,
                    parent,
                    false
                )
            )
        else-> TODO()
        }


    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long = list[position].id


    override fun getItemViewType(position: Int): Int {
        return when (list[position].postType) {

            PostType.POST -> VIEW_TYPE_POST
            PostType.REPOST -> VIEW_TYPE_REPOST
            PostType.REPLY -> VIEW_TYPE_REPLY
            PostType.VIDEO -> VIEW_TYPE_VIDEO
            PostType.ADVERTISING -> VIEW_TYPE_ADVERTISING
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as BaseViewHolder) {
            bind(list[position])
        }
    }
}