package com.google.mediapipe.examples.handlandmarker.fragment

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.mediapipe.examples.handlandmarker.HandLandmarkerHelper
import com.google.mediapipe.examples.handlandmarker.MainViewModel
import com.google.mediapipe.examples.handlandmarker.R
import com.google.mediapipe.examples.handlandmarker.databinding.FragmentCameraBinding
import com.google.mediapipe.tasks.vision.core.RunningMode
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.acos
import kotlin.math.sqrt

class CameraFragment : Fragment(), HandLandmarkerHelper.LandmarkerListener {

    companion object {
        private const val TAG = "HandLandmarkerDemo"
    }

    private var _fragmentCameraBinding: FragmentCameraBinding? = null
    private val fragmentCameraBinding
        get() = _fragmentCameraBinding!!

    private lateinit var handLandmarkerHelper: HandLandmarkerHelper
    private val viewModel: MainViewModel by activityViewModels()
    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var cameraFacing = CameraSelector.LENS_FACING_FRONT

    /** Blocking ML operations are performed using this executor */
    private lateinit var backgroundExecutor: ExecutorService

    private val allFrameVectors = mutableListOf<Float>()

    fun getAllFrameVectors(): List<Float> {
        return allFrameVectors.toList()
    }

    override fun onResume() {
        super.onResume()

        val fragmentContainerId = when {
            requireActivity().findViewById<View?>(R.id.order_fragment_container) != null -> R.id.order_fragment_container
            requireActivity().findViewById<View?>(R.id.inquiry_fragment_container) != null -> R.id.inquiry_fragment_container
            else -> null
        }

        if (fragmentContainerId != null) {
            if (!PermissionsFragment.hasPermissions(requireContext())) {
                Navigation.findNavController(requireActivity(), fragmentContainerId)
                    .navigate(R.id.action_camera_to_permissions)
            }
        } else {
            Log.e("CameraFragment", "FragmentContainerView ID를 찾을 수 없습니다.")
        }

        backgroundExecutor.execute {
            if (handLandmarkerHelper.isClose()) {
                handLandmarkerHelper.setupHandLandmarker()
            }
        }
    }


    override fun onPause() {
        super.onPause()
        if (this::handLandmarkerHelper.isInitialized) {
            viewModel.setMaxHands(handLandmarkerHelper.maxNumHands)
            viewModel.setMinHandDetectionConfidence(handLandmarkerHelper.minHandDetectionConfidence)
            viewModel.setMinHandTrackingConfidence(handLandmarkerHelper.minHandTrackingConfidence)
            viewModel.setMinHandPresenceConfidence(handLandmarkerHelper.minHandPresenceConfidence)
            viewModel.setDelegate(handLandmarkerHelper.currentDelegate)

            backgroundExecutor.execute { handLandmarkerHelper.clearHandLandmarker() }
        }
    }

