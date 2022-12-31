package features.authentication.repositories

import features.authentication.models.RefreshToken
import features.authentication.models.SetUserRefreshTokenModel
import kotlinx.coroutines.flow.Flow

interface RefreshTokenRepository {
    fun queryRefreshToken(token: String): Flow<RefreshToken?>
    suspend fun setUserRefreshToken(setUserRefreshTokenModel: SetUserRefreshTokenModel)

}
