package com.example.commit.ListItem

data class Homefeed(val items: List<Item>)
data class Item (
    val title:String?,
    val link:String?,
    val description:String?,
    val telephone:String?,
    val address:String?
)