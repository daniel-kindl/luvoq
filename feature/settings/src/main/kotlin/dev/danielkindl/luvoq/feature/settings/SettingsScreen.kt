package dev.danielkindl.luvoq.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.danielkindl.luvoq.ui.components.CapabilityRow
import dev.danielkindl.luvoq.ui.components.SectionHeader

@Composable
fun SettingsScreen(
    onOpenPlans: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(stringResource(R.string.settings_title), style = MaterialTheme.typography.headlineMedium)
        SectionHeader(stringResource(R.string.settings_theme_title))
        CapabilityRow(
            title = stringResource(R.string.settings_theme_title),
            description = stringResource(R.string.settings_theme_description),
        )
        SectionHeader(stringResource(R.string.settings_plan_title))
        CapabilityRow(
            title = stringResource(R.string.settings_plan_title),
            description = stringResource(R.string.settings_plan_description),
            onClick = onOpenPlans,
        )
        SectionHeader(stringResource(R.string.settings_about_title))
        Text(
            stringResource(R.string.settings_about_description),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
