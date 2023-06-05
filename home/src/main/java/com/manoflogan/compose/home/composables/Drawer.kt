package com.manoflogan.compose.home.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manoflogan.compose.home.Destination
import com.manoflogan.compose.home.R
import java.util.Locale


@Composable
fun ColumnScope.DrawerContent(onNavigation: (Destination) -> Unit, onLogout: () -> Unit) {
    Text(modifier = Modifier.padding(16.dp), text = stringResource(id = R.string.label_name), fontSize = 20.sp)
    Spacer(modifier = Modifier.height(8.dp))
    Text(modifier = Modifier.padding(16.dp), text = stringResource(id = R.string.label_email), fontSize = 16.sp)
    Divider(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
    )
    DrawerItem(modifier = Modifier.fillMaxWidth(), label = Destination.Settings.path) {
        onNavigation(Destination.Settings)
    }
    Spacer(modifier = Modifier.height(8.dp))
    DrawerItem(modifier = Modifier.fillMaxWidth(), label = Destination.Upgrade.path) {
        onNavigation(Destination.Upgrade)
    }
    Spacer(modifier = Modifier.weight(1f))
    DrawerItem(modifier = Modifier.fillMaxWidth(), label = stringResource(id = R.string.logout)) {
        onLogout()
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun DrawerItem(modifier: Modifier, label: String, onClick: () -> Unit) {
    Text(
        modifier = modifier.then(
            Modifier
                .clickable(role = Role.Button) {
                    onClick()
                }
                .semantics() {
                    contentDescription = label
                }
                .padding(16.dp)
        ),
        text = label.replaceFirstChar {
            it.titlecase(Locale.getDefault())
        },
        fontSize = 16.sp
    )
}

@Preview
@Composable
fun DrawerContentPreview() {
    Column {
        DrawerContent({

        }){

        }
    }
}
