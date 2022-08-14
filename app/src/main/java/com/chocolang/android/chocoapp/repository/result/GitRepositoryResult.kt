package com.chocolang.android.chocoapp.repository.result

import com.chocolang.android.chocoapp.repository.model.GitRepository

data class GitRepositoryResult(
    var total_count: Int,
    var items: List<GitRepository>
)
