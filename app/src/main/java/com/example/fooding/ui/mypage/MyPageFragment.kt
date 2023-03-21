package com.example.fooding.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.fooding.FoodingApplication
import com.example.fooding.data.model.User
import com.example.fooding.data.source.UserRepository
import com.example.fooding.data.source.remote.PostApiClient
import com.example.fooding.databinding.FragmentMypageBinding
import com.example.fooding.ui.signin.SignInActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch

class MyPageFragment : Fragment(), InputDialog.NoticeDialogListener {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MyPageViewModel> {
        MyPageViewModel.provideFactory(
            repository = UserRepository(FoodingApplication.database, PostApiClient.create())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setLayout()
        loadUserData()
        setLogOutButton()
    }

    override fun onResume() {
        super.onResume()
        updateUserData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setLayout() {
        binding.btnEdit.setOnClickListener {
            startDialog()
        }
    }

    private fun setUserInfo(user: User) {
        binding.textUserName.text = user.displayName
        Glide.with(this)
            .load(user.photoUrl)
            .circleCrop()
            .into(binding.imgUser)
        binding.textCurrentWtValue.text = "${user.currentWeight}kg"
        binding.textGoalWtValue.text = "${user.goalWeight}kg"
        binding.textGoalCaloriesValue.text = "${user.goalCalories}kcal"
    }

    private fun loadUserData() {
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        account?.let {
            viewLifecycleOwner.lifecycleScope.launch {
                val user = viewModel.loadUserData(account.id!!)
                setUserInfo(user)
                val postCount = viewModel.getPostCount()
                val todayCalories = viewModel.getIntakeCalories()
                binding.textDayCounting2.text = postCount
                binding.textIntakeCaloriesValue.text = "${todayCalories}kcal"
            }
        }
    }

    private fun setLogOutButton() {
        binding.btnLogout.setOnClickListener {
            val signInClient =
                GoogleSignIn.getClient(requireActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN)
            signInClient.signOut().addOnCompleteListener(requireActivity()) {
                val intent = Intent(requireContext(), SignInActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    private fun updateUserData() {
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        account?.let {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.loadUserData(account.id!!)
            }
        }
    }

    private fun startDialog() {
        val inputDialog = InputDialog(this)
        inputDialog.show(parentFragmentManager, "input")
    }

    override fun onDialogPositiveClick(
        currentWeight: String,
        goalWeight: String,
        goalCalories: String
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            val account = GoogleSignIn.getLastSignedInAccount(requireContext())
            account?.let {
                val updatedFields = mutableMapOf<String, String>()
                if (currentWeight.isNotEmpty()) {
                    updatedFields["currentWeight"] = currentWeight
                }
                if (goalWeight.isNotEmpty()) {
                    updatedFields["goalWeight"] = goalWeight
                }
                if (goalCalories.isNotEmpty()) {
                    updatedFields["goalCalories"] = goalCalories
                }
                if (updatedFields.isNotEmpty()) {
                    viewModel.updateUserData(account.id!!, updatedFields)
                }
                val user = viewModel.loadUserData(account.id!!)
                updateUserData()
                setUserInfo(user)
            }
        }
    }
}