package features.users.usecases

import common.usecases.CommandUseCaseWithParam
import features.users.models.User
import features.users.repositories.UserRepository

class QueryUserById(
    private val userRepository: UserRepository
) : CommandUseCaseWithParam<Int, User?>() {
    override suspend fun invoke(param: Int) = userRepository.queryUserById(param)
}
