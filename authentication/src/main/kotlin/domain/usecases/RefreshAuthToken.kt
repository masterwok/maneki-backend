package domain.usecases

import domain.aliases.TokenFactory
import domain.models.SetUserRefreshTokenModel
import domain.models.Token

class RefreshAuthToken(
    private val queryUserRefreshToken: QueryUserRefreshToken,
    private val queryUserById: QueryUserById,
    private val tokenFactory: TokenFactory,
    private val setUserRefreshToken: SetUserRefreshToken,
) : CommandUseCaseWithParam<String, Token>() {
    override suspend fun invoke(param: String): Token {
        val persistedRefreshToken = queryUserRefreshToken(param)
            ?: throw Exception("Token doesn't exist")

        val user = queryUserById(persistedRefreshToken.userId)!!

        val newToken = tokenFactory.create(user)

        setUserRefreshToken(
            SetUserRefreshTokenModel(
                user.id,
                newToken.refreshToken.token,
                newToken.refreshToken.expiresOn
            )
        )

        return newToken
    }
}
