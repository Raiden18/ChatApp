package com.raiden.data.frameworks.adapters.users

import android.util.Log
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import com.raiden.data.frameworks.adapters.utils.SimpleSingleEntityCallback
import io.reactivex.Single

class QBUsersRxAdapterImpl : QBUsersRxAdapter {

    override fun logIn(login: String, password: String): Single<QBUser> {
        return Single.create { emitter ->
            val qbUser = QBUser(login, password, login)
            val callback = SimpleSingleEntityCallback(emitter)
            QBUsers.signIn(qbUser).performAsync(callback)
        }
    }
}