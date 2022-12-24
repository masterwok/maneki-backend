package dev.maneki.common.usecases

abstract class CommandUseCaseWithParam<T> {
    abstract suspend operator fun invoke(param: T)
}
