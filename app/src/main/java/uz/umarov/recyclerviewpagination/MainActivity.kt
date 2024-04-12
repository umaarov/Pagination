package uz.umarov.recyclerviewpagination

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.umarov.recyclerviewpagination.adapters.UserPaginationAdapter
import uz.umarov.recyclerviewpagination.databinding.ActivityMainBinding
import uz.umarov.recyclerviewpagination.models.UserData
import uz.umarov.recyclerviewpagination.networking.ApiClient
import uz.umarov.recyclerviewpagination.networking.ApiService
import uz.umarov.recyclerviewpagination.pagination.PaginationScrollListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var apiService: ApiService
    private lateinit var userPaginationAdapter: UserPaginationAdapter

    private var currentPage = 1
    private var TOTAL_PAGES = 0
    private var isLastPage = false
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiService = ApiClient.getRetrofit().create(ApiService::class.java)

        val linearLayoutManager = LinearLayoutManager(this)
        binding.rv.layoutManager = linearLayoutManager
        userPaginationAdapter = UserPaginationAdapter()
        binding.rv.adapter = userPaginationAdapter

        loadFirstPage()
        binding.rv.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                currentPage++
                isLoading = true
                loadNextPage()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }


        })

    }

    private fun loadNextPage() {
        apiService.getUsers(currentPage).enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    userPaginationAdapter.removeLoadingFooter()
                    isLoading = false

                    userPaginationAdapter.addAll(response.body()?.data ?: emptyList())

                    if (currentPage != TOTAL_PAGES) {
                        userPaginationAdapter.addLoadingFooter()
                    } else isLastPage = true
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {

            }


        })
    }

    private fun loadFirstPage() {
        apiService.getUsers().enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    binding.progress.visibility = View.GONE
                    TOTAL_PAGES = response.body()?.totalPages ?: 0
                    userPaginationAdapter.addAll(response.body()?.data ?: emptyList())

                    if (currentPage <= TOTAL_PAGES) {
                        userPaginationAdapter.addLoadingFooter()
                    } else {
                        isLastPage = true
                    }
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {

            }


        })
    }

}