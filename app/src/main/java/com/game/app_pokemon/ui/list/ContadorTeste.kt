package list


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ContadorTeste() {
    var contador by remember { mutableStateOf(1) }
    Column() {
        Text(text = "$contador")
        Button(onClick = { contador++ }) {
            Text(text = "Incrementar")
        }
        Button(onClick = { contador=0 }){
            Text(text = "Zerar")
        }
    }


}

