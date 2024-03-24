package features.authentication.repositories

import features.authentication.models.RefreshToken
import features.authentication.models.SetUserRefreshTokenModel
import kotlinx.coroutines.flow.Flow

interface RefreshTokenRepository {
    suspend fun queryRefreshToken(token: String): RefreshToken?
    suspend fun setUserRefreshToken(setUserRefreshTokenModel: SetUserRefreshTokenModel)

}
