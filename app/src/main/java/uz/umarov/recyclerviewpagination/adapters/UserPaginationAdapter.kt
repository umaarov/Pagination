package uz.umarov.recyclerviewpagination.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.umarov.recyclerviewpagination.databinding.LoadItemBinding
import uz.umarov.recyclerviewpagination.databinding.UserItemBinding
import uz.umarov.recyclerviewpagination.models.Data

class UserPaginationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val LOADING = 0
    private val ITEM = 1
    private var isLoadingAdded = false
    private var userList = ArrayList<Data>()

    fun addAll(list: List<Data>) {
        list.forEach {
            add(it)
        }
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Data())
    }

    private fun add(data: Data) {
        userList.add(data)
        notifyItemInserted(userList.size - 1)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = userList.size - 1
        userList.removeAt(position)
        notifyItemRemoved(position)

    }

    inner class DataVh(var userItemBinding: UserItemBinding) :
        RecyclerView.ViewHolder(userItemBinding.root) {

        fun onBind(data: Data) {
            userItemBinding.apply {
                Picasso.get().load(data.avatar).into(avatar)
                fullName.text = data.firstName + " " + data.lastName
                email.text = data.email
            }

        }

    }


    inner class LoadVh(var loadItemBinding: LoadItemBinding) :
        RecyclerView.ViewHolder(loadItemBinding.root) {

        fun onBind() {
            loadItemBinding.progress.visibility = View.VISIBLE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM) {
            DataVh(
                UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            LoadVh(LoadItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM) {
            val dataVh = holder as DataVh
            dataVh.onBind(userList[position])
        } else {
            val loadVh = holder as LoadVh
            loadVh.onBind()
        }
    }

    override fun getItemCount(): Int = userList.size

    override fun getItemViewType(position: Int): Int {
        if (position == userList.size - 1 && isLoadingAdded) return LOADING
        return ITEM

    }

}
