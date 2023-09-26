package com.ananth.androidthings.testing.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ananth.androidthings.R
import com.ananth.androidthings.testing.data.local.ShoppingItem
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ShoppingItemAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingItemViewHolder>() {

    class ShoppingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var shoppingItems: List<ShoppingItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {

        return ShoppingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_shopping, parent, false)
        )
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val shoppingItem = shoppingItems[position]
        val tvName = holder.itemView.findViewById<TextView>(R.id.tvName);
        val tvShoppingItemAmount = holder.itemView.findViewById<TextView>(R.id.etShoppingItemAmount);
        val tvShoppingItemPrice = holder.itemView.findViewById<TextView>(R.id.tvShoppingItemPrice);
        holder.itemView.apply {
            glide.load(shoppingItem.imageUrl).into(this.findViewById(R.id.ivShoppingImage))

            tvName.text = shoppingItem.name
            val amountText = "${shoppingItem.amount}x"
            tvShoppingItemAmount.text = amountText
            val priceText = "${shoppingItem.price}€"
            tvShoppingItemPrice.text = priceText
        }
    }

    override fun getItemCount(): Int {
        return shoppingItems.size
    }
}