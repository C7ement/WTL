@file:OptIn(ExperimentalFoundationApi::class)

package space.intbh.wtl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import space.intbh.wtl.ui.flowerList.FlowerListScreen
import space.intbh.wtl.ui.theme.WTLTheme


@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WTLTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlowerListScreen()
                }
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WTLTheme {
        FlowerListScreen()
    }
}