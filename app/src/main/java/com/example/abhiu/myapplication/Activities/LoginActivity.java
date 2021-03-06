package com.example.abhiu.myapplication.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abhiu.myapplication.R;
import com.example.abhiu.myapplication.Utilities.ConnectionDetector;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class LoginActivity extends FirebaseLoginBaseActivity{

    Firebase firebaseRef;
    EditText userNameET;
    EditText passwordET;
    String mName;
    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;
    private final String INTERNET_ERROR = "Error:No Internet Connection";
    public static String getFIREBASEREF() {
        return FIREBASEREF;
    }

    /* String Constants */
    private static final String FIREBASEREF = "https://fixthis.firebaseio.com/";
    private static final String FIREBASE_ERROR = "Firebase Error";
    private static final String USER_ERROR = "User Error";
    private static final String LOGIN_SUCCESS = "Login Success";
    private static final String USER_CREATION_SUCCESS =  "Successfully created user";
    private static final String USER_CREATION_ERROR =  "User creation error";
    private static final String EMAIL_INVALID =  "email is invalid :";
    private static final String LOGGED_OUT =  "Logged out :";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ////////////////////////////////
        Firebase.setAndroidContext(this);
        //////////////////////////////////////////
        firebaseRef = new Firebase(FIREBASEREF);
        super.onCreate(savedInstanceState);
        ///////////////internet///////////
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            ////////////////////////////////////////////////////////////////////////////////
            try {
                PackageInfo info = getPackageManager().getPackageInfo(
                        "com.example.abhiu.myapplication.Activities",
                        PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Package name error", Toast.LENGTH_SHORT);

            } catch (NoSuchAlgorithmException e) {
                Toast.makeText(getApplicationContext(), "No algorith found", Toast.LENGTH_SHORT);
            }
            ////////////////////////////////////////////////////////////////////////////////
            setContentView(R.layout.activity_login);
            userNameET = (EditText) findViewById(R.id.edit_text_email);
            passwordET = (EditText) findViewById(R.id.edit_text_password);
            Button login = (Button) findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginActivity.this.showFirebaseLoginPrompt();
                }
            });

            Button createButton = (Button) findViewById(R.id.button);
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createUser();
                }
            });


        } else {
            Toast.makeText(LoginActivity.this, INTERNET_ERROR, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {
        Snackbar snackbar = Snackbar.
                make(userNameET, FIREBASE_ERROR + firebaseLoginError.message, Snackbar.LENGTH_SHORT);
        snackbar.show();
        resetFirebaseLoginPrompt();
    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {
        Snackbar snackbar = Snackbar
                .make(userNameET, USER_ERROR + firebaseLoginError.message, Snackbar.LENGTH_SHORT);
        snackbar.show();
        resetFirebaseLoginPrompt();
    }

    @Override
    public Firebase getFirebaseRef() {
        return firebaseRef;
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData) {
        switch (authData.getProvider()) {
            case "password":
                mName = (String) authData.getProviderData().get("email");
                break;
            default:
                mName = (String) authData.getProviderData().get("displayName");
                break;
        }
           Toast.makeText(getApplicationContext(), LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();

        if (isInternetPresent) {
            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
           myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            LoginActivity.this.startActivity(myIntent);
        } else {
            Toast.makeText(LoginActivity.this, INTERNET_ERROR, Toast.LENGTH_LONG).show();

            ///////////check///////////
        try {
                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                alertDialog.show();
       }
            catch(Exception e)
            {
              //  Log.d(Constants.TAG, "Show Dialog: "+e.getMessage());
            }
        }
        }


    @Override
    public void onFirebaseLoggedOut() {
        //2Toast.makeText(getApplicationContext(), LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // All providers are optional! Remove any you don't want.
        setEnabledAuthProvider(AuthProviderType.PASSWORD);
        setEnabledAuthProvider(AuthProviderType.GOOGLE);
//      setEnabledAuthProvider(AuthProviderType.FACEBOOK);
        // setEnabledAuthProvider(AuthProviderType.TWITTER);
    }

    // Validate email address for new accounts.
    private boolean isEmailValid(String email) {
        boolean isGoodEmail = (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            userNameET.setError(EMAIL_INVALID + email);
            return false;
        }
        return true;
    }

    // create a new user in Firebase
    public void createUser() {
        if(userNameET.getText() == null ||  !isEmailValid(userNameET.getText().toString())) {
            return;
        }
        firebaseRef.createUser(userNameET.getText().toString(), passwordET.getText().toString(),
                new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        Snackbar snackbar = Snackbar.make(userNameET, USER_CREATION_SUCCESS, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Snackbar snackbar = Snackbar.make(userNameET, USER_CREATION_ERROR, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                });
    }
        ////////////////////////////// getting data from google /////////////////////////////////////
                @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if(result.isSuccess()) {
                        GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
                      String  name = googleSignInAccount.getDisplayName();
                      String  email = googleSignInAccount.getEmail();
                      Uri profilePicUrl = googleSignInAccount.getPhotoUrl();
                      SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

                               SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", name);
                        editor.putString("email", email);
                        editor.putString("profile_pic", profilePicUrl.toString());
                       editor.commit();
                    }
            }
                //////////////////////////////////////////////////////////////////////////////////////////////////
}

/*
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

}
*/