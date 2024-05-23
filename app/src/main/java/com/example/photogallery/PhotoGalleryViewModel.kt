package com.example.photogallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photogallery.api.GalleryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "PHOTOGROWLERY--VIEWMODEL";
class PhotoGalleryViewModel: ViewModel() {
    private val photoRepository = PhotoRepository()

    private val _galleryItems: MutableStateFlow<List<GalleryItem>> =
        MutableStateFlow(emptyList())
    val galleryItems: StateFlow<List<GalleryItem>>
        get() = _galleryItems.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val items = fetchGalleryItems("")
                 //val items = photoRepository.fetchPhotos()
                Log.d(TAG, "ITEMS RECEiVED: $items")
                _galleryItems.value = items
            } catch (ex: Exception) {
                Log.e(TAG, "FAILED TO FETCH GALLERY ITEMS", ex)
            }
        }
    }
    fun setQuery(query: String) {
        viewModelScope.launch { _galleryItems.value = fetchGalleryItems(query) }
    }

    private suspend fun fetchGalleryItems(query: String): List<GalleryItem> {
        return if (query.isNotEmpty()) {
            Log.d(TAG, "NOT EMPTY QUERY")
            photoRepository.searchPhotos(query)

        } else {
            Log.d(TAG, "EMPTY QUERY")
            photoRepository.fetchPhotos()

        }
    }

//    fun setQuery(query: String) {
//        viewModelScope.launch { _galleryItems.value = fetchGalleryItems(query) }
//    }
//    private suspend fun fetchGalleryItems(query: String): List<GalleryItem> {
//        return if (query.isNotEmpty()) {
//            else {
//                photoRepository.fetchPhotos()
//            }
//        }
//    }
}