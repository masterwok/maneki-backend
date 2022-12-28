package features.users.usecases

import common.usecases.QueryUseCase
import features.users.models.User
import features.users.repositories.UserRepository

class QueryUsersUseCase(
    private val userRepository: UserRepository
) : QueryUseCase<List<User>>() {
    override suspend fun invoke() = userRepository.queryUsers()
}
