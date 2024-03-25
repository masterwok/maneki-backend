package domain.usecases

import domain.models.RefreshToken
import domain.repositories.refresh_token_respository.RefreshTokenRepository

class QueryUserRefreshToken(
    private val refreshTokenRepository: RefreshTokenRepository,
) : CommandUseCaseWithParam<String, RefreshToken?>() {
    override suspend fun invoke(param: String): RefreshToken? = refreshTokenRepository.queryRefreshToken(param)
}
