package dev.danielkindl.luvoq.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.padding(vertical = 8.dp),
    )
}

@Composable
fun EmptyState(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
    ) {
        Text(text)
    }
}

@Composable
fun StatusIndicator(
    label: String,
    isActive: Boolean,
    activeStateDescription: String,
    inactiveStateDescription: String,
    modifier: Modifier = Modifier,
) {
    val state = if (isActive) activeStateDescription else inactiveStateDescription
    AssistChip(
        onClick = {},
        label = { Text(label) },
        modifier = modifier.semantics {
            stateDescription = state
        },
    )
}

@Composable
fun RoutineCard(
    title: String,
    summary: String,
    enabled: Boolean,
    enabledLabel: String,
    disabledLabel: String,
    activeStateDescription: String,
    inactiveStateDescription: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Card(
        onClick = onClick ?: {},
        enabled = onClick != null,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(),
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(summary, color = MaterialTheme.colorScheme.onSurfaceVariant)
            StatusIndicator(
                label = if (enabled) enabledLabel else disabledLabel,
                isActive = enabled,
                activeStateDescription = activeStateDescription,
                inactiveStateDescription = inactiveStateDescription,
            )
        }
    }
}

@Composable
fun CapabilityRow(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Surface(
        onClick = onClick ?: {},
        enabled = onClick != null,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall)
            Text(description, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun PermissionBanner(
    title: String,
    message: String,
    actionLabel: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall)
            Text(message, color = MaterialTheme.colorScheme.onSurfaceVariant)
            TextButton(onClick = onAction) { Text(actionLabel) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier.padding(bottom = 24.dp)) {
            content()
        }
    }
}

@Composable
fun PlanCard(
    title: String,
    description: String,
    actionLabel: String,
    modifier: Modifier = Modifier,
    onAction: () -> Unit = {},
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Text(description, color = MaterialTheme.colorScheme.onSurfaceVariant)
            AppButton(text = actionLabel, onClick = onAction)
        }
    }
}

@Composable
fun TemplateCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(description, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
