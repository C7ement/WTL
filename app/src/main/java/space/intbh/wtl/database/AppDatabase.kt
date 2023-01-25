package space.intbh.wtl.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DescriptionData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun descriptionDataDao(): DescriptionsDao
}
