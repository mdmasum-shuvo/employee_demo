package com.nuveq.sojibdemo.common

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.gson.GsonBuilder
import com.google.gson.JsonNull
import com.nuveq.sojibdemo.R
import com.nuveq.sojibdemo.appdata.AppConstants
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum
import com.nuveq.sojibdemo.feature.HomeFragment
import com.nuveq.sojibdemo.feature.ProfileFragment
import com.nuveq.sojibdemo.network.ApiService
import com.nuveq.sojibdemo.network.RestClient
import com.nuveq.sojibdemo.view.activity.RegistrationActivity
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import com.yarolegovich.slidingrootnav.callback.DragListener
import com.yarolegovich.slidingrootnav.callback.DragStateListener
import java.util.*

abstract class BaseActivity : AppCompatActivity()  {
    private var progressDialog: ProgressDialog? = null
    private var mContext: Context? = null
    private var mActivity: Activity? = null
    private var mToolbar: Toolbar? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var mNavigationView: NavigationView? = null
    private var loadingView: LinearLayout? = null
    private var noDataView: LinearLayout? = null
    private var binding: ViewDataBinding? = null
    private val fragment: Fragment? = null

    var profileFragment: ProfileFragment? = null
    var homeFragment: HomeFragment? = null

    // toolbar titles respected to selected nav menu item

    // flag to load home fragment when user presses back key
    private val shouldLoadHomeFragOnBackPress = true
    private var mHandler: Handler? = null

    //private GPSTracker gps;
    private val latitude = 0.0
    val lng = 0.0
    private val toggle: ActionBarDrawerToggle? = null
    private var drawer: SlidingRootNav? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVariable()
        initAllFragment()
        mHandler = Handler()
        binding = DataBindingUtil.setContentView(this, layoutResourceFile)
        initComponent()
        initFunctionality()
        initListener()


