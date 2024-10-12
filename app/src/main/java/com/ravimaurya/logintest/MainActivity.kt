package com.ravimaurya.logintest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ravimaurya.logintest.ui.theme.LoginTestTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginTestTheme {

                val context = LocalContext.current
                val navController = rememberNavController()
                val loginViewModel: LoginViewModel = viewModel()
                val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()
                var showProcess by remember {
                    mutableStateOf(true)
                }
                LaunchedEffect(Unit) {
                    loginViewModel.observeStatus(context)
                    delay(700)
                    showProcess = false
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (showProcess){
                        CircularProgressIndicator()
                    }
                    else{
                        NavHost(
                            navController = navController,
                            startDestination = if (isLoggedIn) "home" else "login"
                        ){

                            composable("login"){
                                LoginScreen(navController)
                            }
                            composable("home"){
                                HomeScreen(navController)
                            }
                        }
                    }
                }


            }
        }
    }
}


