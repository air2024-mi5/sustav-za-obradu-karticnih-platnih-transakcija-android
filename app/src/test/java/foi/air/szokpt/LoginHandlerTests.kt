import foi.air.szokpt.helpers.LoginHandler
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class LoginHandlerTests {
    private lateinit var loginHandler: LoginHandler

    private var loginResult: String? = null

    private val onSuccessfulLogin = { username: String ->
        loginResult=("success:$username")
    }

    private val onFailure = { errorMessage: String ->
        loginResult=("failure:$errorMessage")
    }

    private val setAwaitingResponse = { isAwaiting: Boolean ->
    }

    @Test
    fun `login with invalid credentials`() {
        loginHandler = LoginHandler(onSuccessfulLogin, onFailure, setAwaitingResponse)
        loginResult = null

        loginHandler.login("", "")

        Thread.sleep(2000)

        assertNotNull(loginResult)
        assertEquals("failure:Neuspješna prijava", loginResult)
    }

    @Test
    fun `login with valid credentials`() {
        loginHandler = LoginHandler(onSuccessfulLogin, onFailure, setAwaitingResponse)
        loginResult = null

        loginHandler.login("admin", "admin")

        Thread.sleep(2000)

        assertNotNull(loginResult)
        assertEquals("success:admin", loginResult)
    }
}