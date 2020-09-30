package com.example.permission_helper.ui.demo_recycler_view


import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.permission_helper.R
import com.example.permission_helper.ui.BaseFragment
import com.example.permission_helper.ui.demo_recycler_view.item_decoration.CanvasDrawLine
import com.example.permission_helper.ui.demo_recycler_view.item_decoration.DrawableDrawLine
import com.example.permission_helper.ui.demo_recycler_view.item_decoration.ItemOffsetDecoration
import com.example.permission_helper.ui.demo_recycler_view.item_decoration.MarginItemOffsets
import com.example.permission_helper.ui.demo_recycler_view.view_holder_type.ContactViewHolder
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_2.*
import kotlinx.android.synthetic.main.fragment_recycler.*

class RecyclerFragment : BaseFragment() {

    private var contacts: ArrayList<Contact>? = null
    private var listAny: ArrayList<Any> = ArrayList()
    private var adapterNormal: RecyclerViewAdapterNormal? = null
    private var adapterType: RecyclerViewAdapterType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerViewIntData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Initialize adapter and recyclerView
        if (contacts != null) {
            recyclerViewAdapter()
            recyclerViewLayout()
            recyclerViewEffect()
            // recyclerViewOnClick()
            recyclerViewItemTouch()
        }
        btnAddMoreClick()

        btnBack.text = "runtest"
    }

    private fun recyclerViewIntData() {
        // Initialize contacts
        contacts = Contact.createContactsList(20)

        // Initialize anyonItemClick
        listAny.addAll(Contact.createContactsList(2))
        listAny.add("image")
        listAny.add("image")
        listAny.addAll(Contact.createContactsList(3))
        listAny.add("image")
    }

    private fun recyclerViewAdapter() {
        adapterNormal = RecyclerViewAdapterNormal(contacts!!)
        rvContacts.adapter = adapterNormal

        /*adapterType = RecyclerViewAdapterType(listAny)
        rvContacts.adapter = adapterType*/
    }

    private fun recyclerViewLayout() {
        /*GridLayoutManager*/
//        rvContacts.layoutManager =
//            GridLayoutManager(this.context, 2, GridLayoutManager.HORIZONTAL, false)


        /*LinearLayoutManager*/
        rvContacts.layoutManager = LinearLayoutManager(requireContext())
        rvContacts.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun recyclerViewEffect() {
        /*Animation: jp.wasabeef:recyclerview-animators:2.2.3
        not work with notifyDataSetChanged()*/
        rvContacts.itemAnimator = SlideInUpAnimator()

        /*RecyclerView Snap Item*/
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvContacts)

        /*RecyclerView ItemDecoration*/
     // rvContacts.addItemDecoration(ItemOffsetDecoration(100)) // Whit GridLayoutManager
         rvContacts.addItemDecoration(DrawableDrawLine(this.context!!))
     //    rvContacts.addItemDecoration(CanvasDrawLine(Color.LTGRAY, 40f))
        // rvContacts.addItemDecoration(MarginItemOffsets(100))
    }


    private fun recyclerViewOnClick() {
        adapterNormal?.setOnClickListener(object : RecyclerViewAdapterNormal.OnClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                when {
                    view.id == R.id.message_button -> {
                        Log.w("TestLog", "Btn onItemLongClick $position")
                    }
                    view.id == R.id.contact_name -> {
                    }
                    else -> {
                    }
                }
            }

            override fun onItemClick(holder: ContactViewHolder, position: Int, message: Any) {

            }

            override fun onItemClick(view: View, position: Int) {
                when (view.id) {
                    R.id.message_button -> {
                        Log.w("TestLog", "Btn clicked $position")
                    }
                    R.id.contact_name -> {
                        Log.w("TestLog", "Text clicked $position")
                    }
                    R.id.avatar -> {
                        Log.e("TestLog", "avatar clicked $position")
                    }
                    else -> {
                        Log.e("TestLog", "Item clicked $position")
                    }
                }
            }
        })
    }

    private fun recyclerViewItemTouch() {
        rvContacts.addOnItemTouchListener(
            RecyclerTouchListener(this.context!!, rvContacts,
                object : RecyclerTouchListener.OnClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        when {
                            view.id == R.id.message_button -> {
                                Log.w("TestLog", "Btn clicked $position")
                            }
                            view.id == R.id.contact_name -> {
                                Log.w("TestLog", "Text clicked $position")
                            }
                            else -> {
                                Log.e("TestLog", "Item clicked $position")
                            }
                        }
                    }

                    override fun onItemLongClick(view: View, position: Int) {
                        Log.e("TestLog", "Item onLongClick")
                    }
                })
        )
    }

    var d = 0
    private fun btnAddMoreClick() {
        if (adapterNormal != null) {
            btnAddMoreContact.setOnClickListener {
                d++
                val curSize = adapterNormal!!.itemCount

                /*Add range items*/
                // val newItems = Contact.addOneContact(d)
                // contacts?.addAll(newItems)
                // adapterNormal!!.notifyItemRangeRemoved(curSize, newItems.size)

                /*Add one item*/
                contacts?.add(Contact.addOneContact(d))
                adapterNormal!!.notifyItemInserted(curSize)

                rvContacts.scrollToPosition(curSize)

                /*if (rvContacts.itemDecorationCount > 0) {
                    rvContacts.removeItemDecorationAt(0)
                    recyclerViewItemDecoration()
                }*/
            }
        }
    }


}
