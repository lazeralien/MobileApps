package com.babcock.app1

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "firstName"
private const val ARG_PARAM2 = "middleName"
private const val ARG_PARAM3 = "lastName"
private lateinit var onStringListener: NameFragment.OnStringListener


class NameFragment : Fragment() {
    private lateinit var submitButton: Button
    private var firstName: String? = null
    private var middleName: String? = null
    private var lastName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            firstName = it.getString(ARG_PARAM1)
            middleName = it.getString(ARG_PARAM2)
            lastName = it.getString(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, //inflator allows this to be viewed
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_name, container, false)
        //listen for text

        //button
        submitButton = view.findViewById<Button>(R.id.submitButton)
        return view
    }

    interface OnStringListener {
        fun onStringSent(str: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onStringListener = context as OnStringListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnStringListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mFirstName = view.findViewById<EditText>(R.id.editTextFirstName)
        val mMiddleName = view.findViewById<EditText>(R.id.editTextMiddleName)
        val mLastName = view.findViewById<EditText>(R.id.editTextLastName)

        submitButton.setOnClickListener {
            //update the first, middle and last name values
            firstName = mFirstName.getText().toString()
            middleName = mMiddleName.getText().toString()
            lastName = mLastName.getText().toString()

            //send the return string to interface
            onStringListener.onStringSent(firstName + " " + lastName)

            //Next 4 lines minimize the keyboard
            val activity = requireActivity()
            val cView = activity.currentFocus
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(cView?.windowToken,0)

            //adds notification for user
            Toast.makeText(requireContext(), "Name Submitted", Toast.LENGTH_SHORT).show()
        }

    }
}

