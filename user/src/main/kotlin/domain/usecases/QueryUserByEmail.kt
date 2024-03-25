package domain.usecases

import domain.models.User
import domain.repositories.user_repository.UserRepository

class QueryUserByEmail(
    private val userRepository: UserRepository
) : CommandUseCaseWithParam<String, User?>() {
    override suspend fun invoke(param: String): User? = userRepository.queryUserByEmail(param)
}
