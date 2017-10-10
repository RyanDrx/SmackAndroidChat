package com.ryandrx.smack.Model

/**
 * Created by RyanDrx on 10/9/2017.
 */
class Channel(val name: String, val description: String, val id: String) {
    override fun toString(): String {
        return "#$name"
    }
}