package dev.danielkindl.luvoq.feature.paywall

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
import dev.danielkindl.luvoq.ui.components.AppButton
import dev.danielkindl.luvoq.ui.components.PlanCard

@Composable
fun PaywallScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(stringResource(R.string.paywall_title), style = MaterialTheme.typography.headlineMedium)
        Text(
            stringResource(R.string.paywall_description),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        PlanCard(
            title = stringResource(R.string.paywall_free_title),
            description = stringResource(R.string.paywall_free_description),
            actionLabel = stringResource(R.string.paywall_continue),
        )
        PlanCard(
            title = stringResource(R.string.paywall_pro_title),
            description = stringResource(R.string.paywall_pro_description),
            actionLabel = stringResource(R.string.paywall_continue),
        )
        AppButton(
            text = stringResource(R.string.paywall_back),
            onClick = onBack,
        )
    }
}
