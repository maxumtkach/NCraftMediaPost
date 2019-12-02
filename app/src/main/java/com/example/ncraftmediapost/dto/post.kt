package com.example.ncraftmediapost.dto

import java.util.*

enum class PostType {
    POST, REPOST, REPLY,VIDEO,ADVERTISING
}

class Post(
    val id: Long,
    val author: String,
    val content: String? = null,
    val wet:String?=null,
    val resource:String?=null,
    val created: String?=null, //время
    var likedByMe: Boolean = false,
    val postType: PostType = PostType.POST,
    val source: Post? = null   //источник
)