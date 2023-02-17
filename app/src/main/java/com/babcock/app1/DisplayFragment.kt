package com.babcock.app1

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class DisplayFragment : Fragment() {
    var mName: String = "NOBODY";
    private var mIvPic: ImageView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if(bundle != null) {
            if(bundle.containsKey("NAME")) {
                mName = bundle.getString("NAME").toString()
            }
            if(bundle.containsKey("bitmapPhoto")) {
                //TODO: add photo to bitmap somehow
//                thumbnailImage = bundle.getParcelable("bitmapPhoto")!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_display, container, false)

        //find greeting text
        var xName = view.findViewById<View>(R.id.greetingText) as TextView
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

    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if(result.resultCode == RESULT_OK) {
            mIvPic = view?.findViewById<View>(R.id.pictureView) as ImageView
        }
        var thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)!!
        mIvPic!!.setImageBitmap(thumbnailImage)
    }
}