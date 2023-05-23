package com.example.wardrobe

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wardrobe.databinding.ActivityMainloginBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainLoginActivity : AppCompatActivity(){
    private val binding by lazy {ActivityMainloginBinding.inflate(layoutInflater)}
    private val firebaseAuth by lazy{ Firebase.auth}
    var backKeyPressedTime: Long =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.v("Acitivity", "메인액티비티, 로그인")

        binding.signupButton.setOnClickListener {

            Log.v("Click", "회원가입버튼 클릭")
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            Log.v("Click", "로그인버튼 클릭")
            val idEditText = findViewById<EditText>(R.id.idEditText).text.toString()
            val passwordEditText = findViewById<EditText>(R.id.passwordEditText).text.toString()
            signIn(idEditText,passwordEditText)
        }
    }
    public override fun onStart() {
        super.onStart()
        moveMainPage(firebaseAuth.currentUser)
    }

    private fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this, "로그인에 성공 하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    moveMainPage(firebaseAuth.currentUser)
                } else {
                    Toast.makeText(
                        this, "로그인에 실패 하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500){
            Toast.makeText(
                this, "뒤로가기 버튼을 한번 더 누르면 종료합니다.",
                Toast.LENGTH_SHORT
            ).show()
            backKeyPressedTime = System.currentTimeMillis()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500){
            finishAffinity()
        }
    }

}