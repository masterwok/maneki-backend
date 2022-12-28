package features.authentication.usecases

import common.aliases.CreateToken
import common.usecases.CommandUseCaseWithParam
import features.users.repositories.UserRepository

class Login(
    private val createToken: CreateToken,
    private val userRepository: UserRepository,
) : CommandUseCaseWithParam<LoginParam, String?>() {

    override suspend fun invoke(param: LoginParam): String? {
        val isValid = userRepository.validatePassword(param.email, param.password)

        if (!isValid) return null

        return createToken()
    }
}

data class LoginParam(
    val email: String,
    val password: String,
)
