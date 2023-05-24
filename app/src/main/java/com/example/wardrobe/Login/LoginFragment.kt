package com.example.wardrobe.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.wardrobe.MainActivity
import com.example.wardrobe.R
import com.example.wardrobe.SignUpActivity
import com.example.wardrobe.databinding.ActivityMainloginBinding
import com.example.wardrobe.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(){
    private lateinit var binding: ActivityMainloginBinding
    private val firebaseAuth by lazy{ Firebase.auth}
    var backKeyPressedTime: Long =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("Acitivity", "메인액티비티, 로그인")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMainloginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupButton.setOnClickListener {

            Log.v("Click", "회원가입버튼 클릭")
            findNavController().navigate(R.id.action_loginFragment2_to_signupFragment)

        }

        binding.loginButton.setOnClickListener {
            Log.v("Click", "로그인버튼 클릭")
            val idEditText = binding.idEditText.text.toString()
            val passwordEditText = binding.passwordEditText.text.toString()
            signIn(idEditText,passwordEditText)
        }


    }

   override fun onStart() {
        super.onStart()
        moveMainPage(firebaseAuth.currentUser)
    }

    private fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            /*.addOnCompleteListener(this) { task ->
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
            }*/
    }
    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
        //    findNavController().navigate(R.id.action_loginFragment2_to_homeFragment)

        }
    }
    /*override fun onBackPressed() {
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
    }*/
    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()

    }
}