package com.liacforcadell.tp1lia

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.liacforcadell.tp1lia.ui.theme.TP1LiaTheme

class MainActivity : ComponentActivity() {
    private var isDarkTheme by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TP1LiaTheme(darkTheme = isDarkTheme) {
                Column {
                    HomeApp()
                }
            }
        }
    }

    @Composable
    fun HomeApp() {
        var recetas by remember { mutableStateOf(mutableListOf<Receta>().toMutableStateList()) }
        val ingredientesTexto = "tomate, cebolla, ajo"
        val listaIngredientes: List<String> = ingredientesTexto.split(",").map { it.trim() }
        recetas.add(Receta(nombre = "Nueva",
            tiempo = "15 minutos",
            ingredientes = listaIngredientes,
            calorias = "500",
            imagenUrl = "https://img.freepik.com/free-photo/seafood-pizza_1203-8951.jpg?t=st=1729129773~exp=1729133373~hmac=73f66106ca2d272d13db6d3b264ea939d380ad7c282a667d532f97d2feabad01&w=360"))

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    FormularioIngreso() { nuevaReceta ->
                        recetas.add(nuevaReceta)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    ListaRecetas(recetas)
                }
            }
    }

    @Composable
    fun FormularioIngreso(onAgregarReceta: (Receta) -> Unit) {
        var nombrePlato by remember { mutableStateOf("") }
        var tiempoPreparacion by remember { mutableStateOf("") }
        var ingredientesTexto by remember { mutableStateOf("") }
        var calorias by remember { mutableStateOf("") }
        var imagenUrl by remember { mutableStateOf("") }
        var showToast by remember { mutableStateOf(false) }
        var toastMessage by remember { mutableStateOf("") }

        Column (
            modifier = Modifier.wrapContentHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Etiqueta("Nombre del Plato")
            Entrada(value = nombrePlato, onValueChange = { nombrePlato = it }, label = "Ingresa el Nombre del Plato")
            Etiqueta("Tiempo de Preparación (minutos)")
            Entrada(value = tiempoPreparacion, onValueChange = { tiempoPreparacion = it }, label = "Ingresa el Tiempo de Preparación")
            Etiqueta("Ingredientes (separados por comas)")
            Entrada(value = ingredientesTexto, onValueChange = { ingredientesTexto = it }, label = "Ingresa los Ingredientes")
            Etiqueta("Calorías por Porción (kCal)")
            Entrada(value = calorias, onValueChange = { calorias = it }, label = "Ingresa las Calorías")
            Etiqueta("URL de la Imagen")
            Entrada(value = imagenUrl, onValueChange = { imagenUrl = it }, label = "Ingresa la URL de la Imagen")


            Spacer(modifier = Modifier.height(16.dp))

            Btn(onClick = {
//                if (nombrePlato.isNotEmpty() && tiempoPreparacion.isNotEmpty() && ingredientesTexto.isNotEmpty() &&
//                    calorias.isNotEmpty() && imagenUrl.isNotEmpty()) {
                if (nombrePlato.isNotEmpty()) {

                    val listaIngredientes = ingredientesTexto.split(",").map { it.trim() }

                    val nuevaReceta = Receta(
                            nombre = nombrePlato,
                            tiempo = tiempoPreparacion,
                            ingredientes = listaIngredientes,
                            calorias = calorias,
                            imagenUrl = imagenUrl
                    )

                    onAgregarReceta(nuevaReceta)

                    nombrePlato = ""
                    tiempoPreparacion = ""
                    ingredientesTexto = ""
                    calorias = ""
                    imagenUrl = ""

                } else {
                    toastMessage = "Por favor, completa todos los campos."
                    showToast = true
                }
            })

            if (showToast) {
                mostrarToast(toastMessage)
            }

        }
    }

    @Composable
    fun RecetaItem(receta: Receta) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(receta.imagenUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = receta.nombre,
                    modifier = Modifier
                        .height(200.dp)
                        .width(150.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = receta.nombre,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Tiempo de preparación: ${receta.tiempo}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Ingredientes: ${receta.ingredientes.joinToString(", ")}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Calorías: ${receta.calorias}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

        }
    }


    @Composable
    fun ListaRecetas(recetas: List<Receta>) {
        Log.d("ListaRecetas", "Número de recetas: ${recetas.size}")
        if (recetas.isEmpty()) {
            Text("No hay recetas disponibles.", style = MaterialTheme.typography.headlineMedium)
        } else {
            LazyColumn {
                items(recetas) { receta ->
                    RecetaItem(receta)
                }
            }
        }
    }

    @Composable
    fun Etiqueta(text: String) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }

    @Composable
    fun Entrada(value: String, onValueChange: (String) -> Unit, label: String) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth()
        )
    }

    @Composable
    fun Btn(onClick: () -> Unit) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = "Agregar Receta",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }

    @Composable
    fun mostrarToast(mensaje: String) {
        val context = LocalContext.current
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }

    @Preview(showBackground = true)
    @Composable
    fun HomeAppPreview() {
        TP1LiaTheme(darkTheme = false) {
            Column {
                HomeApp()

            }
        }
    }

    data class Receta(
        val nombre: String,
        val tiempo: String,
        val ingredientes: List<String>,
        val calorias: String,
        val imagenUrl: String
    )

//    @Composable
//    fun Layaout() {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Saludo("Android")
//            ColorCabiante()
//            Contador()
//        }
//    }
//
//    @Composable
//    fun Saludo(nombre: String) {
//        Text(text = "Hola $nombre")
//    }
//
//    @Composable
//    fun ColorCabiante() {
//        var colorActual by remember { mutableStateOf(Color.Red) }
//        Column {
//            Box(
//                modifier = Modifier
//                    .size(100.dp)
//                    .background(colorActual)
//            )
//            Button(onClick = {
//                colorActual = when (colorActual) {
//                    Color.Red -> Color.Green
//                    Color.Green -> Color.Blue
//                    else -> Color.Red
//                }
//            }) {
//                Text("Cambiar a Azul")
//            }
//        }
//    }
//
//    @Composable
//    fun Contador() {
//        var cuenta by remember{ mutableStateOf(0)}
//        Button(onClick = { cuenta++ }) {
//            Text("La cuenta es $cuenta")
//        }
//    }
}
