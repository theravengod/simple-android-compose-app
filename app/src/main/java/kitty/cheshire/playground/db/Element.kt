package kitty.cheshire.playground.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Element(
    @PrimaryKey val id: Int,
    val data: String?
)