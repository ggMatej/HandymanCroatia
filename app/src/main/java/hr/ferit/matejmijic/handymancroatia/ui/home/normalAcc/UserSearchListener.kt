package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import hr.ferit.matejmijic.handymancroatia.model.BusinessUser

interface UserSearchListener {
    fun onUserFound(users: MutableList<BusinessUser>)
}