package domain.repositories.refresh_token_respository

import domain.models.RefreshToken
import domain.models.SetUserRefreshTokenModel

interface RefreshTokenRepository {
    suspend fun queryRefreshToken(token: String): RefreshToken?
    suspend fun setUserRefreshToken(setUserRefreshTokenModel: SetUserRefreshTokenModel)

}
