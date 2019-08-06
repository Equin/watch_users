package watchusers.testtask.com.domain.interactors

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import watchusers.testtask.com.data.model.User
import watchusers.testtask.com.data.model.UsersResponse
import watchusers.testtask.com.domain.repository.UserRepositoryContract

class MainInteractor(
    private val userRepository: UserRepositoryContract
) {

    fun getUsers(page: Int, limit: Int, positionRestoreLoad: Boolean): Flowable<UsersResponse> {
        return userRepository.getUsersFromDB(limit, page, positionRestoreLoad)
            .flatMap {
                Flowable.fromCallable {
                    val pageInfo = userRepository.getPageInformationFromDB()
                    if (pageInfo == null) {
                        UsersResponse(0, 0, 0, 0, it)
                    } else {
                        UsersResponse(
                            pageInfo.page,
                            pageInfo.perPage,
                            pageInfo.total,
                            pageInfo.totalPages,
                            it
                        )
                    }
                }
            }.doOnSubscribe {
                userRepository.getUsers(page, limit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(
                        { userRepository.saveUsersToDB(it) },
                        { Exception(it) }
                    )
            }
    }

    fun getUser(userId: Int): Flowable<User> {
        return userRepository.getUserFromDB(userId)
            .doOnSubscribe {
                userRepository.getUser(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(
                        { userRepository.saveUserToDB(it.data) },
                        { Exception(it) }
                    )
            }
    }
}