package features.authentication.repositories

import at.favre.lib.crypto.bcrypt.BCrypt
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import common.utils.HashUtil
import dev.maneki.data.Database
import dev.maneki.data.RefreshTokenQueries
import dev.maneki.data.Refresh_token
import features.authentication.models.RefreshToken
import features.authentication.models.SetUserRefreshTokenModel
import features.users.extensions.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext

class RefreshTokenRepositoryImpl(
    private val refreshTokenQueries: RefreshTokenQueries,
) : RefreshTokenRepository {
    override fun queryRefreshToken(token: String): Flow<RefreshToken?> {
        return refreshTokenQueries
            .selectByToken(HashUtil.hash(token))
            .asFlow()
            .mapToOneOrNull()
            .map { entity -> entity?.let(RefreshToken::from) }
    }

    override suspend fun setUserRefreshToken(
        setUserRefreshTokenModel: SetUserRefreshTokenModel
    ) = withContext(Dispatchers.IO) {
        with(setUserRefreshTokenModel) {
            refreshTokenQueries.insertOrUpdate(
                Refresh_token(
                    userId,
                    HashUtil.hash(token),
                    expiresOn
                )
            )
        }
    }
}


