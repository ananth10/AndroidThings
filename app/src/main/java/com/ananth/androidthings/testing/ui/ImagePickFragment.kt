package com.ananth.androidthings.testing.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ananth.androidthings.R
import com.ananth.androidthings.databinding.FragmentImagePickBinding
import com.ananth.androidthings.testing.adapters.ImageAdapter
import com.ananth.androidthings.testing.other.Constants.GRID_SPAN_COUNT
import javax.inject.Inject

class ImagePickFragment @Inject constructor(
     val imageAdapter: ImageAdapter
) : Fragment() {

    private var _binding: FragmentImagePickBinding? = null
    private val binding get() = _binding

    lateinit var viewModel: ShoppingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImagePickBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        setUpRecyclerView()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setImageUrl(it)
        }
    }

    private fun setUpRecyclerView() {
        binding?.rvImages?.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(),GRID_SPAN_COUNT)
        }
    }
}