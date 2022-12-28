package common.usecases

abstract class CommandUseCaseWithParam<T: Any, R : Any> {
    abstract suspend operator fun invoke(param: T): R
}
