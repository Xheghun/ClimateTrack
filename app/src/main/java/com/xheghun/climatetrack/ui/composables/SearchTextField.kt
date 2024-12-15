package com.xheghun.climatetrack.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xheghun.climatetrack.R

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hintText: String,
) {
    val textStyle = MaterialTheme.typography.bodySmall
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        BasicTextField(
            value = value,
            singleLine = true,
            textStyle = textStyle,
            onValueChange = { newValue ->
                onValueChange(newValue)
            },
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = hintText,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = textStyle,
                    )
                } else {
                    Box {
                        innerTextField()
                    }
                }
            },
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = painterResource(id = R.drawable.search_icon),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}