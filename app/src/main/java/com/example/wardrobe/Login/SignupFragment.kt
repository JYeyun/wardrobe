package com.example.wardrobe.Login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.wardrobe.Login.UserDTO
import com.example.wardrobe.databinding.FragmentAddclothesBinding
import com.example.wardrobe.databinding.FragmentLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignupFragment : Fragment(){
    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy {Firebase.auth}
    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbRef = Firebase.database.reference

        binding.signupOkButton.setOnClickListener {
            createAccount(binding.signupID.text.toString().trim(),binding.signupPassword.text.toString().trim(),
                binding.signupID.text.toString().trim())
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isValidEmail(target: String): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    // 비밀번호 6자리 유효성 검사
    private fun isValidPasswd(target: String): Boolean {
        return target.length >= 6
    }

    private fun createAccount(email: String, password: String, nickname: String) {

        //val checkPassword = binding.CheckPassword.text.toString()

        /*if(!isValidEmail(email)){ // 이메일이 유효하지 않다
            Toast.makeText(this, "유효하지 않은 이메일 형식입니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (!isValidPasswd(password)){ // 비밀번호가 6자리 미만이다
            Toast.makeText(this, "비밀번호는 6자리 이상으로 만들어주세요", Toast.LENGTH_SHORT).show()
            return
        }*/

        /*if(signupPassword.text.toString() != checkPassword){
            Log.v("check", signupPassword.text.toString())
            Log.v("check", checkPassword)
            Toast.makeText(this, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show()
            return
        }*/

        firebaseAuth.createUserWithEmailAndPassword(email, password)

    }

    private fun addUserToDB(nickname : String, email:String, uid : String){
        dbRef.child("user").child(uid).setValue(UserDTO(nickname = nickname, email = email, uid = uid))
    }

}