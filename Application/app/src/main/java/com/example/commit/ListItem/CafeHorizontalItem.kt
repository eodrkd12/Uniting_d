package com.example.commit.ListItem

data class Homefeed(val items: List<Item>)
data class Item (
    val name:String?,
    val x:String?,
    val y:String?,
    val phone:String?,
    val imageSrc:String?,
    val roadAddr:String?
)


data class Image(
    val img_url:String?
)