    override fun onDestroyView() {
        _fragmentCameraBinding = null
        super.onDestroyView()
        backgroundExecutor.shutdown()
        backgroundExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentCameraBinding =
            FragmentCameraBinding.inflate(inflater, container, false)
        return fragmentCameraBinding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundExecutor = Executors.newSingleThreadExecutor()

        fragmentCameraBinding.viewFinder.post {
            setUpCamera()
        }

        backgroundExecutor.execute {
            handLandmarkerHelper = HandLandmarkerHelper(
                context = requireContext(),
                runningMode = RunningMode.LIVE_STREAM,
                minHandDetectionConfidence = viewModel.currentMinHandDetectionConfidence,
                minHandTrackingConfidence = viewModel.currentMinHandTrackingConfidence,
                minHandPresenceConfidence = viewModel.currentMinHandPresenceConfidence,
                maxNumHands = viewModel.currentMaxHands,
                currentDelegate = viewModel.currentDelegate,
                handLandmarkerHelperListener = this
            )
        }
    }

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            {
                cameraProvider = cameraProviderFuture.get()
                bindCameraUseCases()
            }, ContextCompat.getMainExecutor(requireContext())
        )
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun bindCameraUseCases() {
        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("카메라 초기화 실패")

        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(cameraFacing).build()

        preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(fragmentCameraBinding.viewFinder.display.rotation)
            .build()

        imageAnalyzer =
            ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(fragmentCameraBinding.viewFinder.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
                .also {
                    it.setAnalyzer(backgroundExecutor) { image ->
                        detectHand(image)
                    }
                }

        cameraProvider.unbindAll()

        try {
            camera = cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageAnalyzer
            )
            preview?.setSurfaceProvider(fragmentCameraBinding.viewFinder.surfaceProvider)
        } catch (exc: Exception) {
            Log.e(TAG, "Use case 연결 실패: ", exc)
        }
    }

    private fun detectHand(imageProxy: ImageProxy) {
        handLandmarkerHelper.detectLiveStream(
            imageProxy = imageProxy,
            isFrontCamera = cameraFacing == CameraSelector.LENS_FACING_FRONT
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        imageAnalyzer?.targetRotation =
            fragmentCameraBinding.viewFinder.display.rotation
    }

    override fun onResults(resultBundle: HandLandmarkerHelper.ResultBundle) {
        val results = resultBundle.results
        if (results.isNotEmpty()) {
            val result = results.first()
            val landmarkList = result.landmarks()
            if (landmarkList.isNotEmpty()) {
                for ((handIndex, landmarks) in landmarkList.withIndex()) {
                    val joint = Array(21) { FloatArray(3) }
                    for ((j, lm) in landmarks.withIndex()) {
                        joint[j][0] = lm.x()
                        joint[j][1] = lm.y()
                        joint[j][2] = lm.z()
                    }

                    val flattenCoords = joint.flatMap { listOf(it[0], it[1], it[2]) }
                    val angles = computeAngles(joint)
                    val frameVector = flattenCoords + angles.toList()

                    allFrameVectors.addAll(frameVector)
                }
                Log.i(TAG, "전체 누적 벡터 길이 = ${allFrameVectors.size}")
                if (allFrameVectors.size >= 78) {
                    val firstFrame = allFrameVectors.subList(0, 78)
                    Log.i(TAG, "첫 프레임 벡터 (0~77): $firstFrame")
                }
            }
        } else {
            Log.i(TAG, "리스트가 비어있음")
        }
    }


    override fun onError(error: String, errorCode: Int) {
        Log.e(TAG, "$error")
    }

    private fun computeAngles(joint: Array<FloatArray>): FloatArray {
        val v1Indices = intArrayOf(0,1,2,3,0,5,6,7,0,9,10,11,0,13,14,15,0,17,18,19)
        val v2Indices = intArrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)

        val v = Array(20) { FloatArray(3) }
        for (i in v.indices) {
            for (j in 0..2) {
                v[i][j] = joint[v2Indices[i]][j] - joint[v1Indices[i]][j]
            }
            val norm = sqrt(v[i].fold(0.0f) { acc, x -> acc + x * x })
            for (j in 0..2) {
                v[i][j] /= norm
            }
        }

        val angleIndicesA = intArrayOf(0,1,2,4,5,6,8,9,10,12,13,14,16,17,18)
        val angleIndicesB = intArrayOf(1,2,3,5,6,7,9,10,11,13,14,15,17,18,19)

        val angles = FloatArray(15)
        for (k in angleIndicesA.indices) {
            val a = v[angleIndicesA[k]]
            val b = v[angleIndicesB[k]]
            val dot = a[0]*b[0] + a[1]*b[1] + a[2]*b[2]
            val clamped = dot.coerceIn(-1f,1f)
            val angleRad = acos(clamped)
            angles[k] = Math.toDegrees(angleRad.toDouble()).toFloat()
        }
        return angles
    }
}
