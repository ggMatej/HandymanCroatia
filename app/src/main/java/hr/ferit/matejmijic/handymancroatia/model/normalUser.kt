package hr.ferit.matejmijic.handymancroatia.model

data class NormalUser(
    val userId: String? = null,
    val email: String? = null,
    val nickname: String? = null,
    val phoneNumber: String? = null,
    val accType: String? = null,
    val location: String? = null
) {
}