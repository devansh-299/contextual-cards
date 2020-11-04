package com.devansh.contextualcards.data

/**
 * A helper class to specify the domain names, endpoints and protocols of services consumed by
 * the application.
 */
object ApiHelper {
    private const val PROTOCOL = "http://"
    private const val DOMAIN_NAME = "www.mocky.io/"
    private const val VERSION = "v2"
    const val BASE_URL = "$PROTOCOL$DOMAIN_NAME$VERSION/"
    const val ENDPOINT = "5ed79368320000a0cc27498b/"
}