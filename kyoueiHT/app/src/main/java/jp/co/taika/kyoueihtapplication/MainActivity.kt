package jp.co.taika.kyoueihtapplication
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.co.taika.kyoueihtapplication.function.Strings
import jp.co.taika.kyoueihtapplication.ui.theme.KyoueiHTApplicationTheme
class MainActivity : ComponentActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.work_day)
        var work_year = findViewById<EditText>(R.id.work_year)
        var work_month = findViewById<EditText>(R.id.work_month)
        var work_day = findViewById<EditText>(R.id.work_day)
        var timesave = LocalDateTime.now()
        var getyear = DateTimeFormatter.ofPattern("yyyy")
        var getmonth = DateTimeFormatter.ofPattern("MM")
        var getday = DateTimeFormatter.ofPattern("dd")
        var year = getyear.format(timesave)
        var month = getmonth.format(timesave)
        var day = getday.format(timesave)

        work_year.setText(year.toString())

        work_month.setText(month.toString())

        work_day.setText(day.toString())




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