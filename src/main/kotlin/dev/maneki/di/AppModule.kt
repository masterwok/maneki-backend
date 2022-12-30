package dev.maneki.di

import common.extensions.init
import dev.maneki.data.Database
import dev.maneki.utils.JwtUtil
import features.authentication.repositories.RefreshTokenRepository
import features.authentication.repositories.RefreshTokenRepositoryImpl
import features.authentication.usecases.Login
import features.authentication.usecases.QueryUserRefreshTokenByEmail
import features.authentication.usecases.SetUserRefreshToken
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
    single { get<Database>().refreshTokenQueries }

    single<UserRepository> { UserRepositoryImpl(get()) }
    single<RefreshTokenRepository> { RefreshTokenRepositoryImpl(get()) }

    // Users
    single { CreateUser(get()) }
    single { QueryUserById(get()) }
    single { QueryUserByEmail(get()) }
    single { QueryUsers(get()) }

    // Authentication
    single { JwtUtil.createToken }
    single { JwtUtil.validateToken }
    single { QueryUserRefreshTokenByEmail(get()) }
    single { Login(get(), get(), get()) }
    single { SetUserRefreshToken(get()) }
}
