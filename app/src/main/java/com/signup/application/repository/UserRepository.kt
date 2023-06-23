package com.signup.application.repository

import androidx.lifecycle.MutableLiveData
import com.signup.application.database.UserDao
import com.signup.application.database.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(private val userDao: UserDao) {

    val allUsers = MutableLiveData<List<UserEntity>>()
    val foundUser = MutableLiveData<UserEntity>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addUser(newUser: UserEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.addUser(newUser)
        }
    }

    fun getAllUsers() {
        coroutineScope.launch(Dispatchers.IO) {
            allUsers.postValue(userDao.getAllUsers())
        }
    }

    fun findUserById(empId: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            foundUser.postValue(userDao.findUserById(empId))
        }
    }

}