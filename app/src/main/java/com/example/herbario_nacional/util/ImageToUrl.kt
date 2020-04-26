package com.example.herbario_nacional.util

class ImageToUrl {
    companion object {
        fun exportImageToURL(url: String): String {
            val exportURL = url.replace("file/d/", "uc?export=view&id=")
            return exportURL.replace("/view?usp=drivesdk", "")
        }
    }
}