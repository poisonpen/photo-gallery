package com.example.photogallery

import androidx.fragment.app.Fragment
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photogallery.api.FlickrApi
import com.example.photogallery.databinding.FragmentPhotoGalleryBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

class PhotoGalleryFragment : Fragment() {
    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding

        get() = checkNotNull(_binding) {
            "Connot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoGalleryBinding.inflate(inflater, container, false)
        binding.photoGrid.layoutManager = GridLayoutManager(context, 3)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.flickr.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val flickrApi: FlickrApi = retrofit.create<FlickrApi>()

        viewLifecycleOwner.lifecycleScope.launch {
          try {
              val response = PhotoRepository().fetchPhotos();
              Log.d("REASONING", "Answer to Problem: $response")
          } catch (ex: Exception) {
            Log.e("Erring Exception", "Failed to fetch galelry items", ex);
        }
          }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}