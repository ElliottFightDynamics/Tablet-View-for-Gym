package com.striketec.fanapp.view.events.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.striketec.fanapp.R;
import com.striketec.fanapp.presenter.events.fragment.EventsHolderFragmentPresenter;
import com.striketec.fanapp.presenter.events.fragment.EventsHolderFragmentPresenterImpl;

/**
 * This is fragment that holds ViewPager.
 * ViewPager holds other fragment to display the Events list.
 */
public class EventsHolderFragment extends Fragment implements EventsHolderFragmentInteractor {

    private ViewPager mViewPager;
    private OnFragmentInteractionListener mListener;
    private EventsHolderFragmentPresenter mEventsHolderFragmentPresenter;

    public EventsHolderFragment() {
        // Required empty public constructor
        mEventsHolderFragmentPresenter = new EventsHolderFragmentPresenterImpl(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events_holder, container, false);
        findViewByIds(view);
        return view;
    }

    /**
     * Method to set the layout references.
     *
     * @param view
     */
    private void findViewByIds(View view) {
        mViewPager = view.findViewById(R.id.view_pager);

        mEventsHolderFragmentPresenter.setupViewPagerAdapter(mViewPager);
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
    public void onDestroy() {
        super.onDestroy();
        mEventsHolderFragmentPresenter.onDestroy();
        mListener = null;
    }

    @Override
    public void setTabLayoutWithViewPager() {
        if (mListener != null) {
            mListener.setTabLayoutWithViewPager(mViewPager);
        }
    }


    public interface OnFragmentInteractionListener {
        /**
         * Method to set ViewPager to TabLayout so that TabLayout can communicate with ViewPager to change page on tab click.
         *
         * @param mViewPager
         */
        void setTabLayoutWithViewPager(ViewPager mViewPager);
    }

}
