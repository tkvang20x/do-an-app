package com.example.do_an_app.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.do_an_app.R
import com.example.do_an_app.databinding.FragmentQrcodeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentQrCode : Fragment() {

    private lateinit var binding: FragmentQrcodeBinding
    private lateinit var codeScanner: CodeScanner
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQrcodeBinding.inflate(inflater, container, false)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bnv_view)
        view.visibility = View.GONE

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {

            requireActivity().requestPermissions(arrayOf(Manifest.permission.CAMERA), 123)
        } else {
            startScanning()
        }


        return binding.root
    }

    private fun startScanning() {
        val scannerView: CodeScannerView = binding.scannerView

        codeScanner = CodeScanner(requireContext(), scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                Toast.makeText(
                    requireContext(),
                    "Scanner Result: ${it.text.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
                val bundel = Bundle()
                bundel.putString("code_id", it.text)
                bundel.putString("message", "scan_qr")
                findNavController().navigate(R.id.action_fragmentQrCode_to_fragmentDetailBook, bundel)
            }

        }

        codeScanner.errorCallback = ErrorCallback {
            activity?.runOnUiThread {
                Toast.makeText(requireContext(), "Error: ${it.toString()} !!!", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Camera is Granted!", Toast.LENGTH_SHORT).show()
                startScanning()
            } else {
                Toast.makeText(requireContext(), "Camera is Denied   !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        if (::codeScanner.isInitialized) {
            codeScanner.releaseResources()
        }
        super.onPause()
    }
}