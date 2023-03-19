package com.example.fooding.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.fooding.databinding.FragmentPostBinding

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    private val args: PostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()
        setNutritionButton()
    }

    private fun setLayout() {
        binding.post = args.post
        binding.foodImgView.setImageURI(Uri.parse(args.post?.imgUri))
    }

    private fun setNutritionButton() {
        if (args.post?.nutritionInfo != null) {
            binding.btnNutrition.visibility = View.VISIBLE
            binding.btnNutrition.text = args.post?.nutritionInfo?.DESC_KOR
        } else {
            binding.btnNutrition.visibility = View.INVISIBLE
        }
    }
}