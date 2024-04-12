package uz.umarov.recyclerviewpagination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.umarov.recyclerviewpagination.adapters.UserPaging3Adapter
import uz.umarov.recyclerviewpagination.databinding.ActivitySecondBinding
import uz.umarov.recyclerviewpagination.view_models.UserViewModel
import kotlin.coroutines.CoroutineContext

class SecondActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userPaging3Adapter: UserPaging3Adapter

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userPaging3Adapter = UserPaging3Adapter()
        binding.rv.adapter = userPaging3Adapter

        launch {
            userViewModel.flow.collect {
                userPaging3Adapter.submitData(it)
            }
        }

    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}