package dev.maneki.common.usecases

abstract class CommandUseCase {
    abstract suspend operator fun invoke()
}
