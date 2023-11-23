package com.example.fillfullment.ui.data

data class User(
    val id: Int = 0,
    val username: String = "",
    val password: String = "",
    val status: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (other is User) {
            return other.username == username && other.password == password
        }

        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
