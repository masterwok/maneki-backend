package features.users.repositories

import features.users.models.CreateUserModel
import features.users.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: CreateUserModel): User
    suspend fun queryUserById(id: Int): Flow<User?>
    suspend fun queryUsers(): Flow<List<User>>
}
