package com.vtorushin.core.resources

data class Resource<T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        @JvmStatic
        fun <T>loading() = Resource<T>(status = Status.LOADING, data = null, message = null)

        @JvmStatic
        fun <T>complete(data: T) = Resource(status = Status.COMPLETE, data = data, message = null)

        @JvmStatic
        fun <T>error(message: String) = Resource<T>(status = Status.ERROR, data = null, message = message)
    }
}