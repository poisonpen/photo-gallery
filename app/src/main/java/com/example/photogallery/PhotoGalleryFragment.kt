package com.example.photogallery

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photogallery.api.FlickrApi
import com.example.photogallery.databinding.FragmentPhotoGalleryBinding
import retrofit2.Retrofit
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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.flickr.com")
            .build()
        val flickrApi: FlickrApi = retrofit.create<FlickrApi>()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}