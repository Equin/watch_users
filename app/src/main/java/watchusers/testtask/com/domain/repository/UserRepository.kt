package watchusers.testtask.com.domain.repository

import io.reactivex.*
import watchusers.testtask.com.data.db.dao.UserDao
import watchusers.testtask.com.data.model.PageInformation
import watchusers.testtask.com.data.model.User
import watchusers.testtask.com.data.model.UserResponse
import watchusers.testtask.com.data.model.UsersResponse
import watchusers.testtask.com.data.network.RetrofitManager

class UserRepository(
    private val retrofitManager: RetrofitManager,
    private val userDao: UserDao
) : UserRepositoryContract {

    override fun getPageInformationFromDB(): PageInformation {
        return userDao.getPageInformation()
    }

    override fun saveUsersToDB(userEntity: UsersResponse) {
        userDao.saveUserAndPageInfo(userEntity)
    }

    override fun getUsers(page: Int, limit: Int): Single<UsersResponse> {
        return retrofitManager.getUsers(page, limit)
    }

    override fun getUser(userId: Int): Single<UserResponse> {
        return retrofitManager.getUser(userId)
    }

    override fun getUserFromDB(userId: Int): Flowable<User> {
        return userDao.getUserFlowable(userId)
    }

    override fun getUsersFromDB(limit: Int, page: Int, positionRestoreLoad: Boolean): Flowable<List<User>> {

        var offsetForPreviousPage = (page - 1) * limit
        var perPage = limit

        if (positionRestoreLoad) {
            perPage = limit * page
            offsetForPreviousPage = 0
        }

        return userDao.getUsersFlowable(perPage, offsetForPreviousPage).distinctUntilChanged()
    }

    override fun saveUserToDB(userEntity: User): Completable {
        return Completable.fromCallable { userDao.save(userEntity) }
    }

    override fun clear(): Completable {
        return userDao.clear()
    }
}