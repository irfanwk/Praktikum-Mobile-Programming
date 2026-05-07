package com.muhammadirfanwirakusuma.helloandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadirfanwirakusuma.helloandroid.ui.theme.HelloAndroidTheme

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AboutScreen(
                        modifier = Modifier.padding(innerPadding),
                        onBackClick = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun AboutScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sejarah Android", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        // Fakta 1
        Text(text = "• Dalam pemrograman, terdapat 'State' yang selalu berubah-ubah seiring interaksi pengguna.", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Fakta 2
        Text(text = "• Transisi besar terjadi pada Android 4.0 yang mulai mempopulerkan virtual keyboard dan navigasi layar sentuh penuh.", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Fakta 3
        Text(text = "• Android awalnya dirancang sebagai sistem operasi untuk kamera pintar, bukan untuk ponsel!", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onBackClick) {
            Text(text = "Kembali")
        }
    }
}