package domain.usecases

import domain.models.User
import domain.repositories.user_repository.UserRepository

class QueryUsers(
    private val userRepository: UserRepository
) : CommandUseCase<List<User>>() {
    override suspend fun invoke(): List<User> = userRepository.queryUsers()

}
