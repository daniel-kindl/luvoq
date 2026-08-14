package dev.danielkindl.luvoq.feature.editor

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
import dev.danielkindl.luvoq.ui.components.SectionHeader

@Composable
fun RoutineEditorScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(stringResource(R.string.editor_title), style = MaterialTheme.typography.headlineMedium)
        SectionHeader(stringResource(R.string.editor_when))
        Text(stringResource(R.string.editor_when_placeholder))
        SectionHeader(stringResource(R.string.editor_only_if))
        Text(stringResource(R.string.editor_only_if_placeholder))
        SectionHeader(stringResource(R.string.editor_do))
        Text(stringResource(R.string.editor_do_placeholder))
        AppButton(
            text = stringResource(R.string.editor_save),
            onClick = onBack,
        )
        AppButton(
            text = stringResource(R.string.editor_back),
            onClick = onBack,
        )
    }
}
