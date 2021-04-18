package com.theaverageguy.fastestFinger.ui.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theaverageguy.fastestFinger.databinding.RulesBinding


class Rules : BottomSheetDialogFragment() {

    private var _binding: RulesBinding? = null
    private val bind get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RulesBinding.inflate(inflater, container, false)
        return bind.root
    }
}