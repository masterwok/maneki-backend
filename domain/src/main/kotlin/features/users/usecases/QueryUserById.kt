package features.users.usecases

import common.usecases.QueryUseCaseWithParam
import features.users.models.User
import features.users.repositories.UserRepository

class QueryUserById(
    private val userRepository: UserRepository
) : QueryUseCaseWithParam<Int, User?>() {
    override suspend fun invoke(param: Int) = userRepository.queryUserById(param)
}
