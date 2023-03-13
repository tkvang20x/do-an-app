package com.example.do_an_app.model.group

data class Result(
    val created_by: String,
    val created_time: String,
    val description: String,
    val group_code: String,
    val group_name: String,
    val is_active: Boolean,
    val is_delete: Boolean,
    val modified_by: String,
    val modified_time: String
)