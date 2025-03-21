package mx.edu.itesca.practica12

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class logup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_logup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
        val et_mail_up:EditText = findViewById(R.id.et_mail_up)
        val et_password_up:EditText = findViewById(R.id.et_password_up)
        val et_password_confirm:EditText = findViewById(R.id.et_password_confirm)
        val tv_error_up:TextView = findViewById(R.id.tv_error_up)

        val btn_up:Button = findViewById(R.id.btn_up)

        tv_error_up.visibility= View.INVISIBLE

        btn_up.setOnClickListener {
            if(et_mail_up.text.isEmpty() || et_password_up.text.isEmpty() ||  et_password_confirm.text.isEmpty()){
                tv_error_up.setText("Todos los Campos Deben ser Llenados")
                tv_error_up.visibility=View.VISIBLE
            }else if(!et_password_up.text.toString().equals(et_password_confirm.text.toString())){
                tv_error_up.setText("Las Contraseñas no Coinciden")
                tv_error_up.visibility=View.VISIBLE
            }else{
                tv_error_up.visibility= View.INVISIBLE
                logUp(et_mail_up.text.toString(),et_password_up.text.toString())
            }
        }

    }
    fun logUp(mail:String,password:String){
        Log.d("INFO","email: ${mail}, password: ${password}")
        auth.createUserWithEmailAndPassword(mail,password)
            .addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    Log.d("INFO","singInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this,MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }else{
                    Log.w("ERROR","singInWithEmail:failure",task.exception)
                    Toast.makeText(baseContext, "El registro Fallo", Toast.LENGTH_SHORT).show()
                }
            }
    }
}