package com.example.herbario_nacional.ui.Fragments


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.api.load
import com.example.herbario_nacional.R
import com.example.herbario_nacional.preferences.AppPreferences
import com.example.herbario_nacional.ui.Activities.NoLoginActivity
import com.example.herbario_nacional.ui.viewModels.MeViewModel
import com.example.herbario_nacional.ui.viewModels.ProfileViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_new_plant.*
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.create
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment(), View.OnClickListener {

    private val meViewModel: MeViewModel by viewModel()

    private val myProfileViewModel: ProfileViewModel by viewModel()

    private val myAccountViewModel: MeViewModel by viewModel()

    private var file: File? = null
    private lateinit var currentPhotoPath: String
    private lateinit var fileUri: Uri
    private var filePath: String? = ""


    companion object {
        private const val REQUEST_TAKE_PHOTO = 1
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_profile, container, false)

        var profile_button: FloatingActionButton = view.findViewById(R.id.save_account_profile_btn);
        profile_button.setOnClickListener(this);


        var change_photo_btn: Button = view.findViewById(R.id.change_pp);
        change_photo_btn.setOnClickListener { showDialog(); }


        var logout_btn: Button = view.findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener {
            AppPreferences().remove(AppPreferences.Key.cookies)
            showActivity(NoLoginActivity::class.java)
            Toast.makeText(context, getString(R.string.logoutNotification), Toast.LENGTH_LONG).show()
        }

        // Inflate the layout for this fragment
        return view


    }

    private fun showDialog(){
        val array = arrayOf("Tomar una foto", "Agregar de la galería")

        val builder = context?.let { AlertDialog.Builder(it) }

        builder!!.setTitle("Agregar una foto")

        builder.setItems(array) { _, which ->
            val selected = array[which]
            if (selected == "Tomar una foto") {
                dispatchTakePictureIntent()
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (context?.let { checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE) } ==
                        PackageManager.PERMISSION_DENIED){
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, ProfileFragment.PERMISSION_CODE)
                    }
                    else{
                        pickImageFromGallery()
                    }
                }
                else{
                    pickImageFromGallery()
                }
            }
        }

        builder.setNegativeButton("Cancelar", null)

        val dialog = builder.create()

        dialog.show()
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentUser: Int?

        profile_picture.load("https://api.adorable.io/avatars/200/25@adorable.png")

        meViewModel.uiState.observe(viewLifecycleOwner, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    currentUser = result.data.id;
                    Toast.makeText(context, "Usuario actual: $currentUser", Toast.LENGTH_LONG).show()
                    fullname.text = "${result.data.first_name} ${result.data.last_name}"
                    username.text = result.data.username
                    email.text = result.data.email

                    //UPDATE FIELDS
                    first_name_update.setText(result.data.first_name)
                    last_name_update.setText(result.data.last_name)
                    username_update.setText(result.data.username)
                    email_update.setText(result.data.email)
                    var placeholder_phone = ""

                    if(result.data.profile?.phone != null){
                        placeholder_phone = result.data.profile?.phone;
                    }

                    phone_update.setText(placeholder_phone);

                    var placeholder_number_id = ""

                    if(result.data.profile?.number_id != null){
                        placeholder_number_id = result.data.profile?.number_id;
                    }

                    if(result.data.profile?.photo != null){

                        val newLink = result.data.profile?.photo.replace("file/d/", "uc?export=view&id=");
                        val finalLink = newLink.replace("/view?usp=drivesdk", "");
                        Picasso.get().load(finalLink).placeholder(R.drawable.ic_load_img).into(profile_picture);
                        //profile_picture.load(finalLink);
                    }else{
                        Log.i("HERES  NOT THE PICTURE ", "NO PICTURE FOUND");
                    }

                    numb_ref_update.setText(placeholder_number_id)
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(context, resources.getString(error), Toast.LENGTH_LONG).show()
                    showActivity(NoLoginActivity::class.java)
                }
            }
        })
    }

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(activity, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }



    override fun onClick(v: View?) {

        Toast.makeText(context, "UPDATING...", Toast.LENGTH_LONG).show();

        //Profile mutable params
        val profileParams: MutableMap<String, Any> = ArrayMap();
        profileParams["number_id"] = numb_ref_update.text.toString()
        profileParams["phone"] = phone_update.text.toString()
        //jsonParams["photo"] = profile!!.photo;
        profileParams["username"] = username_update.text.toString();

        if(file != null){
            val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file!!)
            val image: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file!!.name, requestBody)

            val number_id: RequestBody = create(MediaType.parse("multipart/form-data"), numb_ref_update.text.toString())
            val phone : RequestBody = create(MediaType.parse("multipart/form-data"), phone_update.text.toString())

            myProfileViewModel.updateProfile(image, number_id, phone);

        }else{
            val number_id: RequestBody = create(MediaType.parse("multipart/form-data"), numb_ref_update.text.toString())
            val phone : RequestBody = create(MediaType.parse("multipart/form-data"), phone_update.text.toString())

            myProfileViewModel.updateProfileNoPhoto(number_id, phone);


        }



        //Account mutable params
        val accountParams: MutableMap<String, Any> =
            ArrayMap()
        accountParams["email"] = email_update.text.toString();
        accountParams["first_name"] = first_name_update.text.toString();
        accountParams["last_name"] = last_name_update.text.toString();
        accountParams["username"] = username_update.text.toString();


        //Account request body
        val AccountBody = myProfileViewModel.createJsonObject(accountParams);


        //update account

        myAccountViewModel.updateAccount(AccountBody);

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale("es")).format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also {
            file = File(currentPhotoPath)
            it.data = Uri.fromFile(file)
            activity?.sendBroadcast(it)
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            activity?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        //Toast.makeText(this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show()
                        null
                    }

                    photoFile?.also {
                        val photoUri: Uri? = context?.let { it1 ->
                            FileProvider.getUriForFile(
                                it1,
                                "com.example.fileprovider",
                                it
                            )
                        }
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                    }
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            ProfileFragment.PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else{
                    //Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == ProfileFragment.IMAGE_PICK_CODE){
            plant_picture.setImageURI(data?.data)
            fileUri = data?.data!!
            filePath = getRealPathFromURI(fileUri)
            file = File(filePath!!)
        }

        if (resultCode == Activity.RESULT_OK && requestCode == ProfileFragment.REQUEST_TAKE_PHOTO) {
            galleryAddPic()
            val photo: Bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            profile_picture.setImageBitmap(photo)
            Timber.e("Uri: ${currentPhotoPath.toUri()}")
        }
    }



    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor = activity?.contentResolver?.query(contentURI, null, null, null, null)
        if (cursor == null) {
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

}


