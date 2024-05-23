package com.example.photogallery.api

import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
//    @GET(
//        "services/rest/?method=flickr.interestingness.getList"+
//                "&api_key=ee0b3ba9a13d40d09213a519094c6cc5" +
//                "&format=json" +
//                "&nojsoncallback=1" +
//                "&extras=url_s"
//    )
   @GET("services/rest/?method=flickr.interestingness.getList")
    suspend fun fetchPhotos(): FlickrResponse

    @GET("services/rest/?method=flickr.photos.search")
    suspend fun searchPhotos(@Query("text") query: String): FlickrResponse

}