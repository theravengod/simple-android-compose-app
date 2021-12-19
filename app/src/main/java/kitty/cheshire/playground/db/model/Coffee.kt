package kitty.cheshire.playground.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Coffee(
    @SerialName("uid") @PrimaryKey val uid: String,
    @SerialName("id") val id: Int,
    @SerialName("blend_name") val blendName: String,
    @SerialName("intensifier") val intensifier: String,
    @SerialName("notes") val notes: String,
    @SerialName("origin") val origin: String,
    @SerialName("variety") val variety: String
)