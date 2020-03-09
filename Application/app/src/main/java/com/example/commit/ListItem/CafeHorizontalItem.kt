package com.example.commit.ListItem

data class Homefeed(val items: ArrayList<Item>)
data class Item (
    val name:String?,
    val x:String?,
    val y:String?,
    val phone:String?,
    val imageSrc:String?,
    val roadAddr:String?,
    val category:String?,
    val tags:ArrayList<String>?,
    val id:String?,
    val options:String?,
    val bizHourInfo:String?
)

data class Type(
    val title:String?
)

data class Menu(
    val name:String?,
    val price:String?
)

data class KoreanFood(
    val name:String?,
    val imageSrc:String?
)

data class ChineseFood(
    val name:String?,
    val imageSrc:String?
)

data class JapaneseFood(
    val name:String?,
    val imageSrc:String?
)

data class Chicken(
    val name:String?,
    val imageSrc:String?
)