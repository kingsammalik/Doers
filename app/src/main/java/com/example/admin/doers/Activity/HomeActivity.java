package com.example.admin.doers.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.doers.Constants.Constants;
import com.example.admin.doers.Fragments.MoreVideos;
import com.example.admin.doers.Interface.BaseModel;
import com.example.admin.doers.Preference.ComminPreference;
import com.example.admin.doers.Fragments.Contact_fragment;
import com.example.admin.doers.Fragments.Home_fragment;
import com.example.admin.doers.Fragments.Myfavourite_fragment;
import com.example.admin.doers.Fragments.Profile_Fragment;
import com.example.admin.doers.Fragments.Rankers_fragment;
import com.example.admin.doers.Fragments.Teamgallery;
import com.example.admin.doers.Fragments.Terms_Fragment;
import com.example.admin.doers.R;
import com.example.admin.doers.UI.BottomNavigationViewBehavior;
import com.example.admin.doers.UI.CustomTypefaceSpan;
import com.example.admin.doers.Users.Users;
import com.example.admin.doers.Videos_fragment;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,BaseModel.FavListener {

    TextView textView;
    public Toolbar tool;
    boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    String fragtag = "";
    ImageView imageView;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    private  BottomNavigationView navigations;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!isConnected(HomeActivity.this)) buildDilaog(HomeActivity.this).show();

        else {


        }
        BaseModel.setFavListener(this);

        Fragment fragment = new Home_fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        tool = (Toolbar) findViewById(R.id.toolbar);
        tool.setTitle("Home");
        setSupportActionBar(tool);

        navigations = (BottomNavigationView) findViewById(R.id.navigation);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigations.getLayoutParams();
        layoutParams.setBehavior(new com.example.admin.doers.BottomListBehaviour.BottomNavigationViewBehavior());

        navigations.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, tool, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Users users = new Users(HomeActivity.this);
        View header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.username);
        imageView = header.findViewById(R.id.headerimage);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();
                //uploadMultipart();


            }
        });

        //name.setText("welcome,"+users.getName());
        name.setText("Welcome," + ComminPreference.getPreferencesString(getApplicationContext(), "user"));

        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            applyFontToMenuItem(mi);
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/OpenSans/OpenSans-ExtraBold.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            tool.setTitle("Home");
            Home_fragment fragment = new Home_fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment, fragtag).addToBackStack(fragtag).commit();
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_profile) {

            tool.setTitle("Profile Settings");
            fragtag = "Profile Settings";
            Profile_Fragment fragment = new Profile_Fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment, fragtag).addToBackStack(fragtag).commit();
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_rankers) {

            tool.setTitle("Our Rankers");
            fragtag = "Our Rankers";
            Rankers_fragment fragment = new Rankers_fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment, fragtag).addToBackStack(fragtag).commit();
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_terms) {

            tool.setTitle("Terms Conditions");
            fragtag = "Terms Condition";
            Terms_Fragment fragment = new Terms_Fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment, fragtag).addToBackStack(fragtag).commit();
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_contact) {
            tool.setTitle("Contact Us");
            fragtag = "Contact Us";
            Contact_fragment fragment = new Contact_fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment, fragtag).addToBackStack(fragtag).commit();
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Team Doers");
                String sAux = "\nPlease Download Team Doers Android Application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=the.package.id \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }

        } else if (id == R.id.nav_logout) {

            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.logout)
                    .setTitle("Closing Application")
                    .setMessage("Do You want to Logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  CommonClass.getGlobalInstance().Logout();
                            ComminPreference.removeAllPreference(HomeActivity.this);
                            Intent intent = new Intent(getBaseContext(), MainPage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(new Intent(HomeActivity.this, MainPage.class));
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }

        return true;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    tool.setTitle("Home");
                    Home_fragment fragment = new Home_fragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                    return true;
                case R.id.teamgallery:
                    tool.setTitle("Team Gallery");
                    Teamgallery fragment1 = new Teamgallery();
                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    fragmentManager1.beginTransaction().replace(R.id.container, fragment1).commit();
                    return true;
                case R.id.myfav:
                    tool.setTitle("My Favourites");
                    Myfavourite_fragment fragment2 = new Myfavourite_fragment();
                    FragmentManager fragmentManager2 = getSupportFragmentManager();
                    fragmentManager2.beginTransaction().replace(R.id.container, fragment2).commit();
                    return true;
            }
            return false;
        }
    };


    public boolean isConnected(Context context) {


        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mobile != null && mobile.isConnectedOrConnecting() || (wifi != null && wifi.isConnectedOrConnecting()))

                return true;
            else return false;
        }
        return false;
    }

    public AlertDialog.Builder buildDilaog(Context context)

    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Internet Problem!")
                .setMessage("Your Internet Connection has been Lost..Press ok to exit")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.dismiss();
            }
        }).show();


        return builder;

    }


    public void uploadMultipart() {
        //getting name for the image


        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                    .addParameter("mobile",ComminPreference.getPreferencesString(HomeActivity.this,"mobile"))
                    .addFileToUpload(path, "profileImage") //Adding file
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }


    }


    @Override
    public void favClicked() {
        navigations.setSelectedItemId(R.id.myfav);
        /*Myfavourite_fragment fragment2 = new Myfavourite_fragment();
        FragmentManager fragmentManager2 = getSupportFragmentManager();
        fragmentManager2.beginTransaction().replace(R.id.container, fragment2).commit();
        ((HomeActivity) getActivity()).tool.setTitle("My Favourites");*/
    }
}
