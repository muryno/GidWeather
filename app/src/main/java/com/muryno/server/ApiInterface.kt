import com.muryno.model.CurrentWeatherData
import com.muryno.model.SubsequenceWeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {


    @GET("forecast")
    fun getWeatherByLatLon(@Query("lat") lat: Double?,@Query("lon") lon: Double?,@Query("appid") appkey: String?): Call<CurrentWeatherData>


    @GET("weather")
    fun getSubWeatherByLatLon(@Query("lat") lat: Double?,@Query("lon") lon: Double?,@Query("appid") appkey: String?): Call<SubsequenceWeatherData>



    @GET("forecast")
    fun getWeatherByQueary(@Query("q") q: String?,@Query("appid") appkey: String?): Call<CurrentWeatherData>

    @GET("forecast")
    fun getSubWeatherByQueary(@Query("q") q: String?,@Query("appid") appkey: String?): Call<SubsequenceWeatherData>
}