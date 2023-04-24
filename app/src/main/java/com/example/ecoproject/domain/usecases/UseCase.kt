package com.example.ecoproject.domain.usecases

import kotlinx.coroutines.flow.Flow

interface UseCase<I, O> {
    operator fun invoke(input: I): Flow<O>
}