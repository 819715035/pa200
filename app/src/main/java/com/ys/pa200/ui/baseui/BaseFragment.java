package com.ys.pa200.ui.baseui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ys.pa200.utils.UiUtils;


/**
 * Created by lilaoda on 2016/9/9.
 */
public class BaseFragment extends Fragment {
    private View view;
//    protected DialogLoading mDialogLoading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



//    protected void showLoadingDialog() {
//        if (mDialogLoading == null) {
//            mDialogLoading = new DialogLoading(getActivity());
//        }
//        mDialogLoading.show();
//    }
//
//    protected void dismissLoadingDialog() {
//        if (mDialogLoading != null) {
//            mDialogLoading.dismiss();
//        }
//    }
}
