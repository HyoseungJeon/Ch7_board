package kotlintest.hyoseung.ch7_board

import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Okhttp {
    fun POST(client: OkHttpClient, url : String?, jsonbody:String?) : String?{
        var response : Response

        try{
            var request = Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"),jsonbody))
                    .build()
            response = client.newCall(request).execute()

            return response.body()?.string()
        } catch (e : IOException){
            return e.toString()
        }
        return null
    }

    fun jsonbody(id: String?, pw : String?): String?{
        var JSONObject = JSONObject()
        JSONObject.put("id", id)
        JSONObject.put("pw", pw)

        return JSONObject.toString()
    }
}