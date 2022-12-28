package features.users.usecases

import common.usecases.QueryUseCaseWithParam
import features.users.models.User
import features.users.repositories.UserRepository

class QueryUserByEmail(
    private val userRepository: UserRepository
) : QueryUseCaseWithParam<String, User?>() {
    override suspend fun invoke(param: String) = userRepository.queryUserByEmail(param)
}
