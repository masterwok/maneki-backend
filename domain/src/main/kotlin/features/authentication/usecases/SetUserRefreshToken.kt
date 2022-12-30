package features.authentication.usecases

import common.usecases.CommandUseCaseWithParam
import features.authentication.models.SetUserRefreshTokenModel
import features.authentication.repositories.RefreshTokenRepository

class SetUserRefreshToken(
    private val refreshTokenRepository: RefreshTokenRepository,
) : CommandUseCaseWithParam<SetUserRefreshTokenModel, Unit>() {
    override suspend fun invoke(param: SetUserRefreshTokenModel) {
        refreshTokenRepository.setUserRefreshToken(param)
    }
}
