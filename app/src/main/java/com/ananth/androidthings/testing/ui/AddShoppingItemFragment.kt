package com.ananth.androidthings.testing.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ananth.androidthings.R
import com.ananth.androidthings.databinding.FragmentAddShoppingItemBinding
import com.ananth.androidthings.testing.other.Status
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class AddShoppingItemFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_add_shopping_item) {

    private var _binding: FragmentAddShoppingItemBinding? = null
    private val binding get() = _binding

    lateinit var viewModel: ShoppingViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddShoppingItemBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        subscribeToObservers()

        binding?.btnAddShoppingItem?.setOnClickListener {
            viewModel.insertShoppingItem(
                binding?.etShoppingItemName?.text.toString(),
                binding?.etShoppingItemAmount?.text.toString(),
                binding?.etShoppingItemPrice?.text.toString()

            )
        }
        binding?.ivShoppingImage?.setOnClickListener {
            findNavController().navigate(AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment())
        }

        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setImageUrl("")
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun subscribeToObservers() {
        viewModel.imagesUrl.observe(viewLifecycleOwner) {
            binding?.ivShoppingImage?.let { it1 -> glide.load(it).into(it1) }
        }
        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        binding?.root?.let { it1 ->
                            Snackbar.make(
                                requireActivity(),
                                it1, "Added shopping item", Snackbar.LENGTH_LONG
                            ).show()
                        }
                        findNavController().popBackStack()
                    }

                    Status.ERROR -> {
                        binding?.root?.let { it1 ->
                            Snackbar.make(
                                requireActivity(),
                                it1, result.message ?: "Unknown error", Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }

                    Status.LOADING -> {

                    }

                }
            }
        }
    }

}