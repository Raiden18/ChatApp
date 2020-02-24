package com.raiden.domain.models

class Dialog(
    val id: String,
    val userId: Int,
    val occupantIds: List<Int>
)