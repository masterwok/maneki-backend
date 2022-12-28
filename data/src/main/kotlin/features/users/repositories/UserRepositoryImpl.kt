package features.users.repositories

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import dev.maneki.data.UserQueries
import common.utils.HashUtil
import features.users.extensions.from
import features.users.models.CreateUserModel
import features.users.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.sql.SQLIntegrityConstraintViolationException

class UserRepositoryImpl(
    private val userQueries: UserQueries,
) : UserRepository {
    override suspend fun createUser(user: CreateUserModel): User {
        try {
            return userQueries.transactionWithResult {
                userQueries.insertUser(
                    user.email,
                    HashUtil.hashPassword(user.password),
                    user.firstName,
                    user.lastName
                )

                userQueries.selectLastInsertedRowId().executeAsOne()
            }.let {
                User.from(userQueries.selectById(it.toInt()).executeAsOne())
            }
        } catch (ex: SQLIntegrityConstraintViolationException) {
            // TODO: Throw UserRepositoryException
            throw Exception("foo bar baz bax")
        }
    }

    // TODO (JT): Ensure null case is handled by replacing [mapToOne].
    override suspend fun queryUserById(id: Int): Flow<User> = userQueries
        .selectById(id)
        .asFlow()
        .mapToOne()
        .map(User.Companion::from)

    override suspend fun queryUsers(): Flow<List<User>> {
        return userQueries.users()
            .asFlow()
            .mapToList()
            .map { users -> users.map(User::from) }
    }
}
