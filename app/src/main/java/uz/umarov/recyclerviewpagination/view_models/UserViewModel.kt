package uz.umarov.recyclerviewpagination.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import uz.umarov.recyclerviewpagination.paging3.UserDataPagingSource

class UserViewModel : ViewModel() {

    val flow = Pager(
        PagingConfig(20)
    ) {
        UserDataPagingSource()
    }.flow.cachedIn(viewModelScope)

}