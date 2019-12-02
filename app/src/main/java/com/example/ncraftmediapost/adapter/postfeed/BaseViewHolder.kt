package com.example.ncraftmediapost.adapter.postfeed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ncraftmediapost.dto.Post


abstract class BaseViewHolder(val adapter: PostAdapter, view: View): RecyclerView.ViewHolder(view) {

    abstract fun bind(post: Post)
}