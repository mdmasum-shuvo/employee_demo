package com.nuveq.sojibdemo.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.nuveq.sojibdemo.network.ApiService;
import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.network.RestClient;


public abstract class BaseFragment extends Fragment {
    private ViewDataBinding binding;
    private ProgressDialog progressDialog;
    private LinearLayout loadingView, noDataView;

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId(), container, false);
        return binding.getRoot();
    }

    protected abstract Integer layoutResourceId();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFragmentComponents();
        initFragmentFunctionality();
        initFragmentListener();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected abstract void initFragmentComponents();

    protected abstract void initFragmentFunctionality();

    protected abstract void initFragmentListener();

    public ViewDataBinding getBinding() {
        return binding;
    }

    public Fragment getFragment() {
        return this;
    }

    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    public ApiService getApiService() {
        return RestClient.getInstance().callRetrofit();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*
        public void showProgressDialog(final String title, final String message) {
        new Runnable() {
                @Override
                public void run() {
                    progressDialog = ProgressDialog.show(getActivity(), title, message, true);
                }
            };
        }
        public void showProgressDialog() {
            new Runnable() {
                @Override
                public void run() {
                    progressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.please_wait), true);
                }
            };
        }

        public void hideProgressDialog() {
            if (progressDialog != null && progressDialog.isShowing()) {
               new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                };
            }
        }*/
    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void initLoader() {
        loadingView = (LinearLayout) getActivity().findViewById(R.id.loadingView);
        noDataView = (LinearLayout) getActivity().findViewById(R.id.noDataView);
    }

    public void showLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }

        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    public void hideLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    public void showEmptyView() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    public void startActivity(Activity activity, Class<?> cls, boolean finishSelf, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        startActivity(intent);
        if (finishSelf) {
            activity.finish();
        }
    }

    public void setToolbarTitle(String title) {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(title);
    }


    public void showProgressDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.please_wait), true);
            }
        });
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
        }
    }


}