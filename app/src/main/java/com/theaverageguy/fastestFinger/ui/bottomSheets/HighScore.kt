package com.theaverageguy.fastestFinger.ui.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theaverageguy.fastestFinger.databinding.HighScoreBinding
import com.theaverageguy.fastestFinger.ui.modelClasses.AppSharePreference


class HighScore : BottomSheetDialogFragment() {

    private var appSharePreference: AppSharePreference? = null
    private var _binding: HighScoreBinding? = null
    private val bind get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HighScoreBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAllComponents()
    }

    private fun initAllComponents() {
        appSharePreference =
            AppSharePreference(
                context
            )
        bind.highScore.text = "High Score : ${appSharePreference?.highScore}"
    }

}