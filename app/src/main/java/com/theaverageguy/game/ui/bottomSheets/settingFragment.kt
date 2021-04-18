package com.theaverageguy.game.ui.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theaverageguy.game.R
import com.theaverageguy.game.databinding.FragmentSettingBinding
import com.theaverageguy.game.utils.AppSharePreference

class settingFragment : BottomSheetDialogFragment(), CompoundButton.OnCheckedChangeListener {

    private var appSharePreference: AppSharePreference? = null
    private var _binding: FragmentSettingBinding? = null
    private val bind get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAllComponents()
    }

    private fun initAllComponents() {
        appSharePreference = AppSharePreference(context)
        bind.ads.setOnCheckedChangeListener(this)
        bind.music.setOnCheckedChangeListener(this)

        if (appSharePreference?.ads == true) {
            bind.ads.isChecked = true
        }

        if (appSharePreference?.music == true) {
            bind.music.isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.ads -> {
                //showToast(buttonView.context, isChecked.toString())
                appSharePreference?.ads = isChecked
            }
            R.id.music -> {
                //showToast(buttonView.context, isChecked.toString())
                appSharePreference?.music = isChecked
            }
        }
    }
}