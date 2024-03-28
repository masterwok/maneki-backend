package domain.usecases

import domain.models.Token

//package domain.usecases
//
//import domain.aliases.TokenFactory
//import domain.models.SetUserRefreshTokenModel
//import domain.models.Token
//import domain.models.TokenPayload
//import domain.repositories.user_repository.UserRepository
//
//class domain.usecases.Login(
//    private val tokenFactory: TokenFactory,
//    private val setUserRefreshToken: SetUserRefreshToken,
//) : CommandUseCaseWithParam<domain.usecases.LoginParam, Token?>() {
//
//    override suspend fun invoke(param: domain.usecases.LoginParam): Token? {
//        val isValid = userRepository.validatePassword(param.email, param.password)
//
//        if (!isValid) return null
//
//        val payload = TokenPayload(
//            userId = 1,
//            email = "",
//            firstName = "",
//            lastName = "",
//        )
//
//        val authToken = tokenFactory.create(payload)
//
//        val setUserRefreshTokenModel = with(authToken.refreshToken) {
//            SetUserRefreshTokenModel(
//                user.id,
//                token,
//                expiresOn,
//            )
//        }
//
//        setUserRefreshToken(setUserRefreshTokenModel)
//
//        return authToken
//    }
//}

data class LoginParam(
    val email: String,
    val password: String,
)


class Login : CommandUseCaseWithParam<LoginParam, Token>() {
    override suspend fun invoke(param: LoginParam): Token {
        val x = 1
        throw Exception("derp")
    }

}