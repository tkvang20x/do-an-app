package com.example.do_an_app.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.do_an_app.R
import com.example.do_an_app.adapter.BooksAdapter
import com.example.do_an_app.adapter.ItemBookVoucherCreateAdapter
import com.example.do_an_app.callback.CallbackBookInCreateVC
import com.example.do_an_app.databinding.FragmentCreateVoucherBinding
import com.example.do_an_app.model.users.Data
import com.example.do_an_app.model.voucher.DataVoucherCreate
import com.example.do_an_app.model.voucher.ListIdBook
import com.example.do_an_app.viewmodel.VoucherViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.example.do_an_app.fragment.FragmentHome.Companion.code_user
import com.example.do_an_app.model.book.UpdateBook
import com.example.do_an_app.viewmodel.BookViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentCreateVoucher: Fragment(), CallbackBookInCreateVC {

    companion object {
        var list_book = arrayListOf<ListIdBook>()
    }

    private lateinit var binding: FragmentCreateVoucherBinding
    private lateinit var adapter: ItemBookVoucherCreateAdapter
    private lateinit var voucherViewModel: VoucherViewModel
    private lateinit var bookViewModel: BookViewModel
    private val myCalendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateVoucherBinding.inflate(inflater, container, false)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bnv_view)
        view.visibility = View.VISIBLE
        binding.tvViewVoucher.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentCreateVoucher_to_fragmentVoucher)
        }

        val date =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel()
            }

        binding.txtBirthDay.setOnClickListener {
            DatePickerDialog(
                this.requireContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        adapter = ItemBookVoucherCreateAdapter(list_book, this)
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager =
            LinearLayoutManager(FragmentHome().context, LinearLayoutManager.VERTICAL, false)


        binding.btnAddVoucher.setOnClickListener {
            if(list_book.size == 0){
                Toast.makeText(requireContext(), "Danh sách phiếu mượn không được trống!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else if (binding.txtBirthDay.text.toString().trim().length == 0){
                Toast.makeText(requireContext(), "Ngày hẹn trả không được trống!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else{
                voucherViewModel = VoucherViewModel()
                voucherViewModel.postVoucher(
                    DataVoucherCreate(list_book, binding.txtBirthDay.text.toString(),code_user, "")
                )
                voucherViewModel.dataDetailVoucher.observe(viewLifecycleOwner) {
                    if (it?.status == 201){
                        Toast.makeText(requireContext(), "Tạo phiếu mượn thành công!", Toast.LENGTH_LONG).show()
                        list_book.clear()
                        findNavController().navigate(R.id.action_fragmentCreateVoucher_to_fragmentVoucher)
                    }else{
                        Toast.makeText(requireContext(), "Tạo phiếu mượn thất bại!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.imgScanQR.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentCreateVoucher_to_fragmentQrCode)
        }



        return binding.root
    }

    override fun onClick(voucher: ListIdBook, index: Int) {
        val bundle = Bundle()
        bundle.putString("code_id", voucher.code_id)
        bundle.putInt("index", index)
        bundle.putString("message", "create_voucher")
        findNavController().navigate(R.id.action_fragmentCreateVoucher_to_fragmentDetailBook, bundle)
    }

    override fun onLongClick(groups: ListIdBook, index: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Xóa sách khỏi phiếu mượn")
        builder.setMessage("Bạn có muốn xác nhận xóa?")
        builder.setNegativeButton(("No")) { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() }
        builder.setPositiveButton(("Yes")) { dialogInterface: DialogInterface, i: Int ->
            list_book.removeAt(index)
            adapter.notifyDataSetChanged()
            dialogInterface.dismiss()

            bookViewModel = BookViewModel()
            bookViewModel.updateBook(groups.code_id, UpdateBook("NEW", "READY", ""))


            val builder2 = AlertDialog.Builder(requireContext())
            builder2.setMessage("Xóa thành công!!!")
            builder2.show()
        }
        builder.show()
    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd-HH:mm:ss" // định dạng ngày
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.txtBirthDay.setText(sdf.format(myCalendar.time))
    }
}