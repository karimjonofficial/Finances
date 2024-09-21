package com.orka.finances.features.home.entities

open class Category(private val id: Int, private var name: String) {
    fun toDto(): CategoryDto {
        return CategoryDto(id, name)
    }

    fun rename(name: String) {
        this.name = name
    }
}