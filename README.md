# TP1DispositivosMov - Proyecto de Recetas

Este proyecto es una aplicación de recetas desarrollada en Kotlin utilizando Jetpack Compose. Permite a los usuarios agregar recetas, que incluyen información como el nombre del plato, tiempo de preparación, ingredientes, calorías y una URL de imagen.

## Estructura del Proyecto

El proyecto está organizado en el siguiente paquete principal:

- **`com.liacforcadell.tp1lia`**
  - **`MainActivity.kt`**: Contiene la actividad principal de la aplicación y la lógica de la interfaz de usuario.
  - **`Receta`**: Clase de datos que representa una receta con las propiedades:
    - `nombre`: Nombre del plato.
    - `tiempo`: Tiempo de preparación.
    - `ingredientes`: Lista de ingredientes.
    - `calorias`: Calorías por porción.
    - `imagenUrl`: URL de la imagen del plato.

### Clases Composables

- **`HomeApp`**: Composable principal que muestra el formulario de ingreso y la lista de recetas.
- **`FormularioIngreso`**: Permite a los usuarios ingresar detalles de una nueva receta.
- **`ListaRecetas`**: Muestra una lista de recetas utilizando un `LazyColumn`.
- **`RecetaItem`**: Representa visualmente cada receta en la lista, mostrando su imagen y detalles.
- **`Etiqueta`**: Composable para mostrar etiquetas de texto.
- **`Entrada`**: Composable que implementa un campo de texto.
- **`Btn`**: Composable que implementa un botón para agregar una nueva receta.
- **`mostrarToast`**: Función para mostrar mensajes emergentes.

## Cómo Ejecutar la Aplicación

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/liacforcadell/UAA-PAPDM-Grupo-7-TP-1.git
