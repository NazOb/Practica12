package mx.edu.itesca.practica12

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth

        val et_mail: EditText = findViewById(R.id.et_mail)
        val et_password: EditText = findViewById(R.id.et_password)
        val tv_error_up: TextView = findViewById(R.id.tv_error)

        val btn_in: Button = findViewById(R.id.bnt_login)
        val btn_up: Button = findViewById(R.id.btn_logup)

        tv_error_up.visibility = View.INVISIBLE
        btn_in.setOnClickListener {
            login(et_mail.text.toString(),et_password.text.toString())
        }
        btn_up.setOnClickListener {
            val intent = Intent(this,logup::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    fun goToMain(user: FirebaseUser){
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("user", user.email)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    fun showError(text:String="",visible:Boolean){
        val tv_error_up: TextView = findViewById(R.id.tv_error)
        tv_error_up.setText(text)
        tv_error_up.visibility = if(visible) View.VISIBLE else View.INVISIBLE
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){
            goToMain(currentUser)
        }
    }

    fun login(mail:String,password:String){
        auth.signInWithEmailAndPassword(mail,password)
            .addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    showError(visible = false)
                    goToMain(user!!)
                }else{
                    showError("Usuario y/o Contaseña equivocados",true)
                }
            }
    }
}