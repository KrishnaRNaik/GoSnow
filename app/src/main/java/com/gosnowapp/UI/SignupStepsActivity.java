package com.gosnowapp.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gosnowapp.Business.BusinessConsts;
import com.gosnowapp.Business.GoSnowLoginManager;
import com.gosnowapp.Business.UpdateProfileService;
import com.gosnowapp.Globals;
import com.gosnowapp.Model.Destination;
import com.gosnowapp.Model.User;
import com.gosnowapp.R;
import com.gosnowapp.UI.Adaptors.DestCountryAdaptor;
import com.gosnowapp.UI.Adaptors.DestinationAdaptor;

import java.util.ArrayList;
import java.util.HashMap;

public class SignupStepsActivity extends AppCompatActivity {

    final int SELECT_RIDERTYPE = 1;
    final int SELECT_RIDERLEVEL = 2;
    final int SELECT_NEXTDESTINATION =  3;
    final int SELECT_INTEREST = 4;

    int signupStep = SELECT_RIDERTYPE;

    User currentUser=null;

    HashMap<String,Integer>SkillLevelMap = new HashMap<String, Integer>();
    HashMap<String,Integer>InterestMap = new HashMap<String, Integer>();
    ArrayList<Destination> destinationList = new ArrayList<Destination>();
    HashMap<String, ArrayList<Destination>> destinationMap = new HashMap<String,ArrayList<Destination>>();
    ArrayList<String> countrylist = new ArrayList<String>();

    Dialog SelectDestinationDlg  = null;
    Dialog SelectSkillLevelDlg =  null;
    Dialog SelectInterestDlg = null;
    Dialog ShowDestinationDlg = null;
    Dialog ShowDestCountryDlg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_steps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide the action bar
        getSupportActionBar().hide();

