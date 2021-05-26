package kotlintest.hyoseung.ch7_board

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_button_login.setOnClickListener {
            if(main_edit_id.length() > 0 && main_edit_pw.length() > 0){
                var id = main_edit_id.text.toString()
                var pw = main_edit_pw.text.toString()
                //logintask().execute(getString(R.string.loginurl),id,pw)
            }
            else
                Toast.makeText(applicationContext,"아이디와 패스워드를 1글자 이상 입력해주세요",Toast.LENGTH_SHORT).show()
        }

        main_button_signup.setOnClickListener {
            startActivity(Intent(applicationContext, SignupActivty::class.java))
        }
    }

    inner class logintask : AsyncTask<String?, Void, String?>(){
        override fun doInBackground(vararg params: String?): String? {
            var client = OkHttpClient()
            var url = params[0]
            var id = params[1]
            var pw = params[2]

            var response : String?

            try{
                var okhttpApi = Okhttp()
                response = okhttpApi.POST(client,url,okhttpApi.jsonbody(id, pw))
            } catch (e: IOException){
                return e.toString()
            }

            return response
        }

        override fun onPostExecute(result: String?) {
            if(result?.get(0) != '{'){
                Toast.makeText(applicationContext,"통신 에러",Toast.LENGTH_SHORT).show()
            }
            else{
                var jsonObject = JSONObject(result)
                if(jsonObject.getInt("result") == 1){
                    Toast.makeText(applicationContext,"로그인 성공",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext,"아이디가 없습니다",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
