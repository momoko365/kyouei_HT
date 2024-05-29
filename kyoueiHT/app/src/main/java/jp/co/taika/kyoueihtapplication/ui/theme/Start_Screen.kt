package jp.co.taika.kyoueihtapplication.ui.theme

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import jp.co.taika.kyoueihtapplication.R
import jp.co.taika.kyoueihtapplication.function.Strings

//初期画面
class Start_Screen : ComponentActivity() {
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 101
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//レイアウト画面との紐づけ
        setContentView(R.layout.start_screen)
        requestCameraPermission()
        var QR_btn = findViewById<Button>(R.id.QR_btn)




//QRコード読み込みボタンを推したときの処理

        QR_btn.setOnClickListener {
            if (isCameraPermissionGranted()) {
                // QRCodeMainへの遷移
                val intent = Intent(this, QRCodeMain::class.java)
                startActivity(intent)
                finish()
            } else {
                // カメラのパーミッションが拒否されている場合、トーストメッセージを表示
                Toast.makeText(this, "カメラの許可が必要です。設定からカメラを許可してください。", Toast.LENGTH_LONG).show()
            }
        }



    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        // カメラのパーミッションがすでに許可されているかチェック
        if (!isCameraPermissionGranted()) {
            // 許可されていない場合はパーミッションをリクエスト
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                // パーミッションが拒否された場合、ユーザーにカメラの許可が必要であることを通知
                showPermissionDeniedMessage()
            }
        }
    }

    private fun showPermissionDeniedMessage() {
        // パーミッション拒否のメッセージを表示
        Toast.makeText(this, "カメラの許可が必要です。設定からカメラを許可してください。", Toast.LENGTH_LONG).show()
    }
}
