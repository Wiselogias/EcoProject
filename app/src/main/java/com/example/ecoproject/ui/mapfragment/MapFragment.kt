package com.example.ecoproject.ui.mapfragment

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.ecoproject.R
import com.example.ecoproject.common.mvvm.BaseFragment
import com.example.ecoproject.databinding.MapFragmentBinding
import com.example.ecoproject.domain.entities.PointEntity
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.utils.UIUtils.collectOnLifeCycle
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.math.abs


class MapFragment : BaseFragment<MainActivity>() {
    @Inject
    lateinit var viewModel: MapViewModel

    private lateinit var binding: MapFragmentBinding

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        runBlocking {
            viewModel.lat.emit(it.latitude())
            viewModel.lon.emit(it.longitude())
        }
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {

        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }
    private lateinit var mapView: MapView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MapFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapView
        onMapReady()

        val pointBitmap =
            AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_nature_24)
                ?.toBitmap(128, 128, Bitmap.Config.ARGB_8888)

        viewModel.points.collectOnLifeCycle(this) { points ->
            mapView.annotations.apply {
                cleanup()
                val manager = createPointAnnotationManager()
                points.forEach { point ->
                    pointBitmap?.let { icon ->
                        val marker = manager.create(
                            PointAnnotationOptions()
                                .withIconImage(icon)
                                .withPoint(Point.fromLngLat(point.lon, point.lat))
                        )
                        manager.addClickListener {
                            if (marker.id == it.id) {
                                onPointClick(point)
                                true
                            } else false
                        }
                    }

                }
            }
        }
    }

    private fun onPointClick(point: PointEntity) {
        findNavController().navigate(
            R.id.action_action_map_to_pointFragment,
            bundleOf("id" to point.id)
        )
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
            setupGesturesListener()
        }
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        mapView.camera.apply {
            addCameraZoomChangeListener {
                runBlocking {
                    val value = abs((it - 0.5) * (1 - 10000) / (22 - 0.5) + 10000)
                    viewModel.radius.emit(value)
                }
            }
        }
        mapView.location.apply {
            updateSettings {
                enabled = true
            }
            addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        }
    }
}