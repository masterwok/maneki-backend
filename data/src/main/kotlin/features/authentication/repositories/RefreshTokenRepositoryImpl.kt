package features.authentication.repositories

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import dev.maneki.data.RefreshTokenQueries
import features.authentication.models.RefreshToken
import features.authentication.models.SetUserRefreshTokenModel
import features.users.extensions.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RefreshTokenRepositoryImpl(
    private val refreshTokenQueries: RefreshTokenQueries,
) : RefreshTokenRepository {
    override fun queryRefreshTokenByUserEmail(email: String): Flow<RefreshToken?> {
        return refreshTokenQueries
            .selectByUserEmail(email)
            .asFlow()
            .mapToOneOrNull()
            .map { entity -> entity?.let(RefreshToken::from) }
    }

    override suspend fun setUserRefreshToken(setUserRefreshTokenModel: SetUserRefreshTokenModel) {
//        TODO("Not yet implemented")
    }
}
