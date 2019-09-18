package hr.ferit.matejmijic.handymancroatia.persistence

import hr.ferit.matejmijic.handymancroatia.model.BusinessUser
import kotlin.concurrent.timerTask

object BusinessUserRepo {

    private val users = mutableListOf<BusinessUser>()
    private var currentId = 0

//    fun save(
//        userId: String?,
//        email: String?,
//        nickname: String?,
//        phoneNumber: String?,
//        accType: String?,
//        location: String?,
//        jobType: String?,
//        workTerritory: String?): BusinessUser {
//        val businessUser = BusinessUser(currentId,userId, email, nickname, phoneNumber, accType, location, jobType, workTerritory)
//        businessUser.id = currentId
//        users.add(businessUser)
//        currentId++
//        return businessUser
//    }

    fun add(user: BusinessUser){
        users.add(user)
    }
    fun deleteALL(){
        users.clear()
    }
    fun count() = users.size

    fun get(id: String): BusinessUser {
        return users.first {it.userId == id}
    }

    fun getAllUsers() = users
}