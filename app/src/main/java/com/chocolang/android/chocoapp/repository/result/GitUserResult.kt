package com.chocolang.android.chocoapp.repository.result

import com.chocolang.android.chocoapp.repository.model.GitUser

data class GitUserResult(
    var total_count: Int,
    var items: List<GitUser>
)
