package com.dpfht.tmdbmvi.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dpfht.tmdbmvi.databinding.FragmentErrorMessageDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ErrorMessageDialogFragment: BottomSheetDialogFragment() {

  private lateinit var binding: FragmentErrorMessageDialogBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentErrorMessageDialogBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    isCancelable = false

    val args = ErrorMessageDialogFragmentArgs.fromBundle(requireArguments())
    val message = args.message

    binding.tvMessage.text = message

    binding.btnOk.setOnClickListener {
      dismiss()
    }
  }
}