        Globals globals = Globals.getInstance();
        currentUser= globals.getUser();
        ShowSelectRiderTypeDlg();
    }

    public  void ShowSelectRiderTypeDlg( ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dlgView = inflater.inflate(R.layout.select_ridertype,null);
        builder.setView(dlgView);
        final AlertDialog dialog = builder.create();

        TextView snowBoarderView = (TextView)dlgView.findViewById(R.id.snowborderid);
        Globals globals = Globals.getInstance();
        final User currentuser = globals.getUser();
        snowBoarderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.setRiderTypeId(String.valueOf(BusinessConsts.SNOWBOARDER_ID));
                dialog.cancel();
                selectRiderLevel();
            }
        });

        TextView skierView = (TextView)dlgView.findViewById(R.id.skierid);
        skierView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.setRiderTypeId(String.valueOf(BusinessConsts.SKIER_ID));
                dialog.cancel();
                selectRiderLevel();
            }
        });

        dialog.setCancelable(false);

        // allow back navigation
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                    return  true;
                }
                return false;
            }
        });
        dialog.show();

    }


    public  void selectRiderLevel( ) {

        signupStep = SELECT_RIDERLEVEL;

        SkillLevelMap.put(getString(R.string.title_firsttimer),1);
        SkillLevelMap.put(getString(R.string.title_beginner),2);
        SkillLevelMap.put(getString(R.string.title_intermediate),3);
        SkillLevelMap.put(getString(R.string.title_advanced),4);
        SkillLevelMap.put(getString(R.string.title_mountainpro),5);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dlgView = inflater.inflate(R.layout.select_riderlevel,null);
        builder.setView(dlgView);

        if(currentUser.getSkillLevelID() != null && !currentUser.getSkillLevelID().isEmpty())
        {
            RadioButton btn1 = (RadioButton)dlgView.findViewById(R.id.firsttimerid);
            if(btn1.getText().toString().equals(currentUser.getSkillLevel()))
            {
                if(!btn1.isChecked())
                    btn1.toggle();
            }
            else
                btn1 = (RadioButton) dlgView.findViewById(R.id.beginnerid);
            if (btn1.getText().toString().equals(currentUser.getSkillLevel())) {
                if (!btn1.isChecked())
                    btn1.toggle();
            }
            else
                btn1 = (RadioButton) dlgView.findViewById(R.id.intermid);
            if (btn1.getText().toString().equals(currentUser.getSkillLevel())) {
                if (!btn1.isChecked())
                    btn1.toggle();
            }
            else
                btn1 = (RadioButton) dlgView.findViewById(R.id.advncedid);
            if (btn1.getText().toString().equals(currentUser.getSkillLevel())) {
                if (!btn1.isChecked())
                    btn1.toggle();
            }
            else
                btn1 = (RadioButton) dlgView.findViewById(R.id.mountproid);
            if (btn1.getText().toString().equals(currentUser.getSkillLevel())) {
                if (!btn1.isChecked())
                    btn1.toggle();
            }

        }


        RadioGroup levelGrp = (RadioGroup)dlgView.findViewById(R.id.riderlevelgroup);
        levelGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                RadioButton checkedBtn = (RadioButton)dlgView.findViewById(checkedId);
                String skillLevel = checkedBtn.getText().toString();
                currentUser.setSkillLevel(skillLevel);
                int skilevelId =  SkillLevelMap.get(skillLevel);
                currentUser.setSkillLevelID(String.valueOf(skilevelId));

            }
        });
        SelectSkillLevelDlg = builder.create();
        SelectSkillLevelDlg.setCancelable(false);
        SelectSkillLevelDlg.show();

        // allow back navigation
        SelectSkillLevelDlg.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                    return  true;
                }
                return false;
            }
        });

    }


    public  void selectNextDestination( ) {

        signupStep = SELECT_NEXTDESTINATION;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dlgView = inflater.inflate(R.layout.selected_destination_layout,null);
        builder.setView(dlgView);

        if(currentUser.getNextDestinationName() != null && !currentUser.getNextDestinationName().isEmpty())
        {
            TextView destinationView = (TextView)dlgView.findViewById(R.id.seldestid);
            destinationView.setText(currentUser.getNextDestinationName());
        }

        SelectDestinationDlg = builder.create();
        SelectDestinationDlg.setCancelable(false);
        SelectDestinationDlg.show();

        // allow back navigation
        SelectDestinationDlg.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                    return  true;
                }
                return false;
            }
        });


    }

    public void ShowInterest(View view)
    {
        SelectInterestDlg.cancel();

        InterestMap.put(getString(R.string.title_allterrain),4);
        InterestMap.put(getString(R.string.title_onpiste),1);
        InterestMap.put(getString(R.string.title_offpiste),2);
        InterestMap.put(getString(R.string.title_parks),3);
        InterestMap.put(getString(R.string.title_apres),5);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dlgView = inflater.inflate(R.layout.select_interest,null);
        builder.setView(dlgView);

        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

         if(currentUser.getInterestName()!= null && !currentUser.getInterestName().isEmpty())
        {
            TextView selectedInterestview = (TextView)dlgView.findViewById(R.id.selinterestid);
            selectedInterestview.setText(currentUser.getInterestName());
            RadioButton btn1 = (RadioButton) dlgView.findViewById(R.id.interes1id);
            if(btn1.getText().equals(currentUser.getInterestName()))
            {
                if(!btn1.isChecked())
                    btn1.toggle();
            }
            else   btn1 = (RadioButton) dlgView.findViewById(R.id.interes2id);
            if(btn1.getText().equals(currentUser.getInterestName()))
            {
                if(!btn1.isChecked())
                    btn1.toggle();
            }
            else   btn1 = (RadioButton) dlgView.findViewById(R.id.interes3id);
            if(btn1.getText().equals(currentUser.getInterestName()))
            {
                if(!btn1.isChecked())
                    btn1.toggle();
            }
            else   btn1 = (RadioButton) dlgView.findViewById(R.id.interes4id);
            if(btn1.getText().equals(currentUser.getInterestName()))
            {
                if(!btn1.isChecked())
                    btn1.toggle();
            }
            else   btn1 = (RadioButton) dlgView.findViewById(R.id.interes5id);
            if(btn1.getText().equals(currentUser.getInterestName()))
            {
                if(!btn1.isChecked())
                    btn1.toggle();
            }

        }
        RadioGroup interestGrp = (RadioGroup)dlgView.findViewById(R.id.riderinterestgroup);
        interestGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                RadioButton checkedBtn = (RadioButton)dlgView.findViewById(checkedId);
                String interestName = checkedBtn.getText().toString();
                currentUser.setInterestName(interestName);
                int selectedInterestId =  InterestMap.get(interestName);
                currentUser.setInterestId(String.valueOf(selectedInterestId));

                dialog.cancel();
                selectInterest();
            }
        });


        // allow back navigation
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                    return  true;
                }
                return false;
            }
        });

    }


    public  void selectInterest( ) {

        signupStep = SELECT_INTEREST;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dlgView = inflater.inflate(R.layout.show_interest,null);
        builder.setView(dlgView);

        TextView selectedInterest = (TextView) dlgView.findViewById(R.id.interestid);
        if(currentUser.getInterestName() != null && !currentUser.getInterestName().isEmpty())
            selectedInterest.setText(currentUser.getInterestName());

        SelectInterestDlg = builder.create();
        SelectInterestDlg.setCancelable(false);
        SelectInterestDlg.show();

        // allow back navigation
        SelectInterestDlg.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                    return  true;
                }
                return false;
            }
        });

    }

    public void GetDestinationList(View view)
    {
        SelectDestinationDlg.cancel();
        GetDestinations getDestinations = new GetDestinations();
        getDestinations.execute();
    }

    public void showDestinationCountries()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dlgView = inflater.inflate(R.layout.destination_list,null);
        ListView destList = (ListView) dlgView.findViewById(R.id.destlistid);

        TextView countryView = (TextView)dlgView.findViewById(R.id.countryid);
        countryView.setVisibility(View.GONE);

        for (String key: destinationMap.keySet()) {
            countrylist.add(key);
        }
        DestCountryAdaptor destAdaptor = new DestCountryAdaptor(this,countrylist);
        destList.setAdapter(destAdaptor);
        builder.setView(dlgView);

        ShowDestCountryDlg = builder.create();
        ShowDestCountryDlg.setCancelable(false);
        ShowDestCountryDlg.show();

        // allow back navigation
        ShowDestCountryDlg.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                    return  true;
                }
                return false;
            }
        });


    }

    public void showDestinationList(String country)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dlgView = inflater.inflate(R.layout.destination_list,null);
        ListView destList = (ListView) dlgView.findViewById(R.id.destlistid);

        TextView countryView = (TextView)dlgView.findViewById(R.id.countryid);
        countryView.setText(country);

        DestinationAdaptor destAdaptor = new DestinationAdaptor(this,destinationList);
        destList.setAdapter(destAdaptor);
        builder.setView(dlgView);

        ShowDestinationDlg = builder.create();
        ShowDestinationDlg.setCancelable(false);
        ShowDestinationDlg.show();

        // allow back navigation
        ShowDestinationDlg.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                    return  true;
                }
                return false;
            }
        });

    }
    public void showNextStep(View view)
    {
       // User currentUser = Globals.getInstance().getUser();
        switch (signupStep)
        {
            case  SELECT_RIDERLEVEL:
            {
               /* LayoutInflater inflater = getLayoutInflater();
                View dlgView = inflater.inflate(R.layout.select_riderlevel,null);
                RadioGroup riderLevelSel = (RadioGroup)dlgView.findViewById(R.id.riderlevelgroup);
                int selectedlevelid = riderLevelSel.getCheckedRadioButtonId();
                RadioButton  btn = (RadioButton)dlgView.findViewById(selectedlevelid);
                String skillLevel = (String )btn.getText();
                currentUser.setSkillLevel(skillLevel);
                currentUser.setSkillLevelID(String.valueOf(SkillLevelMap.get(skillLevel)));
*/
                if(SelectSkillLevelDlg != null)
                    SelectSkillLevelDlg.cancel();
                selectNextDestination();

                break;
            }
            case SELECT_NEXTDESTINATION:
            {
               /* LayoutInflater inflater = getLayoutInflater();
                View dlgView = inflater.inflate(R.layout.selected_destination_layout,null);
                TextView selectedDestination = (TextView) dlgView.findViewById(R.id.seldestid);
                if(selectedDestination.getText().toString().equals("Select"))
                {
                    Toast.makeText(this,"Please select destination",Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if(currentUser.getNextDestinationName() == null || currentUser.getNextDestinationName().isEmpty() )
                {
                    Toast.makeText(this,"Please select destination",Toast.LENGTH_SHORT).show();
                    return;
                }
                SelectDestinationDlg.cancel();
                selectInterest();
                break;
            }
            case SELECT_INTEREST:
            {
                SelectDestinationDlg.cancel();
                LayoutInflater inflater = getLayoutInflater();
                View dlgView = inflater.inflate(R.layout.show_interest,null);
                TextView selectedInterestView = (TextView) dlgView.findViewById(R.id.interestid);

                if(currentUser.getInterestName() == null || currentUser.getInterestName().isEmpty())
                {
                    Toast.makeText(this,"Please select Interest",Toast.LENGTH_SHORT).show();
                    return;
                }
                String selectedInterest = selectedInterestView.getText().toString();
                currentUser.setInterestName(selectedInterest);

                UpdateProfile updateProfile = new UpdateProfile();
                updateProfile.execute();

                break;
            }
            default:
                break;

        }

    }

    public void showPreviousStep(View view)
    {
        // User currentUser = Globals.getInstance().getUser();
        switch (signupStep)
        {
            case  SELECT_RIDERLEVEL:
            {
               /* LayoutInflater inflater = getLayoutInflater();
                View dlgView = inflater.inflate(R.layout.select_riderlevel,null);
                RadioGroup riderLevelSel = (RadioGroup)dlgView.findViewById(R.id.riderlevelgroup);
                int selectedlevelid = riderLevelSel.getCheckedRadioButtonId();
                RadioButton  btn = (RadioButton)dlgView.findViewById(selectedlevelid);
                String skillLevel = (String )btn.getText();
                currentUser.setSkillLevel(skillLevel);
                currentUser.setSkillLevelID(String.valueOf(SkillLevelMap.get(skillLevel)));

                if(SelectSkillLevelDlg != null)
                    SelectSkillLevelDlg.cancel();
                selectNextDestination();
*/
                SelectSkillLevelDlg.cancel();
                ShowSelectRiderTypeDlg();
                break;
            }
            case SELECT_NEXTDESTINATION:
            {
               /* LayoutInflater inflater = getLayoutInflater();
                View dlgView = inflater.inflate(R.layout.selected_destination_layout,null);
                TextView selectedDestination = (TextView) dlgView.findViewById(R.id.seldestid);
                String nextDestinationName = selectedDestination.getText().toString();
                int nextDestinationId = 0;
                currentUser.setNextDestinationName(nextDestinationName);
                currentUser.setNextDestinationId(nextDestinationId);*/
                SelectDestinationDlg.cancel();
                selectRiderLevel();
                break;
            }
            case SELECT_INTEREST:
            {
                SelectInterestDlg.cancel();
                selectNextDestination();
                break;
            }
            default:
                break;
        }
    }

    private class GetDestinations extends AsyncTask<String, Integer, String> {

        String error = null;
        TransparentProgressDialog  progressDialog;
        public void onPreExecute() {
            progressDialog = new TransparentProgressDialog(SignupStepsActivity.this,R.drawable.spinner_blue,"");
            progressDialog.show();

        }

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {

            // Perform sign in
            error = GoSnowLoginManager.getLoginManager().GetDestinationList(SignupStepsActivity.this,destinationMap);
            return error;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String errorMsg) {
            // cancel the progress dialog
            progressDialog.dismiss();
            String title = getString(R.string.title_error);

            // If sign in is successful then show RideNow screen
            if (errorMsg == null) {
               showDestinationCountries();
            }
            else {
                // Show the error message
                Toast.makeText(SignupStepsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }

            return;

        }
    }

    private class UpdateProfile extends AsyncTask<String, Integer, String> {

        String error = null;
        TransparentProgressDialog  progressDialog;
        public void onPreExecute() {
            progressDialog = new TransparentProgressDialog(SignupStepsActivity.this,R.drawable.spinner_blue,"");
            progressDialog.show();

        }

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {

            // Perform sign in
            UpdateProfileService updateProfile = UpdateProfileService.getUpdateProfileService();
            error = updateProfile.UpdateProfile(SignupStepsActivity.this);
            return error;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String errorMsg) {
            // cancel the progress dialog
            progressDialog.dismiss();
            String title = getString(R.string.title_error);

            // If sign in is successful then show RideNow screen
            if (errorMsg == null) {
                Intent intent = new Intent(SignupStepsActivity.this, RideNowActivity.class);
                startActivity(intent);
                finish();

            }
            else {
                // Show the error message
                Toast.makeText(SignupStepsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }

            return;

        }
    }

    public  void onDestinationSelected(int position)
    {
        currentUser.setNextDestinationId(Integer.parseInt(destinationList.get(position).getDestinationId()));
        currentUser.setNextDestinationName(destinationList.get(position).getDestinationName());
        if(ShowDestinationDlg != null)
            ShowDestinationDlg.cancel();

        selectNextDestination();

    }

    public void onCountrySelected(int position)
    {
        destinationList = destinationMap.get(countrylist.get(position));
        showDestinationList(countrylist.get(position));

        if(ShowDestCountryDlg != null)
            ShowDestCountryDlg.cancel();


    }


}
