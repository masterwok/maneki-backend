package features.users.usecases

import domain.usecases.CommandUseCaseWithParam
import features.users.models.User
import features.users.repositories.UserRepository

class QueryUserByEmail(
    private val userRepository: UserRepository
) : CommandUseCaseWithParam<String, User?>() {
    override suspend fun invoke(param: String): User? = userRepository.queryUserByEmail(param)
}
