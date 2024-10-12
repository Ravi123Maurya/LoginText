package com.ravimaurya.logintest

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun LoginScreen(navController: NavController){
    val loginViewModel: LoginViewModel = viewModel()
    val context = LocalContext.current
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isLoginBtnClicked by remember {
        mutableStateOf(false)
    }
    var isPassVisible by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 80.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Login",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            modifier = Modifier.onFocusChanged { isLoginBtnClicked = false },
            label = { Text("UserName") },
            placeholder = { Text("Enter your username") },
            value = username,
            onValueChange = {
                username = it
            }
        )
        OutlinedTextField(
            modifier = Modifier.onFocusChanged { isLoginBtnClicked = false },
            label = { Text("Password") },
            placeholder = { Text("Enter password") },
            value = password,
            onValueChange = {
                password = it
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        isPassVisible != isPassVisible
                    }
                ) {
                    Icon(
                        imageVector = if(isPassVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = ""
                    )
                }

            },
            visualTransformation = if(isPassVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {

                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(context, "Username or Password can't be empty!", Toast.LENGTH_SHORT).show()
                }
                else if (username.trim() == "admin" && password.trim() == "admin") {
                loginViewModel.login(context)
                    navController.navigate("home"){
                        popUpTo("home"){
                            inclusive = true
                        }
                    }
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                }else{
                    isLoginBtnClicked = true
                }
            },
            shape = ShapeDefaults.Small
        ) {
            Text("Login")
        }

            if (isLoginBtnClicked){
                AnimatedVisibility(
                    visible = username != "admin",
                    enter = slideInVertically()
                ) {
                    Text( "Username not found!", color = Color.Red)
                }
            }




    }


}