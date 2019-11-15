package ni.abril.azb.megaboicotapp.data.network.cookiesInterceptor

import ni.abril.azb.megaboicotapp.preferences.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty())
        {
            var i = 0
            val cookie = originalResponse.headers("Set-Cookie")[0].substringBefore(";")
            //val cookies = HashSet<String>()
            //println("LISTADO DE COOKIES: ${cookie}")
//            for (header in originalResponse.headers("Set-Cookie"))
//            {
//                println(header)
//                println("Iterando: $i")
//                //cookies.add(header)
//                i++
//            }
            //println("COOKIES: ${cookies}")
            AppPreferences()
                .put(AppPreferences.Key.mega_token, cookie)
        }
        return originalResponse
    }
}