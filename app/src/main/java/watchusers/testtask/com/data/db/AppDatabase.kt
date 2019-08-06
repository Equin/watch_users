package watchusers.testtask.com.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import watchusers.testtask.com.data.db.AppDatabase.Companion.DATABASE_VERSION
import watchusers.testtask.com.data.db.dao.UserDao
import watchusers.testtask.com.data.model.PageInformation
import watchusers.testtask.com.data.model.User

@Database(
    entities = [
        User::class,
        PageInformation::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao

    companion object {
        const val DATABASE_VERSION = 1
    }
}