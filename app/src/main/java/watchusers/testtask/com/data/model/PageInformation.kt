package watchusers.testtask.com.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class PageInformation (
    @PrimaryKey
    val id:Int,
    val page:Int,
    @SerializedName("per_page")
    val perPage:Int,
    val total:Int,
    @SerializedName("total_pages")
    val totalPages:Int
): Serializable