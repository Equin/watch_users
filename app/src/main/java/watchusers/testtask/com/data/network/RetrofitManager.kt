package watchusers.testtask.com.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import watchusers.testtask.com.data.model.UserResponse
import watchusers.testtask.com.data.model.UsersResponse

interface RetrofitManager {

    @GET(ApiConfig.GET_USERS)
    fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") limit: Int
    ): Single<UsersResponse>

    @GET(ApiConfig.GET_USER + "/{userId}")
    fun getUser(
        @Path("userId") userId: Int
    ): Single<UserResponse>
}
