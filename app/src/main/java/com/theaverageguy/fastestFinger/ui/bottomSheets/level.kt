package com.theaverageguy.fastestFinger.ui.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theaverageguy.fastestFinger.R
import com.theaverageguy.fastestFinger.databinding.LevelBinding
import com.theaverageguy.fastestFinger.ui.modelClasses.AppSharePreference
import com.theaverageguy.fastestFinger.utils.utils.showToast


class level : BottomSheetDialogFragment(), View.OnClickListener {

    private var _binding: LevelBinding? = null
    private val bind get() = _binding!!
    private var appSharePreference: AppSharePreference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LevelBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAllComponents()
    }

    private fun initAllComponents() {
        bind.easy.setOnClickListener(this)
        bind.medium.setOnClickListener(this)
        bind.hard.setOnClickListener(this)
        appSharePreference =
            AppSharePreference(
                context
            )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.easy -> {
                appSharePreference?.shuffle = 2000
                appSharePreference?.grey = 1000
                appSharePreference?.checkLife = 2000
                dismiss()
                showToast(context!!, "Easy is selected")
            }
            R.id.medium -> {
                appSharePreference?.shuffle = 1500
                appSharePreference?.grey = 750
                appSharePreference?.checkLife = 1500
                dismiss()
                showToast(context!!, "Medium is selected")
            }
            R.id.hard -> {
                appSharePreference?.shuffle = 1000
                appSharePreference?.grey = 500
                appSharePreference?.checkLife = 1000
                dismiss()
                showToast(context!!, "Hard is selected")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}