package dev.maneki.common.usecases

import kotlinx.coroutines.flow.Flow

abstract class QueryUseCaseWithParam<T, R> {
    abstract suspend operator fun invoke(param: T): Flow<R>
}
