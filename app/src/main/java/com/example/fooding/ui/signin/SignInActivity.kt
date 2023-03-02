package com.example.fooding.ui.signin

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.fooding.BuildConfig
import com.example.fooding.HomeActivity
import com.example.fooding.databinding.ActivitySigninBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar

private typealias SignInSuccessListener = (idToken: String) -> Unit

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var signInClient: SignInClient
    private val signInRequest: BeginSignInRequest = getBeginSignInRequest()
    private val tag = "SingInActivity"

    private val onSuccess: SignInSuccessListener = { idToken ->
        startActivity(Intent(this, HomeActivity::class.java).apply {
            // home activity 구성 후 putExtra 추가
        })
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSignInRequest()
    }

    private fun setSignInRequest() {
        signInClient = Identity.getSignInClient(this)

        val legacySignInLauncher = getLegacySignInResultLauncher(onSuccess)
        val oneTapSignInLauncher = getOneTapSignInResultLauncher(onSuccess, legacySignInLauncher)
        binding.btnGoogleSignIn.setOnClickListener {
            startGoogleSingIn(oneTapSignInLauncher, legacySignInLauncher)
        }
    }

    private fun startGoogleSingIn(
        oneTapSignInLauncher: ActivityResultLauncher<IntentSenderRequest>,
        legacySignInLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) {
        signInClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    oneTapSignInLauncher.launch(
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender)
                            .build()
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(tag, "Couldn't start One Tap: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener { e ->
                Log.e(tag, "sign-in fail: ${e.localizedMessage}")
                requestLegacySingIn(legacySignInLauncher)
            }
            .addOnCanceledListener {
                Log.e(tag, "sign-in canceled")
            }
    }

    private fun getBeginSignInRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    private fun getLegacySignInResultLauncher(onSuccess: SignInSuccessListener): ActivityResultLauncher<IntentSenderRequest> {
        return registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { activityResult ->
            try {
                getGoogleIdToken(activityResult, onSuccess)
            } catch (e: Exception) {
                Log.e(tag, "Legacy launcher error: ${e.localizedMessage}", e)
            }
        }
    }

    private fun getOneTapSignInResultLauncher(
        onSuccess: SignInSuccessListener,
        legacySingInLauncher: ActivityResultLauncher<IntentSenderRequest>
    ): ActivityResultLauncher<IntentSenderRequest> {
        return registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { activityResult ->
            try {
                getGoogleIdToken(activityResult, onSuccess)
            } catch (e: ApiException) {
                Log.e(tag, "One Tap launcher error: ${e.localizedMessage}", e)
                requestLegacySingIn(legacySingInLauncher)
            }
        }
    }

    private fun getGoogleIdToken(activityResult: ActivityResult, onSuccess: SignInSuccessListener) {
        val data = activityResult.data ?: return
        if (activityResult.resultCode != RESULT_OK) return
        val idToken = signInClient.getSignInCredentialFromIntent(data).googleIdToken
        if (idToken != null) {
            onSuccess(idToken)
        }
    }

    private fun requestLegacySingIn(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        val lastToken = GoogleSignIn.getLastSignedInAccount(this)?.idToken
        if (lastToken == null) {
            val request = GetSignInIntentRequest.builder()
                .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
                .build()

            signInClient
                .getSignInIntent(request)
                .addOnSuccessListener { pendingIntent ->
                    launcher.launch(
                        IntentSenderRequest.Builder(pendingIntent.intentSender)
                            .build()
                    )
                }
                .addOnFailureListener { e ->
                    Log.e(tag, "Legacy sign-in fail: ${e.localizedMessage}")
                    Snackbar.make(binding.root, "login failed", Snackbar.LENGTH_LONG).show()
                }
                .addOnCanceledListener {
                    Log.e(tag, "Legacy sign-in canceled")
                }
        } else {
            onSuccess(lastToken)
        }
    }
}