package domain.usecases

import domain.models.SetUserRefreshTokenModel
import domain.repositories.refresh_token_respository.RefreshTokenRepository

class SetUserRefreshToken(
    private val refreshTokenRepository: RefreshTokenRepository,
) : CommandUseCaseWithParam<SetUserRefreshTokenModel, Unit>() {
    override suspend fun invoke(param: SetUserRefreshTokenModel) {
        refreshTokenRepository.setUserRefreshToken(param)
    }
}
