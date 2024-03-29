package com.example.photogallery.api

import retrofit2.http.GET

interface FlickrApi {
    @GET(
        "services/rest/?method=flickr.interestingness.getList"+
                "&api_key=ee0b3ba9a13d40d09213a519094c6cc5" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"
    )
    suspend fun fetchPhotos(): FlickrResponse
}