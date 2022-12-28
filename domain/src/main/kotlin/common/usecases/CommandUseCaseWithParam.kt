package common.usecases

abstract class CommandUseCaseWithParam<T, R> {
    abstract suspend operator fun invoke(param: T): R
}
