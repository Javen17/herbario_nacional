package ni.abril.azb.megaboicotapp.data.network.headerInterceptor

import ni.abril.azb.megaboicotapp.preferences.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(var refreshToken: String, var permanentToken: String, var accessToken: String) : Interceptor {
    companion object ApiConstants{
        const val SET_SESSION = "/api/setSession"
        const val GET_DEPARTMENTS_WITH_BUSINESS = "/secured/obtainDepartmentsWithBusiness"
        const val GET_DEPARTMENTS = "/secured/obtainAllDepartments"
        const val GET_BUSINESS = "/secured/obtainAllBusiness"
        const val SEARCH_BUSINESS = "/secured/searchBusiness"
        const val PLATFORM = "M0b1l3"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        when (request.url().url().path) {
            SET_SESSION -> {
                request = request
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("platform", PLATFORM)
                    .addHeader("platformImei", "517449654288856")
                    .build()
            }
            GET_DEPARTMENTS -> {
                request = request
                    .newBuilder()
                    .addHeader("Authorization", authToken)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("platform", PLATFORM)
                    .addHeader("platformImei", "517449654288856")
                    .addHeader("Cookie", AppPreferences().get(
                        AppPreferences.Key.mega_token, "") as String)
                    .build()
            }

            GET_DEPARTMENTS_WITH_BUSINESS -> {
                request = request
                    .newBuilder()
                    .addHeader("Authorization", authToken)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("platform", PLATFORM)
                    .addHeader("platformImei", "517449654288856")
                    .addHeader("Cookie", AppPreferences().get(
                        AppPreferences.Key.mega_token, "") as String)
                    .build()
            }
            GET_BUSINESS -> {
                request = request
                    .newBuilder()
                    .addHeader("Authorization", authToken)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("platform", PLATFORM)
                    .addHeader("platformImei", "517449654288856")
                    .addHeader("Cookie", AppPreferences().get(
                        AppPreferences.Key.mega_token, "") as String)
                    .build()
            }
            SEARCH_BUSINESS -> {
                request = request
                    .newBuilder()
                    .addHeader("Authorization", authToken)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("platform", PLATFORM)
                    .addHeader("platformImei", "517449654288856")
                    .addHeader("Cookie", AppPreferences().get(
                        AppPreferences.Key.mega_token, "") as String)
                    .build()
            }
            else -> {
                request = request
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
            }
        }
        return chain.proceed(request)
    }
}
