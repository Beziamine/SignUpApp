package com.signup.application.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.signup.application.R
import com.signup.application.utils.Screen
import com.signup.application.viewmodels.HomeViewModel

@Composable
fun ConfirmationScreen(userId: String,
                       navController: NavHostController,
                       homeViewModel: HomeViewModel) {
    val scrollState = rememberScrollState()
    val gradient = Brush.horizontalGradient(listOf(colorResource(id = R.color.orange), colorResource(id = R.color.pink)))

    homeViewModel.findUserById(userId.toInt())
    val selectedUser = homeViewModel.foundUser.observeAsState().value

    BackHandler{
        navController.navigate(route = Screen.SignUp.route)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        val modifier = Modifier.padding(it)
        Box(modifier = modifier.fillMaxSize()){
            if (selectedUser != null) {
                Column(modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState),
                ) {
                    Text(modifier = modifier.padding(20.dp,20.dp,0.dp,0.dp),
                        text = stringResource(R.string.hello) + selectedUser.firstName + "!", color = MaterialTheme.colors.surface,
                        style = MaterialTheme.typography.h1)
                    Text(modifier = modifier.padding(20.dp,5.dp,0.dp,0.dp),
                        text = stringResource(R.string.your_super_awesome_portfolio), color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1)

                    Box(
                        modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)) {
                        Image(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .height(200.dp)
                                .width(150.dp)
                                .clip(RoundedCornerShape(15.dp)),
                            painter = rememberImagePainter(selectedUser.image),
                            contentDescription = null
                        )
                    }
                    if(selectedUser.website.isNotEmpty()){
                        Text(modifier = modifier
                            .fillMaxSize()
                            .padding(20.dp, 20.dp, 20.dp, 0.dp),
                            text = selectedUser.website, color = colorResource(id = R.color.blue),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.Underline)
                    }
                    if(selectedUser.firstName.isNotEmpty()){
                        Text(modifier = modifier
                            .fillMaxSize()
                            .padding(20.dp, 20.dp, 20.dp, 0.dp),
                            text = selectedUser.firstName, color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center)
                    }
                    Text(modifier = modifier
                        .fillMaxSize()
                        .padding(20.dp, 20.dp, 20.dp, 0.dp),
                        text = selectedUser.email, color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center)
                }
                Row(Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom) {
                    GradientButton(
                        text = stringResource(R.string.sign_in),
                        gradient = gradient,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                }
            }
        }

    }
}