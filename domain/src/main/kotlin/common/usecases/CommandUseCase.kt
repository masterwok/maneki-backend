package common.usecases

abstract class CommandUseCase {
    abstract suspend operator fun invoke()
}
