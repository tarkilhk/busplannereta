package com.tarkil.busplannereta.infrastructure.datapersistence.users

import org.springframework.data.repository.CrudRepository


interface UserRepository : CrudRepository<UserDao, Long> {

    fun findByName(name: String): UserDao

    fun existsByName(name: String): Boolean
}