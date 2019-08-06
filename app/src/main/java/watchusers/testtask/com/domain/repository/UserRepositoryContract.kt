package watchusers.testtask.com.domain.repository

import io.reactivex.*
import watchusers.testtask.com.data.model.PageInformation
import watchusers.testtask.com.data.model.User
import watchusers.testtask.com.data.model.UserResponse
import watchusers.testtask.com.data.model.UsersResponse

interface UserRepositoryContract {

    fun getUsers(page: Int, limit: Int): Single<UsersResponse>

    fun getUser(userId: Int): Single<UserResponse>

    fun getUserFromDB(userId: Int): Flowable<User>

    fun getUsersFromDB(limit: Int, page: Int, positionRestoreLoad: Boolean): Flowable<List<User>>

    fun saveUserToDB(userEntity: User): Completable

    fun saveUsersToDB(userEntity: UsersResponse)

    fun getPageInformationFromDB(): PageInformation?

    fun clear(): Completable
}