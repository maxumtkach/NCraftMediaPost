package com.example.ncraftmediapost.dto

import java.util.*

enum class PostType {
    POST, REPOST, REPLY,VIDEO,ADVERTISING
}

class Post(
    val id: Long,
    val author: String?=null,
    val content: String? = null,
    val location:Coordinates?=null,  //координаты
    var countLike:Int,
    var countChat:Int,
    var countCommmit:Int,
    val wet:String?=null,
    val resource:String?=null,
    val created: String?=null, //время я
    var likedByMe: Boolean ,
    val postType: PostType = PostType.POST,
    val source: Post? = null   //источник
)