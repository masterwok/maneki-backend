package domain.usecases

import domain.aliases.TokenFactory
import domain.models.SetUserRefreshTokenModel
import domain.models.Token
import domain.repositories.user_repository.UserRepository

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
