package hr.ferit.matejmijic.handymancroatia.model

import hr.ferit.matejmijic.handymancroatia.ui.auth.CreateAccWorkTerritory

data class BusinessUser(
    var id: Int = 0,
    val userId: String? = null,
    val email: String? = null,
    val nickname: String? = null,
    val phoneNumber: String? = null,
    val accType: String? = null,
    val location: String? = null,
    val jobType: String? = null,
    val workTerritory: String? = null
) {
}