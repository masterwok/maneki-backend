package domain.usecases

import domain.models.User
import domain.repositories.user_repository.UserRepository

class QueryUserById(
    private val userRepository: UserRepository
) : CommandUseCaseWithParam<Int, User?>() {
    override suspend fun invoke(param: Int) = userRepository.queryUserById(param)
}
