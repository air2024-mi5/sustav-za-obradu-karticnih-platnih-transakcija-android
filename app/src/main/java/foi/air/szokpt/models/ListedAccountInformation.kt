package foi.air.szokpt.models

enum class AccountListRole {
    User,
    Admin
}

data class ListedAccountInformation(
    val name: String,
    val lastName: String,
    val userName: String,
    // TODO: Change to actual type of Role, not this enum class above
    val role: AccountListRole // Need to change to actual type of Role! This is temporary for proof of concept!
)
