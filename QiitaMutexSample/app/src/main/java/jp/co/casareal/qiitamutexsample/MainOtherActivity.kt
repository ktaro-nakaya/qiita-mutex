package jp.co.casareal.qiitamutexsample

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MainOtherActivity : AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.Default)

    val mutex = Mutex()

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        textView = findViewById(R.id.textCount)

        findViewById<Button>(R.id.btnCount1).setOnClickListener {
            scope.launch {
                countUp()
            }
        }

        findViewById<Button>(R.id.btnCount2).setOnClickListener {
            scope.launch {
                countUp()
            }
        }
    }

    private suspend fun countUp() = coroutineScope {

        mutex.withLock {

            repeat(10) {
                withContext(Dispatchers.Main) {
                    textView.text = "${it + 1}回目"
                    delay(1000)
                }
            }
        }
    }
}