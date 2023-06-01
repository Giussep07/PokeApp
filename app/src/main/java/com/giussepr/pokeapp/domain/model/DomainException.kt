package com.giussepr.pokeapp.domain.model

open class DomainException(override val message: String = "") : Throwable(message)
data class ApiException(val code: Int, override val message: String) : DomainException()
