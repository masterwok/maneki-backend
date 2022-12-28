package features.users.usecases

import common.usecases.CommandUseCaseWithParam
import features.users.models.CreateUserModel
import features.users.models.User
import features.users.repositories.UserRepository

class CreateUser(
    private val userRepository: UserRepository
) : CommandUseCaseWithParam<CreateUserModel, User>() {
    override suspend fun invoke(param: CreateUserModel) = userRepository.createUser(param)
}