        // uncomment this line to disable ad
        // AdUtils.getInstance(mContext).disableBannerAd();
        // AdUtils.getInstance(mContext).disableInterstitialAd();
    }

    fun getBinding():ViewDataBinding{
        return binding!!;
    }

    protected abstract val layoutResourceFile: Int
    protected abstract fun initComponent()
    protected abstract fun initFunctionality()
    protected abstract fun initListener()
    private fun initVariable() {
        mContext = applicationContext
        mActivity = this@BaseActivity
    }


    fun initToolbar() {
        mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mToolbar)
    }

    fun enableBackButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    val toolbar: Toolbar?
        get() {
            if (mToolbar == null) {
                mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
                setSupportActionBar(mToolbar)
            }
            return mToolbar
        }

    fun showProgressDialog() {
        runOnUiThread { progressDialog = ProgressDialog.show(this@BaseActivity, null, getString(R.string.please_wait), true) }
    }

    fun hideProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            runOnUiThread { progressDialog!!.dismiss() }
        }
    }

    fun logout() {
        val builder: AlertDialog.Builder
        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AlertDialog.Builder(this, R.style.DialogTheme)
        } else {
            AlertDialog.Builder(this)
        }
        builder.setTitle(getString(R.string.logout_title))
        builder.setMessage(getString(R.string.logout_message))
        builder.setIcon(R.drawable.logout_icon)
        builder.setNegativeButton("No", null)
        builder.setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
            //  startActvity(this, LoginActivity.class, true);
            val data = SharedPreferencesEnum.getInstance(this).getString(SharedPreferencesEnum.Key.PHONE_NUMBER)
            val intent = Intent(this, RegistrationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent.putExtra(AppConstants.PHONE_NUMBER, data))
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun setToolbarTitle(title: String?) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
            val tvTitle = findViewById<TextView>(R.id.toolbar_title)
            tvTitle.text = title
        }
    }

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetworkInfo: NetworkInfo? = null
            if (connectivityManager != null) {
                activeNetworkInfo = connectivityManager.activeNetworkInfo
            }
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    private fun initAllFragment() {
        profileFragment = ProfileFragment()
        homeFragment = HomeFragment()
    }






    fun initLoader() {
        loadingView = findViewById<View>(R.id.loadingView) as LinearLayout
        noDataView = findViewById<View>(R.id.noDataView) as LinearLayout
    }

    fun showLoader() {
        if (loadingView != null) {
            loadingView!!.visibility = View.VISIBLE
        }
        if (noDataView != null) {
            noDataView!!.visibility = View.GONE
        }
    }

    fun hideLoader() {
        if (loadingView != null) {
            loadingView!!.visibility = View.GONE
        }
        if (noDataView != null) {
            noDataView!!.visibility = View.GONE
        }
    }

    fun showEmptyView() {
        if (loadingView != null) {
            loadingView!!.visibility = View.GONE
        }
        if (noDataView != null) {
            noDataView!!.visibility = View.VISIBLE
        }
    }

    val activity: Activity
        get() = this

    fun startActivity(cls: Class<*>?, finishSelf: Boolean, bundle: Bundle?) {
        val intent = Intent(this, cls)
        intent.putExtras(bundle)
        startActivity(intent)
        if (finishSelf) {
            finish()
        }
    }

    fun startActvity(activity: Activity, tClass: Class<*>?, isFinish: Boolean) {
        val intent = Intent(activity, tClass)
        startActivity(intent)
        if (isFinish) {
            activity.finish()
        }
    }

    val apiService: ApiService
        get() = RestClient.getInstance().callRetrofit()
    val context: Context
        get() = this

    fun showToast(txt: String) {
        Toast.makeText(mActivity, "" + txt, Toast.LENGTH_LONG).show()
    }

    fun datePickerDialog(editText: EditText) {
        val listener = OnDateSetListener { view, year, month, dayOfMonth ->
            var month = month
            month += 1
            val selectedDate = "$year-$month-$dayOfMonth"
            editText.text.clear()
            editText.setText(selectedDate)
        }
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val dialog = DatePickerDialog(context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                listener,
                year, month, day)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.datePicker.minDate = System.currentTimeMillis() - 1000
        dialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    fun showAlertDialog(title: String?, message: String?) {
        val builder: androidx.appcompat.app.AlertDialog.Builder
        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            androidx.appcompat.app.AlertDialog.Builder(context, R.style.DialogTheme)
        } else {
            androidx.appcompat.app.AlertDialog.Builder(context)
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok) { dialog, which -> dialog.dismiss() }
                .setIcon(R.drawable.sync)
                .show()
    }

    fun showFinishAlertDialog(title: String?, message: String?) {
        val builder: androidx.appcompat.app.AlertDialog.Builder
        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            androidx.appcompat.app.AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert)
        } else {
            androidx.appcompat.app.AlertDialog.Builder(context)
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    dialog.dismiss()
                    finish()
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
    }

    val currentDate: String
        get() {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            var month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            month += 1
            return "$year-$month-$day"
        }

    protected fun addFragment(@IdRes containerViewId: Int,
                              fragment: Fragment,
                              fragmentTag: String) {
        supportFragmentManager
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit()
    }

    protected fun replaceFragment(@IdRes containerViewId: Int,
                                  fragment: Fragment,
                                  fragmentTag: String,
                                  backStackStateName: String?) {
        supportFragmentManager
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit()
    }

    protected fun removeFragment(fragment: Fragment?) {
        supportFragmentManager.beginTransaction().remove(fragment!!).commit()
    }

    fun toJson(src: Any?): String {
        return if (src == null) {
            toJson(JsonNull.INSTANCE)
        } else GsonBuilder().serializeNulls().create().toJson(src)
    }

    fun localCash(): SharedPreferencesEnum {
        return SharedPreferencesEnum.getInstance(activity)
    }

    /*
    public void getGpsLocation() {
        gps = new GPSTracker(BaseActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            // Toast.makeText(this, "lt:" + latitude + "\n" + "lng:" + longitude, Toast.LENGTH_SHORT).show();

        }
    }
*/
    val isExitFromAppDialog: Unit
        get() {
            val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
            alertDialogBuilder.setMessage(resources.getString(R.string.dialoge_text))
            alertDialogBuilder.setPositiveButton(resources.getString(R.string.yes)
            ) { dialog, which ->
                dialog.dismiss()
                finish()
            }
            alertDialogBuilder.setNegativeButton(resources.getString(R.string.no)) { dialog, which -> dialog.dismiss() }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

    fun getltd(): Double {
        return latitude
    }

    /*
    public String getLocalAddress(double latitude, double longitude) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        if (gps.canGetLocation()) {
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                return address + ", " + city + ", " + state + ", " + country + ", " + postalCode + ", " + knownName;
            } catch (IOException e) {
                e.printStackTrace();
                return "IO error, not found";
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return "IndexOutOfBound not found";
            }
        }

        return "location not found";
    }
*/
    private val intentData: Bundle?
        private get() = intent.extras

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        isExitFromAppDialog
    } /*    // when app first time load
    public void homeFragment() {
        ProfileFragment homeFragment = new ProfileFragment();
        fragmentTransaction(homeFragment, getResources().getString(R.string.profile));
    }*/

    companion object {
        var navItemIndex = 0

        // tags used to attach the fragments
        private const val TAG_HOME = "home"
        private const val TAG_PHOTOS = "photos"
        private const val TAG_MOVIES = "movies"
        private const val TAG_NOTIFICATIONS = "notifications"
        private const val TAG_SETTINGS = "settings"
        var CURRENT_TAG = TAG_HOME

        @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}