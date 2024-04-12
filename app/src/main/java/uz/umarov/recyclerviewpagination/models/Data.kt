package uz.umarov.recyclerviewpagination.models


import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("avatar")
    var avatar: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("first_name")
    var firstName: String? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("last_name")
    var lastName: String? = null

    constructor(avatar: String?, email: String?, firstName: String?, id: Int?, lastName: String?) {
        this.avatar = avatar
        this.email = email
        this.firstName = firstName
        this.id = id
        this.lastName = lastName
    }

    constructor()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Data

        if (avatar != other.avatar) return false
        if (email != other.email) return false
        if (firstName != other.firstName) return false
        if (id != other.id) return false
        if (lastName != other.lastName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = avatar?.hashCode() ?: 0
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (id ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        return result
    }


}