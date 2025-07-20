/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.mediapipe.examples.handlandmarker.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.mediapipe.examples.handlandmarker.R
import kotlinx.coroutines.launch

private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

class PermissionsFragment : Fragment() {

    companion object {
        private const val TAG = "PermissionsFragment"
        
        /** Convenience method used to check if all permissions required by this app are granted */
        fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            Log.d(TAG, "권한 요청 결과: $isGranted")
            if (isGranted) {
                Toast.makeText(
                    context,
                    "카메라 권한이 허용되었습니다",
                    Toast.LENGTH_SHORT
                ).show()
                // 약간의 지연을 두고 카메라로 이동
                lifecycleScope.launch {
                    kotlinx.coroutines.delay(500)
                    navigateToCamera()
                }
            } else {
                Toast.makeText(
                    context,
                    "카메라 권한이 거부되었습니다. 앱을 사용하려면 권한이 필요합니다.",
                    Toast.LENGTH_LONG
                ).show()
                // 권한이 거부된 경우에도 카메라로 이동 시도 (사용자가 수동으로 권한을 허용했을 수 있음)
                lifecycleScope.launch {
                    kotlinx.coroutines.delay(1000)
                    if (hasPermissions(requireContext())) {
                        navigateToCamera()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "PermissionsFragment onCreate 시작")
        
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) -> {
                Log.d(TAG, "이미 카메라 권한이 있습니다. 카메라로 이동합니다.")
                navigateToCamera()
            }
            else -> {
                Log.d(TAG, "카메라 권한을 요청합니다.")
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

    private fun navigateToCamera() {
        Log.d(TAG, "navigateToCamera 호출됨")
        lifecycleScope.launch {
            try {
                val containerId = when {
                    requireActivity().findViewById<View?>(R.id.order_fragment_container) != null -> {
                        Log.d(TAG, "order_fragment_container 찾음")
                        R.id.order_fragment_container
                    }
                    requireActivity().findViewById<View?>(R.id.inquiry_fragment_container) != null -> {
                        Log.d(TAG, "inquiry_fragment_container 찾음")
                        R.id.inquiry_fragment_container
                    }
                    else -> {
                        Log.e(TAG, "FragmentContainerView를 찾을 수 없습니다.")
                        return@launch
                    }
                }

                Log.d(TAG, "카메라 프래그먼트로 네비게이션 시작")
                Navigation.findNavController(requireActivity(), containerId)
                    .navigate(R.id.action_permissions_to_camera)
                Log.d(TAG, "카메라 프래그먼트로 네비게이션 완료")
            } catch (e: Exception) {
                Log.e(TAG, "카메라로 네비게이션 중 오류 발생", e)
            }
        }
    }
}
