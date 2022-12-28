package common.usecases

import kotlinx.coroutines.flow.Flow

abstract class QueryUseCase<R> {
    abstract suspend operator fun invoke(): Flow<R>
}
