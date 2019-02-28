package com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.android.billingclient.api.BillingClient;
import com.raphael.rapha.myNews.R;
import com.raphael.rapha.myNews.activities.mainActivity.MainActivity;
import com.raphael.rapha.myNews.payment.BillingManager;
import com.raphael.rapha.myNews.payment.MyBillingUpdateListener;
import com.raphael.rapha.myNews.payment.PurchasedItemsListener;
import com.raphael.rapha.myNews.roomDatabase.KeyWordDbService;
import com.raphael.rapha.myNews.roomDatabase.NewsHistoryDbService;
import com.raphael.rapha.myNews.roomDatabase.RatingDbService;
import com.raphael.rapha.myNews.sharedPreferencesAccess.InAppPaymentService;
import com.raphael.rapha.myNews.sharedPreferencesAccess.NewsOfTheDayTimeService;
import com.raphael.rapha.myNews.sharedPreferencesAccess.SettingsService;
import com.raphael.rapha.myNews.sharedPreferencesAccess.SwipeTimeService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements PurchasedItemsListener {

    View view;
    MainActivity mainActivity;
    BillingManager billingManager;
    Button payment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        mainActivity = (MainActivity) getActivity();
        initBillingProcess();
        initButtons();
        initSwitch();
        return view;
    }

    private void initBillingProcess(){
        MyBillingUpdateListener billingListener = new MyBillingUpdateListener();
        billingListener.setActivity(mainActivity);
        billingListener.setPurchasedItemsListener(this);
        billingManager = new BillingManager(mainActivity, billingListener);
        billingManager.setPurchasedItemsListener(this);
    }

    private void initSwitch(){
        Switch notificationSwitch = view.findViewById(R.id.switch_notifications);
        notificationSwitch.setChecked(SettingsService.getCheckedNotification(getContext()));
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SettingsService.setCheckedNotification(getContext(), isChecked);
        });
    }

    private void initButtons(){
        Button statisticReset = view.findViewById(R.id.statistic_reset_button);
        statisticReset.setOnClickListener(view ->
            new AlertDialog.Builder(getActivity())
                    .setTitle("Confirm")
                    .setMessage("Do you really want to reset all of your liked categories and topics? " +
                            "You can't reverse this decision.")
                    .setPositiveButton("Yes reset", (dialogInterface, i) -> {
                        RatingDbService.getInstance(mainActivity.getApplication())
                                .deleteAllUserPreferences(mainActivity.getApplication());
                        KeyWordDbService.getInstance(mainActivity.getApplication()).deleteAll();
                        NewsHistoryDbService.getInstance(mainActivity.getApplication()).deleteAll();
                        NewsOfTheDayTimeService.resetLastLoaded(getContext());
                        SwipeTimeService.setFirstTopicWasLiked(getContext(), false);
                        SwipeTimeService.setRedirected(getContext(), false);
                        dialogInterface.cancel();
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
                    .create().show());

        payment = view.findViewById(R.id.payment_button);
        payment.setOnClickListener(view ->
            billingManager.initiatePurchaseFlow(
                    InAppPaymentService.MONTHLY_PAYMENT_SKU,
                    null,
                    BillingClient.SkuType.SUBS)
        );
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPurchasedItemsHandled() {
        Log.d("BillingManager","onPurchasedItemsHandled()");
        if(InAppPaymentService.userIsSubscribed(mainActivity)){
            payment.setText("Thank you! :)");
            payment.setClickable(false);
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
