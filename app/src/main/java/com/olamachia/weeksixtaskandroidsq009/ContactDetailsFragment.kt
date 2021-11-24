package com.olamachia.weeksixtaskandroidsq009

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class ContactDetailsFragment : Fragment() {
    lateinit private var  deleteIcon : ImageView
    lateinit private var shareIcon : ImageView
    lateinit private var callIcon : ImageView
    lateinit private  var editIcon: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitViews(view)
        deleteIcon = view.findViewById(R.id.contact_details_delete_icon)
        shareIcon = view.findViewById(R.id.contact_detail_share_icon)
        callIcon = view.findViewById(R.id.contact_details_call_icon)
        editIcon = view.findViewById(R.id.contact_details_edit_icon)

            deleteIcon.setOnClickListener {
                dialogue()
        }

    }


    //This sets the UI up to display the name and number from contacts list
    private fun onInitViews(view: View) {
        val contact = arguments?.get("contact") as MyModel
        val name = view.findViewById<TextView>(R.id.contact_details_name)
        name.apply {
            text = contact.name
        }

        val phoneNumber = view.findViewById<TextView>(R.id.contacts_details_number)
        phoneNumber.apply {
            text = contact.phone
        }

    }

    private fun deleteContact() {
        val contact = arguments?.get("contact") as MyModel
        val mDatabase = FirebaseDatabase.getInstance().getReference("Contacts");
        mDatabase.child(contact.id.toString()).setValue(null)
            .addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "Thank you ..", Toast.LENGTH_LONG)
                    .show()
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Error in deleting contact", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }




    private fun dialogue(){
            val builder = AlertDialog.Builder(requireContext())
            //set title for alert dialog
            builder.setTitle(R.string.dialogTitle)
            //set message for alert dialog
            builder.setMessage(R.string.dialogMessage)
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                deleteContact()
                Toast.makeText(requireContext(), "Contact deleted Successfully", Toast.LENGTH_LONG).show()
            }
            //performing negative action
            builder.setNegativeButton("No") { dialogInterface, which ->
                Toast.makeText(requireContext(), "Action aborted...", Toast.LENGTH_LONG).show()
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()


    }

}