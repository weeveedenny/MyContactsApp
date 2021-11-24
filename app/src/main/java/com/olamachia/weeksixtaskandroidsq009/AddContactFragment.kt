package com.olamachia.weeksixtaskandroidsq009

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AddContactFragment : Fragment(), RecyclerViewAdapter.OnItemClickListener {
    lateinit var addContactButton: Button
    val database = FirebaseDatabase.getInstance().getReference(contactsNode)
    private lateinit var contactAdapter: RecyclerViewAdapter
    lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.addcontact_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactAdapter = RecyclerViewAdapter()
        contactAdapter.apply {
            addOnItemClickListener(this@AddContactFragment)
        }
        addContactButton = view.findViewById(R.id.addcontact_button)
        addContactButton.setOnClickListener {
            validateContactFields()
        }

        val contactsRecyclerView = view.findViewById<RecyclerView>(R.id.addcontact_recyclerview)
        contactsRecyclerView.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        getContactsFromDatabase()

    }


    //function to validate add contact form
    private fun validateContactFields() {
        name = view?.findViewById<EditText>(R.id.addcontact_name)?.text.toString()
        val phoneNumber = view?.findViewById<EditText>(R.id.addcontact_Phone)?.text.toString()

        if (name.isNotEmpty() && phoneNumber.isNotEmpty()) {
            sendToDatabase(name, phoneNumber)
        } else {
            Toast.makeText(requireContext(), "Input fields cannot be empty", Toast.LENGTH_SHORT)
                .show()
        }
    }

    //Function to insert data in database
    private fun sendToDatabase(name: String, phoneNumber: String) {
        val newUser = MyModel(null, name, phoneNumber)
        newUser.id = database.push().key
        database.child(newUser.id!!).setValue(newUser).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "Contact Added Successfully", Toast.LENGTH_LONG)
                    .show()
                val deleteName = view?.findViewById<EditText>(R.id.addcontact_name)?.text?.clear()
                val deleteNumber = view?.findViewById<EditText>(R.id.addcontact_Phone)?.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Unable to add contact", Toast.LENGTH_LONG).show()
            }
        }
    }


    //Listens for changes and then updates recycleView
    private fun getContactsFromDatabase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val contactsFromDB = arrayListOf<MyModel>()
                for (i in snapshot.children) {
                    val contact = i.getValue(MyModel::class.java)
                    contactsFromDB.add(contact!!)
                }
                contactAdapter.submitList(contactsFromDB)
                contactAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    //function add contact details to bundle and send to contact details layout
    override fun onClick(contact: MyModel) {

        val contactBundle = Bundle()
        contactBundle.putParcelable("contact", contact)
        navigate(R.id.fragment_container, ContactDetailsFragment::class.java, contactBundle)

    }


    //This replaces the container with the new fragment being passed
    private fun navigate(
        fragmentContainerId: Int,
        fragmentClass: Class<out Fragment>,
        args: Bundle?
    ) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(fragmentContainerId, fragmentClass, args)
            .addToBackStack(null)
            .commit()
    }

}