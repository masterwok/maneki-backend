package dev.maneki.di

import common.extensions.init
import dev.maneki.data.Database
import domain.repositories.refresh_token_respository.RefreshTokenRepository
import utils.JwtUtil
import domain.repositories.refresh_token_respository.RefreshTokenRepositoryImpl
import domain.repositories.user_repository.UserRepository
import domain.repositories.user_repository.UserRepositoryImpl
import domain.usecases.*
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
    single { QueryUserRefreshToken(get()) }
    single { Login(get(), get(), get()) }
    single { SetUserRefreshToken(get()) }
    single { RefreshAuthToken(get(), get(), get(), get()) }
}
