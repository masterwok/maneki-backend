package features.authentication.usecases

import common.usecases.CommandUseCaseWithParam
import features.authentication.models.RefreshToken
import kotlinx.coroutines.flow.firstOrNull

class RefreshAuthToken(
    private val queryUserRefreshTokenByEmail: QueryUserRefreshTokenByEmail,
) : CommandUseCaseWithParam<RefreshAuthTokenParam, String>() {
    override suspend fun invoke(param: RefreshAuthTokenParam): String {
        val refreshToken = queryUserRefreshTokenByEmail(param.userEmail).firstOrNull()

        if (refreshToken == null) {
            throw Exception("No refresh token exists")
        }

        TODO()

    }
}

data class RefreshAuthTokenParam(
    val userEmail: String,
    val refreshToken: String,
)
