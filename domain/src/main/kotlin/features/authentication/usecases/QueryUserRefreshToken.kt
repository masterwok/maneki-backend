package features.authentication.usecases

import common.usecases.QueryUseCaseWithParam
import features.authentication.models.RefreshToken
import features.authentication.repositories.RefreshTokenRepository
import kotlinx.coroutines.flow.Flow

class QueryUserRefreshToken(
    private val refreshTokenRepository: RefreshTokenRepository,
) : QueryUseCaseWithParam<String, RefreshToken?>() {
    override suspend fun invoke(param: String): Flow<RefreshToken?> {
        return refreshTokenRepository.queryRefreshToken(param)
    }
}
