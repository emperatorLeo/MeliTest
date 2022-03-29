package com.example.melitest.results.model

sealed class ResultsState
class ResultLoading(val showLoading: Boolean) : ResultsState()
object ResultNotFoundError : ResultsState()
object ResultServerError : ResultsState()