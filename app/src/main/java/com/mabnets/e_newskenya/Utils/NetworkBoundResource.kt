package com.mabnets.e_newskenya.Utils

import com.mabnets.e_newskenya.Network.Resource
import kotlinx.coroutines.flow.*

inline  fun <ResultType,RequestType> NetworkBoundResource (
    crossinline query: ()-> Flow<ResultType>,
    crossinline fetch: suspend ()-> RequestType,
    crossinline saveFetchResult: suspend (RequestType)->Unit,
    crossinline shouldfetch: (ResultType)-> Boolean = {true}
)= flow {
val data=query().first()

    val flow=if(shouldfetch(data)){
        emit(Resource.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        }catch (throwable:Throwable){
            query().map { Resource.Error(throwable,it) }

        }
    }else{
        query().map { Resource.Success(it) }
    }
    emitAll(flow)
}