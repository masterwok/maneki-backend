package features.users.usecases

import domain.usecases.CommandUseCase
import features.users.models.User
import features.users.repositories.UserRepository

class QueryUsers(
    private val userRepository: UserRepository
) : CommandUseCase<List<User>>() {
    override suspend fun invoke(): List<User> = userRepository.queryUsers()

}
