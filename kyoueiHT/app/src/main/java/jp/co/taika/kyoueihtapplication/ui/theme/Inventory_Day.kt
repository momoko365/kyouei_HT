package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import jp.co.taika.kyoueihtapplication.R

class Inventory_Day : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inventory_day)
        var inventory_year = findViewById<EditText>(R.id.inventory_year)
        var inventory_month = findViewById<EditText>(R.id.inventory_month)
        var inventory_day = findViewById<EditText>(R.id.inventory_day)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)

        var timesave = LocalDateTime.now()
        var getyear = DateTimeFormatter.ofPattern("yyyy")
        var getmonth = DateTimeFormatter.ofPattern("MM")
        var getday = DateTimeFormatter.ofPattern("dd")
        var year = getyear.format(timesave)
        var month = getmonth.format(timesave)
        var day = getday.format(timesave)
        inventory_year.setText(year.toString())
        inventory_month.setText(month.toString())
        inventory_day.setText(day.toString())

        

        next_btn.setOnClickListener {

        }

        back_btn.setOnClickListener {
            val intent =  Intent(this,Inventory_Day::class.java)
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