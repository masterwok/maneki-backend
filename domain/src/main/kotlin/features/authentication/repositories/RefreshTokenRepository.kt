package features.authentication.repositories

import features.authentication.models.RefreshToken
import features.authentication.models.SetUserRefreshTokenModel
import kotlinx.coroutines.flow.Flow

interface RefreshTokenRepository {
    fun queryRefreshTokenByUserEmail(email: String): Flow<RefreshToken?>

}
