package com.chocolang.android.chocoapp.repository.model

data class GitRepository(
    var id: Int,
    var name: String,
    var owner: GitOwner
)

data class GitOwner(
    var id: Int,
    var avatar_url: String,
    var type: String
)
