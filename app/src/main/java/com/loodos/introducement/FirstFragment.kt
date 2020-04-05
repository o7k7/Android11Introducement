package com.loodos.introducement

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var btnOpen: Button
    private lateinit var btnDownload: Button

    private val manager: SplitInstallManager by lazy { SplitInstallManagerFactory.create(activity) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        init(view)
        initDynamicFeature()
        return view
    }

    private fun init(v: View) {
        btnOpen = v.findViewById(R.id.btn_open)
        btnDownload = v.findViewById(R.id.btn_download)
    }

    private fun initDynamicFeature() {
        updateDynamicFeatureButtonState()
        btnDownload.setOnClickListener {
            val request = SplitInstallRequest.newBuilder()
                .addModule(context!!.getString(R.string.title_dynamicfeature_test))
                .build()

            manager.registerListener {
                when (it.status()) {
                    SplitInstallSessionStatus.DOWNLOADING -> showToast("Downloading feature")
                    SplitInstallSessionStatus.INSTALLED -> {
                        showToast("Feature ready to be used")
                        updateDynamicFeatureButtonState()
                    }
                    else -> { // no - op
                    }
                }
            }
            manager.startInstall(request)
        }

        btnOpen.setOnClickListener {
            Intent().apply {
                setClassName(BuildConfig.APPLICATION_ID, "com.loodos.dynamicfeature_test.AdditionalActivity")
                startActivity(this)
            }
        }
    }

    private fun updateDynamicFeatureButtonState() {
        btnOpen.isEnabled = manager.installedModules.contains(context!!.getString(R.string.title_dynamicfeature_test))
    }

    private fun showToast(message: String) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
    }
}
