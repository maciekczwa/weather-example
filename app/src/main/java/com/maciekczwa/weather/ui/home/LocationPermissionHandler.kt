import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import timber.log.Timber

@Composable
fun rememberLocationPermissionHandler(
    onDenied: () -> Unit,
    onGranted: () -> Unit,
): LocationPermissionHandler {
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions ->
                val permissionsGranted =
                    permissions.values.reduce { acc, isPermissionGranted ->
                        acc && isPermissionGranted
                    }
                if (!permissionsGranted) {
                    onDenied()
                } else {
                    onGranted()
                }
            },
        )
    val handler =
        remember {
            LocationPermissionHandler(launcher)
        }
    return handler
}

class LocationPermissionHandler(
    private val launcher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
) {
    private val permissions = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION)

    fun requestLocationPermission() {
        Timber.d("Requesting location permission")
        launcher.launch(permissions)
    }
}
