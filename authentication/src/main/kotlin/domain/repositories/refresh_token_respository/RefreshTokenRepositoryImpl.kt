package domain.repositories.refresh_token_respository

import common.utils.HashUtil
import dev.maneki.data.RefreshTokenQueries
import dev.maneki.data.Refresh_token
import domain.models.RefreshToken
import domain.models.SetUserRefreshTokenModel
import domain.models.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RefreshTokenRepositoryImpl(
    private val refreshTokenQueries: RefreshTokenQueries,
) : RefreshTokenRepository {
    override suspend fun queryRefreshToken(token: String): RefreshToken? {
        return refreshTokenQueries
            .selectByToken(HashUtil.hashSha256(token))
            .executeAsOneOrNull()
            ?.let(RefreshToken.Companion::from)
    }

    override suspend fun setUserRefreshToken(
        setUserRefreshTokenModel: SetUserRefreshTokenModel
    ) = withContext(Dispatchers.IO) {
        with(setUserRefreshTokenModel) {
            refreshTokenQueries.insertOrUpdate(
                Refresh_token(
                    userId,
                    HashUtil.hashSha256(token),
                    expiresOn
                )
            )
        }
    }
}


