package com.technipixl.filrouge.ui.activities

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import com.technipixl.filrouge.R
import com.technipixl.filrouge.databinding.ActivityAuthenticationBinding
import com.technipixl.filrouge.utils.Validators

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding
    private var validIconDrawable: Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)

        binding.emailEditText.doAfterTextChanged {
            val email = binding.emailEditText.text?.toString()
            toggleButtonValidity()
            toggleEmailValidity(Validators.isEmailValid(email))
        }

        binding.passwordEditText.doAfterTextChanged {
            val password = binding.passwordEditText.text?.toString()
            toggleButtonValidity()
            togglePasswordValidity(Validators.isPasswordValid(password))
        }

        validIconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ico_valid_green, theme)

        toggleButtonValidity()
        setContentView(binding.root)
    }

    fun onAuthenticationRequest(view: View) {
        val login = binding.emailEditText.text?.toString()
        val password = binding.passwordEditText.text?.toString()
        authenticateUser(login, password)
    }

    private fun authenticateUser(email: String?, password: String?) {
        if (Validators.loginValidity(email, password) == Validators.LoginValidity.VALID) {
            displayHomeActivity()
        }
    }

    private fun displayHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent).also {
            finish()
        }
    }

    private fun toggleEmailValidity(isValid: Boolean) {
        if(isValid) {
            binding.emailInputLayout.isErrorEnabled = false
            binding.emailEditText.setTextColor(ResourcesCompat.getColor(resources,
                R.color.text_input_valid_text_color, theme))
        } else {
            binding.emailInputLayout.error = getString(R.string.email_error_hint)
            binding.emailInputLayout.isErrorEnabled = true
            binding.emailEditText.setTextColor(ResourcesCompat.getColor(resources,
                R.color.black, theme))
        }
    }

    private fun togglePasswordValidity(isValid: Boolean) {
        if(isValid) {
            binding.passwordInputLayout.isErrorEnabled = false
            binding.passwordEditText.setTextColor(ResourcesCompat.getColor(resources,
                R.color.text_input_valid_text_color, theme))
        } else {
            binding.passwordInputLayout.error = getString(R.string.password_error_hint)
            binding.passwordInputLayout.isErrorEnabled = true
            binding.passwordEditText.setTextColor(ResourcesCompat.getColor(resources,
                R.color.black, theme))
        }
    }

    private fun toggleButtonValidity() {
        val login = binding.emailEditText.text?.toString()
        val password = binding.passwordEditText.text?.toString()
        val validity = Validators.loginValidity(login, password)
        binding.connectButton.isEnabled = validity == Validators.LoginValidity.VALID
    }
}