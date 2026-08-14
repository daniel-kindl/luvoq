package dev.danielkindl.luvoq

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Style
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.danielkindl.luvoq.feature.editor.RoutineEditorScreen
import dev.danielkindl.luvoq.feature.home.HomeScreen
import dev.danielkindl.luvoq.feature.paywall.PaywallScreen
import dev.danielkindl.luvoq.feature.settings.SettingsScreen

private object Routes {
    const val HOME = "home"
    const val TEMPLATES = "templates"
    const val EDITOR = "editor"
    const val HISTORY = "history"
    const val SETTINGS = "settings"
    const val PAYWALL = "paywall"
}

private data class TopLevelDestination(
    val route: String,
    val labelRes: Int,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val contentDescriptionRes: Int,
)

@Composable
fun LuvoqApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val destinations = listOf(
        TopLevelDestination(Routes.HOME, R.string.nav_home, Icons.Rounded.Home, R.string.home_content_description),
        TopLevelDestination(
            Routes.TEMPLATES,
            R.string.nav_templates,
            Icons.Rounded.Style,
            R.string.templates_content_description,
        ),
        TopLevelDestination(Routes.HISTORY, R.string.nav_history, Icons.Rounded.History, R.string.history_content_description),
        TopLevelDestination(
            Routes.SETTINGS,
            R.string.nav_settings,
            Icons.Rounded.Settings,
            R.string.settings_content_description,
        ),
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in destinations.map { it.route }) {
                NavigationBar {
                    Row {
                        destinations.forEach { destination ->
                            NavigationBarItem(
                                selected = currentRoute == destination.route,
                                onClick = {
                                    navController.navigate(destination.route) {
                                        popUpTo(Routes.HOME) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = destination.icon,
                                        contentDescription = stringResource(destination.contentDescriptionRes),
                                    )
                                },
                                label = { Text(stringResource(destination.labelRes)) },
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            if (currentRoute in destinations.map { it.route }) {
                FloatingActionButton(onClick = { navController.navigate(Routes.EDITOR) }) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.nav_create),
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Routes.HOME) { HomeScreen() }
            composable(Routes.TEMPLATES) { PlaceholderScreen(R.string.templates_title, R.string.templates_description) }
            composable(Routes.HISTORY) { PlaceholderScreen(R.string.history_title, R.string.history_description) }
            composable(Routes.SETTINGS) {
                SettingsScreen(onOpenPlans = { navController.navigate(Routes.PAYWALL) })
            }
            composable(Routes.EDITOR) {
                RoutineEditorScreen(onBack = { navController.popBackStack() })
            }
            composable(Routes.PAYWALL) {
                PaywallScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
private fun PlaceholderScreen(titleRes: Int, descriptionRes: Int) {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier.padding(24.dp),
    ) {
        Text(stringResource(titleRes), style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Text(
            stringResource(descriptionRes),
            modifier = Modifier.padding(top = 8.dp),
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
