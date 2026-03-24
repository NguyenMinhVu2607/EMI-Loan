package com.example.emiloan.ui.settings

import com.example.emiloan.R
import com.example.emiloan.base.BaseFragment
import com.example.emiloan.base.gone
import com.example.emiloan.databinding.FragmentSetiingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSetiingsBinding>(FragmentSetiingsBinding::inflate) {
    override fun initView() {
        binding.toolbar.btnBack.gone()
        binding.toolbar.btnDelete.gone()
        binding.toolbar.tvTitle.text = "Preferences"
        binding.toolbar.tvDescription.text = "Settings"

        binding.itemCurency.iconSetting.setImageResource(R.drawable.ic_currency)
        binding.itemLanguage.iconSetting.setImageResource(R.drawable.ic_language)
        binding.itemPrivacy.iconSetting.setImageResource(R.drawable.ic_privacy)
        binding.itemTerm.iconSetting.setImageResource(R.drawable.ic_term)
        binding.itemShare.iconSetting.setImageResource(R.drawable.ic_share)

        binding.itemCurency.title.text = "Currency"
        binding.itemLanguage.title.text = "Language"
        binding.itemPrivacy.title.text = "Privacy"
        binding.itemTerm.title.text = "Term of Use"
        binding.itemShare.title.text = "Share to friends"

        binding.itemCurency.desctiption.text = "Symbol shown in all results"
        binding.itemLanguage.desctiption.text = "Used throughout the app"
        binding.itemPrivacy.desctiption.gone()
        binding.itemTerm.desctiption.gone()
        binding.itemShare.desctiption.gone()
        binding.itemPrivacy.tvUnit.gone()
        binding.itemTerm.tvUnit.gone()
        binding.itemShare.tvUnit.gone()
    }

    override fun initData() {

    }

    override fun initListener() {
        binding.itemCurency.root.setOnClickListener {

        }
        binding.itemLanguage.root.setOnClickListener {

        }
        binding.itemPrivacy.root.setOnClickListener {

        }
        binding.itemTerm.root.setOnClickListener {

        }
        binding.itemShare.root.setOnClickListener {

        }

    }

}