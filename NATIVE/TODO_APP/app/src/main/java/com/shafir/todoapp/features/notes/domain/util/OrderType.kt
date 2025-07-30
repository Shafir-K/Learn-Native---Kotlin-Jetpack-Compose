package com.shafir.todoapp.features.notes.domain.util

sealed class OrderType {
    data object Ascending : OrderType()
    data object Descending : OrderType()
}