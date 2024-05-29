package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import jp.co.taika.kyoueihtapplication.R

class Barcode : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nyuko_barcode)
        var puroduct_b = findViewById<EditText>(R.id.puroduct_b)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)

        next_btn.setOnClickListener {
            val intent =  Intent(this,Location::class.java)
            startActivity(intent)
            finish()

        }

        back_btn.setOnClickListener {
            val intent = Intent(this,Nyuko_Goods::class.java)
            startActivity(intent)
            finish()
        }


        /*setContent {
            KyoueiHTApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }*/
    }
}

/*@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KyoueiHTApplicationTheme {
        Greeting("Android")
    }
}*/