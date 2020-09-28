package kr.co.bakeapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.co.bakeapplication.views.DashboardActivity
import kr.co.bakeapplication.views.RecipeActivity

class MainActivity : AppCompatActivity() {

    private val _gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val _mGoogleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(application, _gso)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Firebase.auth.signOut()
        _mGoogleSignInClient.signOut()
        val intent = Intent(applicationContext, DashboardActivity::class.java)
        startActivity(intent)
    }
}
