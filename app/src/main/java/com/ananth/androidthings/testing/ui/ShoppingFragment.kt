package com.ananth.androidthings.testing.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ananth.androidthings.R
import com.ananth.androidthings.databinding.ShoppingFragmentBinding
import com.ananth.androidthings.testing.adapters.ShoppingItemAdapter
import com.google.android.material.snackbar.Snackbar
import java.time.Duration
import javax.inject.Inject

class ShoppingFragment @Inject constructor(
    val shoppingItemAdapter: ShoppingItemAdapter,
    var viewModel : ShoppingViewModel? = null
) : Fragment(R.layout.shopping_fragment) {


    private var _binding: ShoppingFragmentBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ShoppingFragmentBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModel?:ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        subscribeToObserver()
        setupRecyclerView()
        binding?.fabAddShoppingItem?.setOnClickListener {
            findNavController().navigate(ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0, LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = shoppingItemAdapter.shoppingItems[pos]
            viewModel?.deleteShoppingItem(item)
            Snackbar.make(requireView(), "Successfully deleted", Snackbar.LENGTH_LONG).apply {
                setAction("Undo") {
                    viewModel?.insertShoppingItemIntoDB(item)
                    show()
                }
            }
        }
    }

    private fun subscribeToObserver() {
        viewModel?.shoppingItems?.observe(viewLifecycleOwner) {
            shoppingItemAdapter.shoppingItems = it
        }
        viewModel?.totalPrice?.observe(viewLifecycleOwner) {
            val price = it ?: 0f
            val priceText = "Total Price: $price"
            binding?.tvShoppingItemPrice?.text= priceText
        }
    }

    private fun setupRecyclerView() {
        binding?.rvShoppingItems.let { rv ->
            rv?.apply {
                adapter = shoppingItemAdapter
                layoutManager = LinearLayoutManager(requireContext())
                ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
            }

        }
    }
}