package com.nico.myapplication

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        firstActivityBtn.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        saveBtn.setOnClickListener {
            if (user1EditText.text.toString().isNotEmpty()) {
                comparative()
                if(auxEditText.text.toString()==user1EditText.text.toString()){
                    showAlert()
                } else{
                    saveDates()
                }
                user1EditText.setText("")
                pass1EditText.setText("")
            }


        }
        recoveryBtn.setOnClickListener {
            if (user1EditText.text.toString().isNotEmpty()) {
                pass1EditText.setText("")
                db.collection("users").document(user1EditText.text.toString()).get()
                    .addOnSuccessListener {
                        pass1EditText.setText(it.get("password") as String?)
                    }
            }
        }
        delete1Btn.setOnClickListener {
            println("holaaaaaaaaaaa")


//            db.collection("users").document(user1EditText.text.toString()).delete()
//            user1EditText.setText("")
//            pass1EditText.setText("")

        }


    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Desea sustituir la contraseña existente?")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(applicationContext, "OK", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(applicationContext, "Cancelado", Toast.LENGTH_LONG).show()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun saveDates() {
        db.collection("users").document(user1EditText.text.toString()).set(
            hashMapOf(
                "usuario" to user1EditText.text.toString(),
                "password" to pass1EditText.text.toString()
            )
        )
    }

    private fun comparative() {
        auxEditText.text = ""
        val user: LiveData<User> = liveData{
            val data =
        }
        db.collection("users").document(user1EditText.text.toString()).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    auxEditText.text = (it.get("usuario") as String?)
                } else {
                    auxEditText.text = (null)
                }
            }
    }
}
