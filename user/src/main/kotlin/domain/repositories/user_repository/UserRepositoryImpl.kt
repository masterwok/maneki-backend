package domain.repositories.user_repository

import common.utils.HashUtil
import dev.maneki.data.UserQueries
import domain.models.CreateUserModel
import domain.models.User
import domain.models.from
import domain.repositories.exceptions.CreateUserUnknownException
import domain.repositories.exceptions.UserAlreadyExistsException
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
            throw UserAlreadyExistsException(ex)
        } catch (ex: Exception) {
            throw CreateUserUnknownException(ex)
        }
    }

    override suspend fun queryUserById(id: Int): User? = userQueries
        .selectById(id)
        .executeAsOneOrNull()
        ?.let(User::from)

    override suspend fun queryUserByEmail(email: String): User? = queryUserDaoByEmail(email)?.let(User::from)

    private fun queryUserDaoByEmail(email: String): dev.maneki.data.User? {
        return userQueries
            .selectByEmail(email)
            .executeAsOneOrNull()
    }

    override suspend fun queryUsers(): List<User> = userQueries
        .users()
        .executeAsList()
        .map(User.Companion::from)

    override suspend fun validatePassword(email: String, password: String): Boolean {
        return queryUserDaoByEmail(email)?.let {
            HashUtil.verifyPassword(password, it.password)
        } ?: false
    }
}
