package com.jm.moviesearch.common.extension

import android.os.Build
import android.text.Html

fun String.convertToHtml(): String =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(this).toString()
    }



fun String.convertToDirectors(): String {
    if(this.isEmpty()) return this

    var result: String = this.replace("|",",").run {
        if(this.last() == ',') {
            this.dropLast(1)
        }

        this
    }

    if(result.last() == ',') {
        result = result.dropLast(1)
    }


    return result.convertToHtml()
}

