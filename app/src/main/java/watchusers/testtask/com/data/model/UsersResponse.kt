package watchusers.testtask.com.data.model

import androidx.room.Entity
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UsersResponse (
    val page:Int,
    @SerializedName("per_page")
    val perPage:Int,
    val total:Int,
    @SerializedName("total_pages")
    val totalPages:Int,
    val data: List<User>
): Serializable