package br.com.mobiplus.gitclient.domain

import br.com.mobiplus.gitclient.domain.base.resultwrapper.FullResultWrapper
import br.com.mobiplus.gitclient.domain.base.resultwrapper.ResultWrapper

class FakeResultWrapper {

    companion object {
        fun <SUCCESS, ERROR> mockSuccess(success: SUCCESS): ResultWrapper<SUCCESS, ERROR> {
            return ResultWrapper(
                success = success
            )
        }

        fun <SUCCESS, ERROR> mockError(error: ERROR): ResultWrapper<SUCCESS, ERROR> {
            return ResultWrapper(
                error = error
            )
        }

        fun <SUCCESS, ERROR> mockFullSuccess(success: SUCCESS): FullResultWrapper<SUCCESS, ERROR> {
            return FullResultWrapper(
                success = success
            )
        }

        fun <SUCCESS, ERROR> mockFullError(error: ERROR): FullResultWrapper<SUCCESS, ERROR> {
            return FullResultWrapper(
                error = error
            )
        }
    }
}