package kotlintest.hyoseung.ch7_board

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.IOException

class SignupActivty : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signup_button_signup.setOnClickListener {
            if(signup_edit_id.length() > 0 && signup_edit_pw.length() > 0 && signup_edit_pw2.length() > 0){
                if(signup_edit_pw.text.toString().equals(signup_edit_pw2.text.toString())){
                    //서버통신
                    var id = signup_edit_id.text.toString()
                    var pw = signup_edit_pw.text.toString()
                    //signuptask().execute(getString(R.string.loginurl),id,pw)
                    finish()
                }
                else
                    Toast.makeText(applicationContext,"1차 2차 비밀번호가 다릅니다",Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(applicationContext,"아이디와 비밀번호를 한글자 이상 입력해주세요",Toast.LENGTH_SHORT).show()
        }
    }

    inner class signuptask : AsyncTask<String?, Void, String?>(){
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
                    Toast.makeText(applicationContext,"회원가입 성공",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext,"아이디가 존재합니다",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}