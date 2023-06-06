package com.example.do_an_app.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.do_an_app.LoginActivity
import com.example.do_an_app.MainActivity
import com.example.do_an_app.R
import com.example.do_an_app.databinding.FragmentProfileBinding
import com.example.do_an_app.fragment.FragmentHome.Companion.data_user
import com.example.do_an_app.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

class FragmentProfile: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bnv_view)
        view.visibility = View.VISIBLE

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.getUser()
        userViewModel.dataUser.observe(viewLifecycleOwner) {
            if (it != null) {
                data_user = it.data
                binding.user = it.data

                if (it.data.role === "STUDENT"){
                    binding.tvCourse.visibility = View.VISIBLE
                    binding.txtCourse.visibility = View.VISIBLE
                    binding.imgCourse.visibility = View.VISIBLE
                }else{
                    binding.tvSpecialized.visibility = View.VISIBLE
                    binding.txtSpecialized.visibility = View.VISIBLE
                    binding.imgSpecialize.visibility = View.VISIBLE
                    binding.txtDepartment.visibility = View.VISIBLE
                    binding.tvDepartment.visibility = View.VISIBLE
                    binding.imgDepartment.visibility = View.VISIBLE
                }
            }
        }

        binding.imgSetting.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Đăng xuất")
            builder.setMessage("Bạn Có Muốn Đăng ?")
            builder.setNegativeButton(
                "No"
            ) { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() }
            builder.setPositiveButton(
                "Yes"
            ) { dialogInterface: DialogInterface?, i: Int ->
                val intent = Intent()
                intent.setClass(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
            }

            builder.show()
        }

        binding.imgChangeAvt.setOnClickListener {
            checkPermission()
        }

        binding.tvUpdate.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentProfile_to_fragmentUpdateProfile)
        }

        binding.imgSetting.setOnClickListener {
            showPopupMenu(it)
        }

        return binding.root
    }

    private fun checkPermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                TedBottomPicker.with(activity).show { uri ->
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Thay Avatar")
                    builder.setMessage("Bạn Có Muốn Xác Nhận Thay Avatar?")
                    builder.setNegativeButton(
                        "No"
                    ) { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() }
                    builder.setPositiveButton(
                        "Yes"
                    ) { dialogInterface: DialogInterface?, i: Int ->
                        var bitmap: Bitmap? = null
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(
                                activity!!.contentResolver,
                                uri
                            )
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        binding.imgAvtUser.setImageBitmap(bitmap)
                        val multipartBody: MultipartBody.Part = getPath(uri)
                        userViewModel.uploadAvatar(data_user.code, multipartBody)
                        val builderSuccess =
                            AlertDialog.Builder(requireContext())
                        builderSuccess.setTitle("Thay Avatar")
                        builderSuccess.setMessage("Thay Avatar Thành Công")
                        builderSuccess.show()
                    }
                    builder.show()
                }
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(context, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .check()
    }
    fun getPath(uri: Uri): MultipartBody.Part {
        val file = File(uri.path)
        val reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
        return MultipartBody.Part.createFormData("avatar", file.name, reqFile)
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.menu_setting)
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.setting -> {
                    findNavController().navigate(R.id.action_fragmentProfile_to_fragmentChangePass)
                    true
                }
                R.id.logout -> {
                    val intent = Intent()
                    intent.setClass(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}