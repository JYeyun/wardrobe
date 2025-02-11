package com.example.wardrobe

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wardrobe.databinding.FragmentDetailCodiBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class DetailCodiFragment : Fragment() {
    private lateinit var binding: FragmentDetailCodiBinding
    private lateinit var storage: FirebaseStorage
    private var storageImageRef = ""

    val db = Firebase.firestore
    // Set(코디) Collection Ref
    val setColRef = db.collection("set")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storage = Firebase.storage

        val bundle = arguments
        if (bundle != null) {
            storageImageRef = bundle.getString("imageRef", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailCodiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        setClothesInfo()

        binding.buttonEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("imageRef",storageImageRef)
            findNavController().navigate(R.id.action_detailCodiFragment_to_detailCodiEditFragment,bundle)
        }


    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()

    }

    private fun setClothesInfo(){

        if(!storageImageRef.equals("")){
            val imageRef = storage.reference.child(storageImageRef)
            imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                val bmp = BitmapFactory.decodeByteArray(it,0,it.size)
                binding.ivCodi.setImageBitmap(bmp)
            }
        }

        setColRef.whereEqualTo("imageRef",storageImageRef).get()
            .addOnSuccessListener {
                for(doc in it){
                    when(doc["season"]){
                        "spring&fall" -> binding.buttonSeasonSpringFall.isChecked = true
                        "summer" -> binding.buttonSeasonSummer.isChecked = true
                        "winter" -> binding.buttonSeasonWinter.isChecked = true
                    }
                    when(doc["public"]){ // 왜?
                        true -> binding.buttonPublic.isChecked = true
                        false -> binding.buttonPrivate.isChecked = true
                    }
                    if(doc["memo"].toString().isNullOrBlank())
                        binding.editTextMemo.setHint("메모를 입력해주세요.")
                    else
                        binding.editTextMemo.setText(doc["memo"].toString())

                    binding.editTextHashtag.text.clear()
                    val tempList = doc["hashtag"] as List<String>
                    if(tempList.isEmpty())
                        binding.editTextHashtag.setHint("해시태그를 #로 구분하여 입력해주세요.")
                    else{
                        tempList.forEach {
                            binding.editTextHashtag.text.append("#")
                            binding.editTextHashtag.text.append(it)
                        }
                    }
                }
        }
    }



}


