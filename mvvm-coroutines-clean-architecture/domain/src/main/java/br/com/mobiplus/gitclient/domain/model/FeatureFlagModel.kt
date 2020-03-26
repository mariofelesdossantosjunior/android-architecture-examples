package br.com.mobiplus.gitclient.domain.model

import java.io.Serializable

data class FeatureFlagModel<DATA>(
    val enabled: Boolean,
    val data: DATA? = null
): Serializable