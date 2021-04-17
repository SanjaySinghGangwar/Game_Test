package com.theaverageguy.game.ui.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theaverageguy.game.R
import com.theaverageguy.game.databinding.LevelBinding
import com.theaverageguy.game.utils.utils.showToast


class level : BottomSheetDialogFragment(), View.OnClickListener {

    private var _binding: LevelBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val bind get() = _binding!!

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
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.easy -> {
                showToast(v.context, "Easy")
            }
            R.id.medium -> {
                showToast(v.context, "medium")
            }
            R.id.hard -> {
                showToast(v.context, "hard")
            }
        }
    }
}