package dave.example.com.trinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;


import dave.example.com.trinder.Utils.API.*;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Dave on 03/03/15.
 */
public class VerifyEmailActivity extends ActionBarActivity {

    private Toolbar toolbar;
    @InjectView(R.id.emailField) EditText emailField;
    @InjectView(R.id.submitButton) Button submitButton;
    @InjectView(R.id.progressSpinner) ProgressBar progressSpinner;
    private ActionBarActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        
        progressSpinner.setVisibility(View.GONE);
        mActivity = this;
    }
    
    @OnClick(R.id.submitButton)
    public void submit() {
        // start loading icon.
        progressSpinner.setVisibility(View.VISIBLE);
        String email = emailField.getText().toString();
        try {
            APIClient.getInstance().verifyEmail(email, new Callback<Boolean>() {
            public void execute(Boolean isValidEmail) {
                if (isValidEmail) {
                    // tell user to verify. Start checking every 30 seconds. Stop loading icon.
                    progressSpinner.setVisibility(View.GONE);
                    Intent intent = new Intent(mActivity, UpdateProfileActivity.class); //TEMP*****
                    startActivity(intent);                                              //END TEMP
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                         @Override
                        public void run() {
                            checkIfVerified();
                        }
                    }, 0, 30000);
                }
                else {
                    // tell user they can't use app. Stop loading icon. 
                    progressSpinner.setVisibility(View.GONE);
                }
            } 
        });   
        }
        catch(JSONException e) {
            // Tell user to try again. Stop loading icon
            progressSpinner.setVisibility(View.GONE);
        }
    }

    private void checkIfVerified() {
//        try {
//            APIClient.getInstance().checkIfCurrentUserIsVerified(new Callback<Boolean>() {
//                public void execute(Boolean isVerified) {
//                    if (isVerified) {
//                            // go to update profile activity.
//                        Intent intent = new Intent(mActivity, UpdateProfileActivity.class);
//                        startActivity(intent);
//                    }
//                }
//            });
//        }
//        catch (JSONException e) {
//            // do nothing
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }



}
