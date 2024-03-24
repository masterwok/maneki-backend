package domain.usecases

abstract class CommandUseCase<R> {
    abstract suspend operator fun invoke(): R
}
