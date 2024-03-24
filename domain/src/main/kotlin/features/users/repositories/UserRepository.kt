package features.users.repositories

import features.users.models.CreateUserModel
import features.users.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: CreateUserModel): User
    suspend fun queryUserById(id: Int): User?
    suspend fun queryUserByEmail(email: String): User?
    suspend fun queryUsers(): List<User>
    suspend fun validatePassword(email: String, password: String): Boolean
}
