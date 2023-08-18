package com.namnp.androidjetpack.navigation.onboarding.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.navigation.onboarding.ParcelableDataArgs

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MyHomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MyHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OnboardingStep1Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding_step1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            val arg1 = "Screen 2"
            val arg2 = 2
            val data1 = 1
            val arg3 = ParcelableDataArgs(data1)
            arg3.data2 = 2
            arg3.data3 = "Welcome to Screen 2"
            arg3.data4 = 1
            val action =
                OnboardingStep1FragmentDirections.actionStep1ToStep2(
                    arg3,
                    arg1,
                    arg2,
                )
            findNavController().navigate(action)
//             findNavController().navigate(R.id.action_step1_to_step2)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OnboardingStep1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}