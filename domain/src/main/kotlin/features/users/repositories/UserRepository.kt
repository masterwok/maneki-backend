package features.users.repositories

import features.users.models.CreateUserModel
import features.users.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: CreateUserModel): User
    fun queryUserById(id: Int): Flow<User?>
    fun queryUserByEmail(email: String): Flow<User?>
    fun queryUsers(): Flow<List<User>>
    suspend fun validatePassword(email: String, password: String): Boolean
}
