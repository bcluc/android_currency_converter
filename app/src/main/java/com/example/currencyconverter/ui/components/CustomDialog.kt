package com.example.currencyconverter.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.theme.Purple40
import com.example.currencyconverter.ui.theme.fontFamily
import com.example.currencyconverter.util.*


@Composable
fun CustomDialog(
    headerMessage: String,
    errorMessage: String,
    openDialogCustom: MutableState<Boolean>
) {
    if (openDialogCustom.value) {

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(
                Modifier
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_notifications_24),
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(
                        color = mainColor
                    ),
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(70.dp)
                        .fillMaxWidth(),

                    )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = headerMessage,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = errorMessage,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(bgColor),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    TextButton(onClick = {
                        openDialogCustom.value = false
                    }) {

                        Text(
                            "Confirm",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }

                }
            }
        }
    }
}
