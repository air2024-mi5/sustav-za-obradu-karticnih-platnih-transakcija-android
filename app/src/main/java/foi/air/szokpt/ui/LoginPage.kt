package foi.air.szokpt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.R
import foi.air.szokpt.helpers.LoginHandler
import foi.air.szokpt.ui.components.LoginTextField
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.BackgroundColor
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.danger

@Composable
fun LoginPage(
    onSuccessfulLogin: (username: String) -> Unit
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var isAwaitingResponse by remember { mutableStateOf(false) }

    val loginHandler = LoginHandler(
        onSuccessfulLogin = { onSuccessfulLogin(username) },
        onFailure = { message ->
            errorMessage = message },
        setAwaitingResponse = { awaiting ->
            isAwaitingResponse = awaiting }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(200.dp)
            )

            Spacer(modifier = Modifier.height(60.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = BGLevelTwo)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(15.dp))

                    // polje za username
                    LoginTextField(
                        label = "Username",
                        value = username,
                        onValueChange = { username = it },
                        isPasswordField = false,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // polje za lozinku
                    LoginTextField(
                        label = "Password",
                        value = password,
                        onValueChange = { password = it },
                        isPasswordField = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // gumb za prijavu
                    Button(
                        onClick = { loginHandler.login(username, password) },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .width(150.dp),
                        enabled = !isAwaitingResponse
                    ) {
                        Text(text = "Login", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Help?",
                        color = Primary,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable { /* TODO: Dodati logiku za help */ }
                            .padding(top = 8.dp)
                    )

                    // tekst za ispis greške
                    Text(
                        text = errorMessage,
                        color = danger,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    LoginPage({})
}