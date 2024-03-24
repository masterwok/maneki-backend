package features.authentication.usecases

import domain.usecases.CommandUseCaseWithParam
import features.authentication.models.RefreshToken
import features.authentication.repositories.RefreshTokenRepository
import kotlinx.coroutines.flow.Flow

class QueryUserRefreshToken(
    private val refreshTokenRepository: RefreshTokenRepository,
) : CommandUseCaseWithParam<String, RefreshToken?>() {
    override suspend fun invoke(param: String): RefreshToken? = refreshTokenRepository.queryRefreshToken(param)
}
