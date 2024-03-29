package domain.repositories.exceptions

sealed class UserRepositoryException(
    override val message: String,
    cause: Throwable? = null,
) : Throwable(cause)

class UserAlreadyExistsException(cause: Throwable) : UserRepositoryException("Username already exists.", cause)

class CreateUserUnknownException(cause: Throwable) : UserRepositoryException("An unknown error occurred.", cause)
