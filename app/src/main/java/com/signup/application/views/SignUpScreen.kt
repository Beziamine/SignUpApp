package com.signup.application.views

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.signup.application.BuildConfig
import com.signup.application.R
import com.signup.application.database.UserEntity
import com.signup.application.utils.Screen
import com.signup.application.viewmodels.HomeViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import java.util.regex.Pattern

var userId: Int = 1
var firstName: String = ""
var email: String = ""
var password: String = ""
var website: String = ""
var image: String = ""

@ExperimentalCoilApi
@Composable
fun SignUpScreen(navController: NavHostController, homeViewModel: HomeViewModel,activity: Activity) {
    val scrollState = rememberScrollState()
    val gradient = Brush.horizontalGradient(listOf(colorResource(id = R.color.orange), colorResource(id = R.color.pink)))
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )
    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
            image = capturedImageUri.toString()
        }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, context.resources.getString(R.string.permission_granted), Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, context.resources.getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
        }
    }
    image = ""
    BackHandler{
        activity.finishAffinity()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        val modifier = Modifier.padding(it)
        Column(modifier = modifier.verticalScroll(state = scrollState),
        ) {
            Text(modifier = modifier.padding(20.dp,20.dp,0.dp,0.dp),
                text = stringResource(R.string.profile_creation), color = MaterialTheme.colors.surface,
                style = MaterialTheme.typography.h1)
            Text(modifier = modifier.padding(20.dp,5.dp,0.dp,0.dp),
                text = stringResource(R.string.use_the_form_below), color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body1)
            Box(modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .align(CenterHorizontally)) {
                Box(modifier = Modifier
                    .align(Center)
                    .height(180.dp)
                    .width(130.dp)
                    .clickable {
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(uri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                    .background(
                        color = colorResource(id = R.color.light_gray),
                        shape = RoundedCornerShape(15.dp)
                    )){
                    Text(modifier = modifier.align(Center),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.tap_to_add_avatar), color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.body1)
                }
                Image(
                    modifier = Modifier
                        .align(Center)
                        .height(200.dp)
                        .width(150.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    painter = rememberImagePainter(capturedImageUri),
                    contentDescription = null
                )
            }

            CustomTextField(
                modifier = Modifier
                    .padding(20.dp, 10.dp, 20.dp, 0.dp)
                    .fillMaxWidth(),
                labelResId = R.string.first_name,
                inputWrapper = firstName,
                isPassword = false,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                maxLength = 100,
                maxLines = 1
            ) {
                firstName = it
            }
            CustomTextField(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                labelResId = R.string.address_email,
                isPassword = false,
                inputWrapper = email,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                maxLength = 100,
                maxLines = 1
            ) {
                email = it
            }
            CustomTextField(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                labelResId = R.string.password,
                isPassword = true,
                inputWrapper = password,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                maxLength = 100,
                maxLines = 1,
            ) {
                password = it
            }
            CustomTextField(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                labelResId = R.string.website,
                inputWrapper = website,
                isPassword = false,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                maxLength = 100,
                maxLines = 1
            ) {
                website = it
            }

            GradientButton(
                text = stringResource(R.string.submit),
                gradient = gradient,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                if(email.isNotEmpty() && isValidEmail(email)){
                    if(password.isNotEmpty()){
                        //Register
                        val userEntity = UserEntity(
                            id = userId,
                            firstName = firstName,
                            email = email,
                            password = password,
                            website = website,
                            image = image
                        )
                        addUserInDB(navController, userEntity, homeViewModel)
                        navController.navigate(route = Screen.Confirmation.passUserId(userId.toString()))
                    }else{
                        Toast.makeText(context, context.resources.getString(R.string.password_is_empty), Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context, context.resources.getString(R.string.email_is_invalid), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun GradientButton(
    text: String,
    gradient : Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .then(modifier),
            contentAlignment = Center,
        ) {
            Text(text = text, color = MaterialTheme.colors.background,
                        style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier,
    keyboardOptions: KeyboardOptions = remember { KeyboardOptions.Default },
    inputWrapper: String,
    @StringRes labelResId: Int,
    isPassword : Boolean,
    maxLength: Int,
    maxLines: Int,
    onTextChanged: (String) -> Unit
) {
    var fieldValue by remember { mutableStateOf(inputWrapper) }
    val focusManager = LocalFocusManager.current
    Column {
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.primary,
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = colorResource(id = R.color.gray)),
            value = fieldValue,
            label = { Text(stringResource(labelResId), style = MaterialTheme.typography.caption, color = colorResource(id = R.color.gray)) },
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            modifier = modifier,
            visualTransformation = if (!isPassword) VisualTransformation.None
            else PasswordVisualTransformation(),
            onValueChange = {
                if (it.length <= maxLength) {
                    fieldValue = it
                    onTextChanged(it)
                }
            },
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onDone = {
                    focusManager.clearFocus()
                }
            ),
        )

    }
}


fun isValidEmail(str: String): Boolean {
    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    return emailPattern.matcher(str).matches()
}

fun addUserInDB(
    navController: NavHostController,
    userEntity: UserEntity,
    homeViewModel: HomeViewModel
) {
    homeViewModel.addUser(userEntity)
    navController.popBackStack()
}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}

fun showErrorMessage(context: Context, message : String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}