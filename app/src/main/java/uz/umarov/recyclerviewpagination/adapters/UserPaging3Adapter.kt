package uz.umarov.recyclerviewpagination.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.umarov.recyclerviewpagination.databinding.UserItemBinding
import uz.umarov.recyclerviewpagination.models.Data

class UserPaging3Adapter : PagingDataAdapter<Data, UserPaging3Adapter.DataVh>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    inner class DataVh(private val userItemBinding: UserItemBinding) :
        RecyclerView.ViewHolder(userItemBinding.root) {

        fun onBind(data: Data) {
            userItemBinding.apply {
                Picasso.get().load(data.avatar).into(avatar)
                fullName.text = data.firstName + " " + data.lastName
                email.text = data.email
            }
        }

    }

    override fun onBindViewHolder(holder: DataVh, position: Int) {

        val item = getItem(position)
        if (item != null) {
            holder.onBind(item)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataVh {
        return DataVh(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}