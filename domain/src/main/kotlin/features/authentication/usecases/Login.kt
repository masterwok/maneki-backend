package features.authentication.usecases

import common.aliases.TokenFactory
import common.usecases.CommandUseCaseWithParam
import features.authentication.models.SetUserRefreshTokenModel
import features.authentication.models.Token
import features.users.repositories.UserRepository
import kotlinx.coroutines.flow.first

class Login(
    private val tokenFactory: TokenFactory,
    private val userRepository: UserRepository,
    private val setUserRefreshToken: SetUserRefreshToken,
) : CommandUseCaseWithParam<LoginParam, Token?>() {

    override suspend fun invoke(param: LoginParam): Token? {
        val isValid = userRepository.validatePassword(param.email, param.password)

        if (!isValid) return null

        val user = userRepository.queryUserByEmail(param.email) ?: return null
        val authToken = tokenFactory.create(user)
        val setUserRefreshTokenModel = with(authToken.refreshToken) {
            SetUserRefreshTokenModel(
                user.id,
                token,
                expiresOn,
            )
        }

        setUserRefreshToken(setUserRefreshTokenModel)

        return authToken
    }
}

data class LoginParam(
    val email: String,
    val password: String,
)
