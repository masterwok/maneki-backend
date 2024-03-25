package domain.usecases

import domain.models.CreateUserModel
import domain.models.User
import domain.repositories.user_repository.UserRepository

class CreateUser(
    private val userRepository: UserRepository
) : CommandUseCaseWithParam<CreateUserModel, User>() {
    override suspend fun invoke(param: CreateUserModel) = userRepository.createUser(param)
}
