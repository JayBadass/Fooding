package com.example.fooding.ui.addpost

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fooding.FoodingApplication
import com.example.fooding.data.model.FoodResponse
import com.example.fooding.data.source.AddPostRepository
import com.example.fooding.databinding.FragmentAddPostBinding
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddPostFragment : Fragment() {

    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!

    private var hasReadPermission = false
    private var hasWritePermission = false
    private var hasCameraPermission = false

    private val args: AddPostFragmentArgs by navArgs()

    private val viewModel: AddPostViewModel by viewModels {
        AddPostViewModel.provideFactory(
            repository = AddPostRepository(FoodingApplication.appContainer.providePostApiClient())
        )
    }

    private val permissionLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            hasReadPermission =
                permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: hasReadPermission
            hasWritePermission =
                permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: hasWritePermission
            hasCameraPermission =
                permissions[Manifest.permission.CAMERA] ?: hasCameraPermission
        }

    private val getGalleryContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let { uri ->
                setImgViewByGallery(uri)
                viewModel.notifyImgChange(uri)
            }
        }

    private val getCaptureImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                val uri = result.data?.data
                setImgViewByCamera(imageBitmap)
                saveImage(imageBitmap)
                if (uri != null) {
                    viewModel.notifyImgChange(uri)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addPostViewModel = viewModel
        setLayoutFromSearchDetail()
        setCategoryButton()
        setAddImgButton()
        setCompleteButton()
        setDateTimePickerDialog()
        setAddNutritionButton()
        observeNavigationCallBack()
    }

    private fun setLayoutFromSearchDetail() {
        if (args.food != null) {
            viewModel.setNutrition(args.food!!)
            args.food!!.DESC_KOR?.let { createTextView(it) }
        }
    }

    private fun setImgViewByGallery(uri: Uri) {
        binding.foodImgView.visibility = View.VISIBLE
        binding.foodImgView.setImageURI(uri)
    }

    private fun setImgViewByCamera(bitmap: Bitmap) {
        binding.foodImgView.visibility = View.VISIBLE
        binding.foodImgView.setImageBitmap(bitmap)
    }

    private fun setAddImgButton() {
        binding.addGalleryImgButton.setOnClickListener {
            val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
            requestPermissions()
            if (hasReadPermission || minSdk29) {
                getGalleryContent.launch("image/*")
            }
        }
        binding.addCameraImgButton.setOnClickListener {
            val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            requestPermissions()
            Log.d("camera2", "$hasCameraPermission")
            Log.d("write", "$hasWritePermission")
            Log.d("sdk", "$minSdk29")
            if ((hasWritePermission || minSdk29) && hasCameraPermission) {
                getCaptureImage.launch(intent)
            }
        }
    }

    private fun setAddNutritionButton() {
        binding.addNutritionButton.setOnClickListener {
            val action = AddPostFragmentDirections.actionGlobalAddNutrition()
            findNavController().navigate(action)
        }
    }

    private fun setDateTimePickerDialog() {
        binding.textInputDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val dateString = "${year}-${monthOfYear + 1}-${dayOfMonth}"
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val date = dateFormat.parse(dateString)!!.time
                    binding.textInputDate.setText(dateString)
                    viewModel.setDate(date)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
            viewModel.date.observe(viewLifecycleOwner) { date ->
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val dateString = dateFormat.format(date)
                binding.textInputTime.setText(dateString)
            }
        }

        binding.textInputTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minute = cal.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val time = "${hourOfDay}:${minute}"
                    binding.textInputTime.setText(time)
                    viewModel.setTime(time)
                },
                hour,
                minute,
                true
            )
            timePickerDialog.show()
            viewModel.time.observe(viewLifecycleOwner) { time ->
                binding.textInputTime.setText(time)
            }
        }
    }

    private fun setCategoryButton() {
        binding.btnGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = group.findViewById<RadioButton>(checkedId)
            val tag = checkedRadioButton.tag as String
            viewModel.notifyRadioButtonChanged(tag)
            Log.d("checkedId", tag)
        }
    }

    private fun setCompleteButton() {
        val date = binding.textInputDate
        val time = binding.textInputTime
        val button = binding.btnComplete
        fun updateButton() {
            val dateStatus = date.text.toString().trim()
            val timeStatus = date.text.toString().trim()
            val isEnabled = dateStatus.isNotEmpty() && timeStatus.isNotEmpty()
            button.isEnabled = isEnabled
        }
        date.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateButton()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
        time.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateButton()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
        binding.btnComplete.setOnClickListener {
            viewModel.uploadImg()
            viewModel.uploadPost()
            // Calendar Detail Fragment로 이동하는 코드 추가 필요
        }
    }

    private fun requestPermissions() {
        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        hasReadPermission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        hasWritePermission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        hasCameraPermission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val permissions = mutableListOf<String>()
        if (!(hasWritePermission || minSdk29)) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!hasReadPermission) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!hasCameraPermission) {
            permissions.add((Manifest.permission.CAMERA))
            Log.d("camera_1", "$hasCameraPermission")
        }
        if (permissions.isNotEmpty()) {
            permissionLauncher.launch(permissions.toTypedArray())
        }
    }

    private fun saveImage(bitmap: Bitmap) {
        val imageFileName = "IMG_${System.currentTimeMillis()}.jpg"
        val contentResolver = requireContext().contentResolver
        val imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }
        try {
            contentResolver.insert(imageUri, contentValues)?.also { uri ->
                contentResolver.openOutputStream(uri).use { outputStream ->
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)) {
                        throw IOException()
                    }
                }
            } ?: throw IOException()
        } catch (e: IOException) {
            Log.e("saveFile", "save failed", e)
        }
    }

    private fun observeNavigationCallBack() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("foodName")
            ?.observe(viewLifecycleOwner) { foodName ->
                foodName?.let {
                    createTextView(it)
                }
            }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<FoodResponse.Food>(
            "nutritionList"
        )
            ?.observe(viewLifecycleOwner) { nutritionList ->
                nutritionList?.let {
                    viewModel.setNutrition(it)
                }
            }
    }

    private fun createTextView(text: String) {
        val newTextView = TextView(requireContext())
        newTextView.text = text
        newTextView.textSize = 15F
        newTextView.id = View.generateViewId()
        val param: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.marginEnd = 10
        newTextView.layoutParams = param
        binding.nutritionListView.addView(newTextView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}