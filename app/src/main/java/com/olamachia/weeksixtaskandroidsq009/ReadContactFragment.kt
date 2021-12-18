package com.olamachia.weeksixtaskandroidsq009

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ReadContactFragment : Fragment() {
    lateinit var myAdapter: ContactRecyclerViewAdapter
    lateinit var myRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.database_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAdapter = ContactRecyclerViewAdapter()
        myRecyclerView = view.findViewById(R.id.mycontacts_recycler)
        myRecyclerView.adapter = myAdapter
        myRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        checkPermissions()

    }


    private fun checkPermissions() {
        var permission_array = arrayOf(android.Manifest.permission.READ_CONTACTS)
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                permission_array[0]
            )) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissions(permission_array, 0)
        }
        else{
            readData()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            readData()

        }
        else{

        }
    }


    @SuppressLint("Range")
    fun readData() {

        val contacts = arrayListOf<FirebaseContact>()
        var cursor: Cursor? = requireActivity().contentResolver
            .query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )

        while (cursor?.moveToNext() == true) {
            val name =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            contacts.add(FirebaseContact(phone = phoneNumber, name = name))
        }
        cursor?.close()
        myAdapter.submitList(contacts)
    }
}