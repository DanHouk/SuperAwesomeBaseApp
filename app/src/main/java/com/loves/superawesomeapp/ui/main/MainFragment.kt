package com.loves.superawesomeapp.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.loves.superawesomeapp.R
import com.loves.superawesomeapp.SuperAwesomeApp
import com.loves.superawesomeapp.dagger.ViewModelFactory
import com.loves.superawesomeapp.databinding.FragmentMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalTime
import javax.inject.Inject

class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val disposables = CompositeDisposable()

    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    @Inject lateinit var factory: ViewModelFactory
    private val viewModel by viewModels<MainViewModel> { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        SuperAwesomeApp.appComponent.inject(this)

        initLocationPermissionRequest()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
        disposables.dispose()
    }

    private fun fetchData() {
        disposables.add(viewModel.fetchSunriseAndSunset(39.626846, -101.196905)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ response ->
                val responseResult = response.results
                val timeNow = LocalTime.now()

                if (timeNow.isAfter(responseResult.sunrise) && timeNow.isBefore(responseResult.sunset))
                    binding.mainFragmentIv.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.day_time_pic
                        )
                    )
                else
                    binding.mainFragmentIv.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.night_time_pic
                        )
                    )

                binding.mainFragmentMessageTv.text =
                    getString(R.string.length_of_day, response.results.sunrise.toString())
            }
        )
    }

    private fun initLocationPermissionRequest() {
        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions.getOrDefault(ACCESS_FINE_LOCATION, false) ||
                permissions.getOrDefault(ACCESS_COARSE_LOCATION, false)) {

            } else {

            }
        }
    }

    private fun launchPermissionCheck() {
        locationPermissionRequest.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
    }
}