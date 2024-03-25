package domain.repositories.user_repository

import domain.models.CreateUserModel
import domain.models.User


interface UserRepository {
    suspend fun createUser(user: CreateUserModel): User
    suspend fun queryUserById(id: Int): User?
    suspend fun queryUserByEmail(email: String): User?
    suspend fun queryUsers(): List<User>
    suspend fun validatePassword(email: String, password: String): Boolean
}
