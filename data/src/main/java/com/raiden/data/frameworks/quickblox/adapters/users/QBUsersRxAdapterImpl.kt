package com.raiden.data.frameworks.quickblox.adapters.users

import com.quickblox.core.request.QBPagedRequestBuilder
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import com.raiden.data.frameworks.quickblox.adapters.utils.SimpleSingleEntityCallback
import io.reactivex.Single

class QBUsersRxAdapterImpl : QBUsersRxAdapter {

    override fun logIn(login: String, password: String): Single<QBUser> {
        return Single.create { emitter ->
            val qbUser = QBUser(login, password, login)
            val callback = SimpleSingleEntityCallback(emitter)
            QBUsers.signIn(qbUser).performAsync(callback)
        }
    }

    override fun getUsers(page: Int, perPage: Int): Single<ArrayList<QBUser>> {
        return Single.create { emitter ->
            val qbPagedRequestBuilder = QBPagedRequestBuilder().apply {
                setPerPage(perPage)
                setPage(page)
            }
            val callback = SimpleSingleEntityCallback(emitter)
            QBUsers.getUsers(qbPagedRequestBuilder).performAsync(callback)
        }
    }
}