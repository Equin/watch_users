package watchusers.testtask.com.data.db.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import watchusers.testtask.com.data.model.PageInformation
import watchusers.testtask.com.data.model.User
import watchusers.testtask.com.data.model.UsersResponse

@Dao
interface UserDao {

    @Transaction
    fun saveUserAndPageInfo(usersResponse: UsersResponse) {
        savePageInfo(
            PageInformation(
                1,
                usersResponse.page,
                usersResponse.perPage,
                usersResponse.total,
                usersResponse.totalPages
            )
        )

        for (user in usersResponse.data) {
            save(user)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePageInfo(pageInformation: PageInformation)

    @Query("SELECT * FROM User  LIMIT (:limit) OFFSET (:offsetForPreviousPage)")
    fun getUsersFlowable(limit: Int, offsetForPreviousPage: Int): Flowable<List<User>>

    @Query("SELECT * FROM User  WHERE User.id = :userId")
    fun getUserFlowable(userId: Int): Flowable<User>

    @Query("SELECT * FROM PageInformation ")
    fun getPageInformation(): PageInformation

    @Query("DELETE FROM user")
    fun clear(): Completable
}