package dev.maneki.di

import common.aliases.CreateToken
import common.aliases.VerifyToken
import dev.maneki.common.extensions.init
import dev.maneki.data.Database
import dev.maneki.utils.JwtUtil
import features.authentication.usecases.Login
import features.users.repositories.UserRepository
import features.users.repositories.UserRepositoryImpl
import features.users.usecases.CreateUser
import features.users.usecases.QueryUserByEmail
import features.users.usecases.QueryUserById
import features.users.usecases.QueryUsers
import io.ktor.server.application.*
import org.koin.dsl.module


fun appModule(application: Application) = module {
    single { Database.init(application) }
    single { get<Database>().userQueries }

    single<UserRepository> { UserRepositoryImpl(get()) }

    single { CreateUser(get()) }
    single { QueryUserById(get()) }
    single { QueryUserByEmail(get()) }
    single { QueryUsers(get()) }

    single<CreateToken> { JwtUtil::createToken }
    single<VerifyToken> { JwtUtil::verifyToken }

    single { Login(get(), get()) }
}
