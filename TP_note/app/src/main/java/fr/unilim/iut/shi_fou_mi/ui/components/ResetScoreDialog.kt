package fr.unilim.iut.shi_fou_mi.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.unilim.iut.shi_fou_mi.utils.LanguageManager
import fr.unilim.iut.shi_fou_mi.utils.capitalizeFirstLetter

@Composable
fun ResetScoresDialog(showDialog: Boolean, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(LanguageManager.getLexicon().confirm.capitalizeFirstLetter()) },
            text = { Text(LanguageManager.getLexicon().resetscores) },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text(LanguageManager.getLexicon().yes.capitalizeFirstLetter())
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(LanguageManager.getLexicon().cancel.capitalizeFirstLetter())
                }
            }
        )
    }
}
