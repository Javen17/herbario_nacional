package com.example.herbario_nacional.util

import com.example.herbario_nacional.base.BaseApplication

class StringResource {
    companion object {
        fun getStringFromResourcesByName(resourceName: String): String {
            val packageName = BaseApplication.context.packageName
            val resourceId = BaseApplication.context.resources.getIdentifier(resourceName,"string", packageName)
            return BaseApplication.context.getString(resourceId)
        }
    }
}
