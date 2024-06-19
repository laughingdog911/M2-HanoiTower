package com.cj.month_02

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Directions
import androidx.compose.material.icons.rounded.Numbers
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cj.month_02.ui.theme.Month_02Theme
import kotlin.concurrent.thread

fun checkValue(numberOfPlate: String): Boolean {
    return numberOfPlate != "" && numberOfPlate.toInt() > 0
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainView() {
    var numberOfPlate by remember {
        mutableStateOf("")
    }

    var from by remember {
        mutableStateOf("A")
    }

    var via by remember {
        mutableStateOf("B")
    }

    var to by remember {
        mutableStateOf("C")
    }

    var showAlert by remember {
        mutableStateOf(false)
    }

    var showProgress by remember {
        mutableStateOf(false)
    }

    val scrollState = rememberScrollState()

    Month_02Theme {
        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "Tower of Hanoi") }, navigationIcon = {
                if (!showProgress) {
                    IconButton(onClick = {
                        numberOfPlate = ""
                    }) {
                        Icon(imageVector = Icons.Rounded.Clear, contentDescription = null)
                    }
                } else {
                    CircularProgressIndicator()
                }

            })
        }) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    TextField(
                        value = numberOfPlate,
                        onValueChange = { numberOfPlate = it },
                        label = {
                            Text(
                                text = "Number of Plate"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = {
                            Icon(imageVector = Icons.Rounded.Numbers, contentDescription = null)
                        },
                        maxLines = 1,
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = from,
                            onValueChange = { from = it },
                            label = {
                                Text(
                                    text = "From"
                                )
                            },
                            modifier = Modifier.weight(0.3f),
                            leadingIcon = {
                                Icon(imageVector = Icons.Rounded.Directions, contentDescription = null)
                            },
                            maxLines = 1,
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.weight(0.05f))

                        OutlinedTextField(
                            value = via,
                            onValueChange = { via = it },
                            label = {
                                Text(
                                    text = "Via"
                                )
                            },
                            modifier = Modifier.weight(0.3f),
                            leadingIcon = {
                                Icon(imageVector = Icons.Rounded.Directions, contentDescription = null)
                            },
                            maxLines = 1,
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.weight(0.05f))

                        OutlinedTextField(
                            value = to,
                            onValueChange = { to = it },
                            label = {
                                Text(
                                    text = "To"
                                )
                            },
                            modifier = Modifier.weight(0.3f),
                            leadingIcon = {
                                Icon(imageVector = Icons.Rounded.Directions, contentDescription = null)
                            },
                            maxLines = 1,
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    if (!showProgress) {
                        FilledTonalButton(onClick = {
                            if (!checkValue(numberOfPlate)) {
                                showAlert = true
                            } else {
                                showProgress = true
                                val start = System.currentTimeMillis()

                                Utils.log.value += "Starting...\n"

                                thread(true) {
                                    Utils.solA(numberOfPlate.toInt(), from, to, via)

                                    val end = System.currentTimeMillis()
                                    Utils.log.value += "Solution is complete. elsped time: ${(end - start) / 1000f}s\n"
                                    showProgress = false
                                }
                            }
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Solve")
                        }
                    } else {
                        CircularProgressIndicator()
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Log",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        TextButton(onClick = { Utils.log.value = "" }) {
                            Text(text = "Clear")
                        }
                    }

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                            .background(Color.LightGray)
                            .clip(
                                RoundedCornerShape(15.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Text(text = Utils.log.value, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (showAlert) {
                        AlertDialog(onDismissRequest = { showAlert = false }, confirmButton = {
                            TextButton(onClick = { showAlert = false }) {
                                Text(text = "OK")
                            }
                        }, icon = {
                            Icon(imageVector = Icons.Rounded.Warning, contentDescription = null)
                        }, title = {
                            Text(text = "Invalid number of plate")
                        }, text = {
                            Text(text = "Please enter 1 or more plates.")
                        })
                    }
                }
            }
        }
    }
}