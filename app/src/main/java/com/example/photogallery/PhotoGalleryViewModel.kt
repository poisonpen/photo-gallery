package com.example.photogallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photogallery.api.GalleryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "PHOTOGROWLERY--VIEWMODEL";
class PhotoGalleryViewModel: ViewModel() {
    private val photoRepository = PhotoRepository()
    private val preferencesRepository = PreferencesRepository.get()

    private val _uiState: MutableStateFlow<PhotoGalleryUiState> =
        MutableStateFlow(PhotoGalleryUiState())
    val uiState: StateFlow<PhotoGalleryUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.storedQuery.collectLatest { storedQuery ->
                try {
                    val items = fetchGalleryItems(storedQuery)
                    _uiState.update { oldState->
                        oldState.copy(
                            images = items,
                            query = storedQuery
                        )
                    }
                } catch (ex: Exception) {
                    Log.e(TAG, "FAILED TO FETCH GALLERY ITEMS", ex)
                }
            }
        }
    }
    fun setQuery(query: String) {
       // viewModelScope.launch { _galleryItems.value = fetchGalleryItems(query) }
        viewModelScope.launch { preferencesRepository.setStoredQuery(query) }
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

    data class PhotoGalleryUiState(
        val images: List<GalleryItem> =listOf(),
        val query: String = "",
    )
}