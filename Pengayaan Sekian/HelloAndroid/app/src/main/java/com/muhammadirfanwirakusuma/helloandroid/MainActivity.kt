package com.muhammadirfanwirakusuma.helloandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.muhammadirfanwirakusuma.helloandroid.ui.theme.HelloAndroidTheme

//import no 16
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

//import no 17 toast
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

//import no 18
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate() dipanggil")
        enableEdgeToEdge()
        setContent {
            HelloAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Muhammad Irfan Wira Kusuma",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart() dipanggil")
    }
    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume() dipanggil")
    }
    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause() dipanggil")
    }
    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop() dipanggil")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy() dipanggil")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Halo! Saya $name",
            fontSize = 24.sp,
            color = Color(0xFF2E7D32),
        )

        Text(
            text = "NIM: 105223005",
            fontSize = 18.sp,
        )

        Button(onClick = {
            Toast.makeText(
                context,
                "Android dikembangkan oleh Google sejak 2005!",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text(text = "Tentang Android")
        }

        Button(onClick = {
            val intent = android.content.Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Tentang Android2")
        }
    }
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloAndroidTheme {
        Greeting("Android")
    }
}