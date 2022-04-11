package com.sky.base

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sky.base.PermissionsActivity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goView(view: View) {
        try {
            checkPermissionAndStart(Intent(this, GLActivity::class.java))
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun goView1(view: View) {
        startActivity(Intent(this, GLActivity::class.java))
    }

    private fun checkPermissionAndStart(intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(PERMISSION_CAMERA) !== PackageManager.PERMISSION_GRANTED || checkSelfPermission(
                    PERMISSION_STORAGE
                ) !== PackageManager.PERMISSION_GRANTED || checkSelfPermission(PERMISSION_AUDIO) !== PackageManager.PERMISSION_GRANTED
            ) {
                // start Permissions activity
                val componentName = intent.component
                intent.setClass(this, PermissionsActivity::class.java)
                intent.putExtra(
                    PERMISSION_SUC_ACTIVITY, Class.forName(
                        componentName!!.className
                    )
                )
                startActivity(intent)
                return
            }
        }
        startActivity(intent)
    }

    private fun getVersionCode(): Int {
        val context = applicationContext
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            -1
        }
    }
}