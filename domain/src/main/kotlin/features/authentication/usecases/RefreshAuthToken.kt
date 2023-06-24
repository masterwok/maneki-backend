package features.authentication.usecases

import common.aliases.TokenFactory
import common.usecases.CommandUseCaseWithParam
import features.authentication.models.SetUserRefreshTokenModel
import features.authentication.models.Token
import features.users.usecases.QueryUserById
import kotlinx.coroutines.flow.firstOrNull

class RefreshAuthToken(
    private val queryUserRefreshToken: QueryUserRefreshToken,
    private val queryUserById: QueryUserById,
    private val tokenFactory: TokenFactory,
    private val setUserRefreshToken: SetUserRefreshToken,
) : CommandUseCaseWithParam<String, Token>() {
    override suspend fun invoke(param: String): Token {
        val persistedRefreshToken = queryUserRefreshToken(param).firstOrNull()
            ?: throw Exception("Token doesn't exist")

        val user = queryUserById(persistedRefreshToken.userId).firstOrNull()!!

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
