package com.namnp.androidjetpack.compose.sharing_data.util.persistent

interface SessionCache {

    fun saveSession(session: Session)

    fun getActiveSession(): Session?

    fun clearSession()
}