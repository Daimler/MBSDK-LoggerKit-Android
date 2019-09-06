package com.daimler.mbloggerkit.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbloggerkit.shake.LogDialogFragment
import com.daimler.mbloggerkit.shake.LogOverlay
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MBLoggerKit.e("Mail: test.test@test.de")
        main_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = MBLoggerKit.i(s.toString())

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.show_log -> {
                LogDialogFragment.show(supportFragmentManager, LogOverlay.Order.CHRONOLOGICAL_DESCENDING)
            }
            R.id.show_chooser -> {
                LogOverlay.showChooser(this, LogOverlay.Order.CHRONOLOGICAL_DESCENDING)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
