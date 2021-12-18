package com.olamachia.weeksixtaskandroidsq009

import android.app.Dialog
import android.content.Intent
import android.content.Intent.createChooser
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ContactDetailsFragment : Fragment() {
    lateinit private var  deleteIcon : ImageView
    lateinit private var shareIcon : ImageView
    lateinit private var callIcon : ImageView
    lateinit private  var editIcon: ImageView
    lateinit var contact : FirebaseContact
    lateinit var mDatabase: DatabaseReference

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
        contact = arguments?.get("contact") as FirebaseContact
        mDatabase =  FirebaseDatabase.getInstance().getReference("Contacts");
        deleteIcon = view.findViewById(R.id.contact_details_delete_icon)
        shareIcon = view.findViewById(R.id.contact_detail_share_icon)
        callIcon = view.findViewById(R.id.contact_details_call_icon)
        editIcon = view.findViewById(R.id.contact_details_edit_icon)

            deleteIcon.setOnClickListener {
                dialogue()
        }
            shareIcon.setOnClickListener {
            shareMyContact()
        }
            editIcon.setOnClickListener {
                showEditContactDialog()
        }
            callIcon.setOnClickListener {
            phoneCall()
        }

    }


    //This sets the UI up to display the name and number from contacts list
    private fun onInitViews(view: View) {
        val contact = arguments?.get("contact") as FirebaseContact
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


    private fun phoneCall(){
        val myIntent = Intent(Intent.ACTION_DIAL)
        myIntent.data = Uri.parse("tel:${contact.phone}")
        requireActivity().startActivity(myIntent)
    }

    private fun shareMyContact(){
        val myIntent = Intent(Intent.ACTION_SEND)
        myIntent.putExtra(Intent.EXTRA_TEXT, contact.name)
        myIntent.type  = "text/plain"
        requireActivity().startActivity(
            createChooser(
                myIntent,
                contact.name
            )
        )
    }

    private fun showEditContactDialog(){
        val dialog = Dialog(requireContext())
        val inflater = LayoutInflater.from(requireContext()).inflate(R.layout.alert_layout, null, false)
        dialog.setContentView(inflater)
        val window = dialog.window
        window?.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT)
        dialog.create()
        dialog.show()
        val name = inflater.findViewById<EditText>(R.id.alert_layout_name)
        val number = inflater.findViewById<EditText>(R.id.alert_layout_number)
        val sendButton = inflater.findViewById<Button>(R.id.alert_layout_button)

        sendButton?.setOnClickListener {
            val contactName = name.text.toString()
            val contactNumber = number.text.toString()
            editContact(FirebaseContact(contact.id, contactName, contactNumber))
            dialog.dismiss()
        }
    }


    private fun editContact(contact: FirebaseContact){
        mDatabase.child(contact.id.toString()).setValue(contact)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "Contact successfully edited", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
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