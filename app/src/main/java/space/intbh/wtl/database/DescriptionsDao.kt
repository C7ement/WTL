package space.intbh.wtl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DescriptionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(description: DescriptionData)

    @Query("SELECT * FROM descriptions WHERE flowerName = :flowerName")
    fun getById(flowerName: String): DescriptionData?

    @Query("DELETE FROM descriptions")
    fun clear()
}
