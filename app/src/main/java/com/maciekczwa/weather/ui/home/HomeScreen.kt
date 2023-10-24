import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maciekczwa.weather.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val state: HomeState = viewModel.state.collectAsState().value
    val permissionHandler = rememberLocationPermissionHandler(onDenied = viewModel::locationPermissionDenied, onGranted = viewModel::load)
    LaunchedEffect(key1 = permissionHandler) {
        permissionHandler.requestLocationPermission()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { viewModel.load() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(id = R.string.refresh),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when (state) {
                is HomeState.Loading ->
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator()
                        Text(text = stringResource(id = R.string.loading))
                    }

                is HomeState.Success ->
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text =
                                stringResource(
                                    id = R.string.location,
                                    state.weather.locationName,
                                    state.weather.latitude,
                                    state.weather.longitude,
                                ),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Text(text = stringResource(id = R.string.temperature, state.weather.temperature))
                    }

                is HomeState.Error ->
                    Column(
                        Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = state.error.toString())
                        Button(onClick = { viewModel.load() }) {
                            Text(text = stringResource(id = R.string.retry))
                        }
                    }

                is HomeState.LocationPermissionDenied ->
                    Column(
                        Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = stringResource(id = R.string.location_permission_denied))
                        Button(onClick = { permissionHandler.requestLocationPermission() }) {
                            Text(text = stringResource(id = R.string.retry))
                        }
                    }

                is HomeState.NoLocation ->
                    Column(
                        Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = stringResource(id = R.string.location_unavailable))
                        Button(onClick = { viewModel.load() }) {
                            Text(text = stringResource(id = R.string.retry))
                        }
                    }
            }
        }
    }
}
