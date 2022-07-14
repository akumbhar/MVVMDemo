package com.example.assignment.common

import com.tickaroo.tikxml.annotation.*


@Xml(name = "feed")
data class Album(
    @PropertyElement(name = "title")
    var title: String? = null,

    @Element(name = "entry")
    var albumList: List<Song>

)

@Xml
data class Song(

    @PropertyElement(name="id")
    var id: String? = null,
    @PropertyElement(name="title")
    var title: String? = null,
    @PropertyElement(name="im:name")
    var name: String? = null,
    @PropertyElement(name="im:price")
    var price: String ?= null,
    @PropertyElement(name="im:artist")
    var artist: String ?= null,
    @PropertyElement(name="im:image")
    var imageUrl: String ?= null
)

