package dev.maneki.di

import dev.maneki.common.extensions.init
import dev.maneki.data.Database
import features.users.repositories.UserRepository
import features.users.repositories.UserRepositoryImpl
import features.users.usecases.CreateUserUseCase
import features.users.usecases.QueryUserByIdUseCase
import features.users.usecases.QueryUsersUseCase
import io.ktor.server.application.*
import org.koin.dsl.module


fun appModule(application: Application) = module {
    single { Database.init(application) }
    single { get<Database>().userQueries }

    single<UserRepository> { UserRepositoryImpl(get()) }

    single { CreateUserUseCase(get()) }
    single { QueryUserByIdUseCase(get()) }
    single { QueryUsersUseCase(get()) }
}
