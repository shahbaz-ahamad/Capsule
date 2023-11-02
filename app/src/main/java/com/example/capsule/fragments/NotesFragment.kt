package com.example.capsule.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capsule.R
import com.example.capsule.adapter.ParentAdapter
import com.example.capsule.databinding.FragmentNotesBinding
import com.example.capsule.datamodel.ChildItem
import com.example.capsule.datamodel.ParentItem
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var parentList:ArrayList<ParentItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentNotesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addToParentList()
        setUpRecyclerView()
    }

    private fun addToParentList() {

        parentList= ArrayList()

        //for the child list
        val childItem1=ArrayList<ChildItem>()
        childItem1.add(ChildItem("point 1"))
        childItem1.add(ChildItem("point 2"))
        childItem1.add(ChildItem("point 3"))
        childItem1.add(ChildItem("point 4"))

        //adding the item to the first parent
        parentList.add(ParentItem("Note Topic 1",childItem1,true))


        //for the child list
        val childItem2=ArrayList<ChildItem>()
        childItem2.add(ChildItem("point 1"))
        childItem2.add(ChildItem("point 2"))
        childItem2.add(ChildItem("point 3"))


        //adding the item to the  parent
        parentList.add(ParentItem("Note Topic 2",childItem2))


        //for the child list
        val childItem3=ArrayList<ChildItem>()
        childItem3.add(ChildItem("point 1"))
        childItem3.add(ChildItem("point 2"))


        //adding the item to the  parent
        parentList.add(ParentItem("Note Topic 3",childItem3))



        //for the child list
        val childItem4=ArrayList<ChildItem>()
        childItem4.add(ChildItem("point 1"))


        //adding the item to the  parent
        parentList.add(ParentItem("Note Topic 4",childItem4))

    }

    private fun setUpRecyclerView(){
        binding.recylerView.setHasFixedSize(true)
        binding.recylerView.layoutManager= LinearLayoutManager(requireContext())
        addToParentList()
        val parentAdapter= ParentAdapter(requireContext(),parentList)
        binding.recylerView.adapter=parentAdapter
    }
}