import com.muryno.model.CurrentWeatherData
import com.muryno.model.SubsequenceWeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {


    @GET("forecast")
    fun getSubWeather(@Query("lat") lat: Double?,@Query("lon") lon: Double?,@Query("appid") appkey: String?): Call<SubsequenceWeatherData>

    @GET("forecast")
    fun getCurrentWeather(@Query("q") q: String?,@Query("appid") appkey: String?): Call<CurrentWeatherData>
}