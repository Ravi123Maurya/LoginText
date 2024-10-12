package com.ravimaurya.logintest

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun HomeScreen(navController: NavController){

    val context = LocalContext.current
    var showDialog by remember {
        mutableStateOf(false)
    }
    val loginViewModel: LoginViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 80.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
            if (showDialog){
                AlertDialog(
                    title = { Text("Logout") },
                    text = { Text("Are you sure want to logout?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
                                navController.navigate("login"){
                                    popUpTo("login"){
                                        inclusive = true
                                    }
                                }
                                loginViewModel.logout(context)
                                showDialog = false
                            }
                        ) {
                            Text("Ok")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    },
                    onDismissRequest = {
                        showDialog = false
                    },
                )
            }


        Text(
            text = "Home",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )


        // Logout Button
        Button(
            onClick = {

                showDialog = true
            }
        ) {
            Text("Logout")
        }

    }

}