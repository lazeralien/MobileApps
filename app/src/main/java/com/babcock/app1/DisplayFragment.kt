package com.babcock.app1

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * A simple [Fragment] subclass.
 */
class DisplayFragment : Fragment() {
    private var mName: String = "NOBODY"
    private var mIvPic: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if(bundle != null) {
            if(bundle.containsKey("NAME")) {
                mName = bundle.getString("NAME").toString()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_display, container, false)

        //find greeting text
        val xName = view.findViewById<View>(R.id.greetingText) as TextView
        xName.text = "$mName is logged in!"

        val takePhoto = view.findViewById<Button>(R.id.updatePhotoButton)
        takePhoto.setOnClickListener {

            //Next set of lines requests camera permission
            val cameraPermission = android.Manifest.permission.CAMERA
            if (ContextCompat.checkSelfPermission(this.requireContext(), cameraPermission) == PackageManager.PERMISSION_GRANTED) {
                // Permission has already been granted
                // Start the camera intent
            } else {
                // Permission has not been granted yet, request it
                ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(cameraPermission), 100)
            }
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                cameraActivity.launch(cameraIntent)
            } catch (ex: ActivityNotFoundException) {

            }
        }
        // Inflate the layout for this fragment
        return view
    }

    /**
     * had to override in order to update the image after the ImageView was created
     * during the onViewCreated portion
     */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        updateImage()
    }

    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if(result.resultCode == RESULT_OK) {
            mIvPic = view?.findViewById<View>(R.id.pictureView) as ImageView
        }
        val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)!!
        saveImage(thumbnailImage) //utilized method to save photo
        mIvPic!!.setImageBitmap(thumbnailImage)
    }

    /**
     * saves image to the app cache
     */
    private fun saveImage (bits:Bitmap) {
    // Get the directory for storing cache files
        val cacheDir = context?.cacheDir

    // Generate a unique file name for the image
        val fileName = "my_image_${System.currentTimeMillis()}"

    // Create a file in the cache directory with the unique file name
        val file = File(cacheDir, fileName)

    // Use a FileOutputStream to write the bitmap data to the file
        val outputStream = FileOutputStream(file)
        bits.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

    // Save the file path to shared preferences or other persistent storage
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putString("my_image_file_path", file.absolutePath).apply()
    }

    /**
     * retrieves image from the cache
     */
    private fun updateImage() {
        // Get the file path from shared preferences or other persistent storage
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val filePath = preferences.getString("my_image_file_path", null)

        if (filePath != null) {
            // Create a FileInputStream to read the image data from the file
            val file = File(filePath)
            val inputStream = FileInputStream(file)

            // Use BitmapFactory to decode the input stream into a Bitmap
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Use the Bitmap to update the ImageView or other UI element

                mIvPic = view?.findViewById<View>(R.id.pictureView) as ImageView
                mIvPic!!.setImageBitmap(bitmap)

            }
        }
}