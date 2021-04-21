package com.theaverageguy.fastestFinger.ui.bottomSheets

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theaverageguy.fastestFinger.R
import com.theaverageguy.fastestFinger.databinding.FragmentSettingBinding
import com.theaverageguy.fastestFinger.service.musicService
import com.theaverageguy.fastestFinger.modelClasses.AppSharePreference

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
        appSharePreference =
            AppSharePreference(
                context
            )
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
                appSharePreference?.ads = isChecked
            }
            R.id.music -> {

                appSharePreference?.music = isChecked
                if (isChecked) {
                    context?.startService(Intent(context, musicService::class.java))
                } else {
                    context?.stopService(Intent(context, musicService::class.java))
                }
            }
        }
    }
}