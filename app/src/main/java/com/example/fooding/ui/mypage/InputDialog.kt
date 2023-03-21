package com.example.fooding.ui.mypage

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.fooding.R
import com.example.fooding.databinding.DialogInputBinding

class InputDialog(private val listener: NoticeDialogListener) : DialogFragment() {

    lateinit var currentWeight: EditText
    lateinit var goalWeight: EditText
    lateinit var goalCalories: EditText

    private var _binding: DialogInputBinding? = null
    private val binding get() = _binding!!


    interface NoticeDialogListener {
        fun onDialogPositiveClick(currentWeight: String, goalWeight: String, goalCalories: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        _binding = DialogInputBinding.inflate(inflater)
        currentWeight = binding.textInputCurrentWt
        goalWeight = binding.textInputGoalWt
        goalCalories = binding.textInputGoalCalories

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
                .setPositiveButton(
                    R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogPositiveClick(
                            currentWeight.text.toString(),
                            goalWeight.text.toString(),
                            goalCalories.text.toString()
                        )
                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}