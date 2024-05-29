package jp.co.taika.kyoueihtapplication.ui.theme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.ViewfinderView
import jp.co.taika.kyoueihtapplication.R
import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface

import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class QRCodeCaptureActivity : CaptureActivity() {
    private lateinit var capture: CaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_qrcode)




        requestCameraPermission()

        val barcodeScannerView = findViewById<com.journeyapps.barcodescanner.CompoundBarcodeView>(R.id.qrcode_reader)
        disableLaser(barcodeScannerView!!)



    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            initializeScanner()
        }
    }

    private fun initializeScanner() {
        val barcodeScannerView = findViewById<com.journeyapps.barcodescanner.CompoundBarcodeView>(R.id.qrcode_reader)
        capture = CaptureManager(this, barcodeScannerView)
        capture.initializeFromIntent(intent, null)
        capture.decode()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeScanner()
            } else {
                showPermissionDeniedDialog()
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("カメラアクセスが必要")
            .setMessage("この機能を使用するにはカメラのパーミッションが必要です。設定からアクセスを許可してください。")
            .setPositiveButton("設定") { dialog, which ->
                openAppSettings()
            }
            .setNegativeButton("キャンセル") { dialog, which -> dialog.dismiss() }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = android.net.Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        findViewById<com.journeyapps.barcodescanner.CompoundBarcodeView>(R.id.qrcode_reader)?.resume()
        var back_btn = findViewById<Button>(R.id.modoru)

        back_btn.setOnClickListener {
            val intent = Intent(this,Start_Screen::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        findViewById<com.journeyapps.barcodescanner.CompoundBarcodeView>(R.id.qrcode_reader)?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        findViewById<com.journeyapps.barcodescanner.CompoundBarcodeView>(R.id.qrcode_reader)?.pause()
    }

    // 赤い線を消す処理
    private fun disableLaser(decoratedBarcodeView: DecoratedBarcodeView) {
        val scannerAlphaField = ViewfinderView::class.java.getDeclaredField("SCANNER_ALPHA")
        scannerAlphaField.isAccessible = true
        scannerAlphaField.set(decoratedBarcodeView.viewFinder, intArrayOf(0))
    }

    companion object {
        const val REQUEST_CAMERA_PERMISSION = 101
    }
}