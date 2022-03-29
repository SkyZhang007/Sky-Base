package com.sky.lux.utils

/**
 * Standard tool library based on Kotlin.
 */

inline fun <T> repeat(same: (t: T?) -> Unit, vararg diff: T?) {
    diff.forEach {
        same(it)
    }
}

inline fun <T> repeatAction(same: (diff: () -> T?) -> Unit, vararg diff: () -> T?) {
    diff.forEach {
        same(it)
    }
}

inline fun <T> `if`(bool: Boolean?, block: () -> T): Condition<T> {
    (bool == true).run {
        if (this) {
            val r = block()
            return Condition(this, r)
        }
        return Condition(this, null)
    }
}

inline fun <T, E, R> Condition<T>.`else`(block: () -> E): R? where T : R, E : R {
    return if (!this.condition) {
        block()
    } else {
        this.result
    }
}

class Condition<T>(val condition: Boolean, val result: T?)

inline fun ifElse(condition: Boolean?, trueBlock: () -> Unit, elseBlock: () -> Unit) {
    `if`(condition == true) {
        trueBlock()
    }.`else`(elseBlock)
}

inline fun ifNotNull(vararg objArray: Any?, block: () -> Unit) {
    objArray.forEach {
        it ?: return
    }
    block()
}

inline fun ifHasNull(vararg objArray: Any?, block: () -> Unit) {
    objArray.forEach {
        if (it == null) {
            block()
            return
        }
    }
}

@JvmOverloads
fun Boolean?.nullOr(dft: Boolean = false) = this ?: dft

inline fun <T> T.`if`(cond: T.() -> Boolean, then: T.() -> Unit) {
    if (cond()) {
        then()
    }
}