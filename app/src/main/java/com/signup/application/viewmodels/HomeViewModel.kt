package com.signup.application.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.signup.application.database.UserEntity
import com.signup.application.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    fun getAllUsers(){
        userRepository.getAllUsers()
    }

    fun addUser(userEntity: UserEntity) {
        userRepository.addUser(userEntity)
        getAllUsers()
    }

    fun findUserById(userId: Int) {
        userRepository.findUserById(userId)
    }

    val userList: LiveData<List<UserEntity>> = userRepository.allUsers

    val foundUser: LiveData<UserEntity> = userRepository.foundUser

}