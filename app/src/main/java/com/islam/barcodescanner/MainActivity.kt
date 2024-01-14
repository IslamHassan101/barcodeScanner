package com.islam.barcodescanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.islam.barcodescanner.ui.theme.BarcodeScannerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    lateinit var barcodeScanner: BarcodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            barcodeScanner = BarcodeScanner(context)

            BarcodeScannerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val barcodeResult = barcodeScanner.barCodeResults.collectAsStateWithLifecycle()

                    ScanBarCode(
                        onScanBarCode = barcodeScanner::startScan,
                        barcodeValue = barcodeResult.value
                    )
                }
            }
        }
    }

    @Composable
    fun ScanBarCode(
        onScanBarCode: suspend () -> Unit,
        barcodeValue: String?
    ) {
        val scope = rememberCoroutineScope()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier.fillMaxWidth(85f), onClick = {
                scope.launch {
                    onScanBarCode()
                }
            }) {
                Text(text = "Scan Barcode")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = barcodeValue ?: "00000000")
    }
}
