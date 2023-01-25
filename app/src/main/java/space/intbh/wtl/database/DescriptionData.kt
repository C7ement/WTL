package space.intbh.wtl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "descriptions")
data class DescriptionData(
    @PrimaryKey val flowerName: String,
    @ColumnInfo(name = "description") val description: String
)
