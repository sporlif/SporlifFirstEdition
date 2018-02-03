package com.sporlif.activities.user.recoverFrg;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sporlif.R;
import com.sporlif.models.user.CtrRecoverPass;
import com.sporlif.utils.UtilsForViews;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrgEnterMail extends Fragment{

    private UtilsForViews utilsForViews;
    private View view;

    private EditText frgEnterMailEmail;
    private Button frgEnterMailButton;
    private Handler handler;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private InputMethodManager mgr;

    private String email;
    private String message;

    private CtrRecoverPass ctrRecoverPass;
    private FrgEnterCode frgEnterCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frg_enter_mail, container, false);

        launchWidgets();
        launchEvents();

        return view;

    }

    private String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private void launchWidgets(){

        utilsForViews = new UtilsForViews();
        utilsForViews.createToolbar(view, (AppCompatActivity)getActivity(), getString(R.string.frg_enter_mail_tag), true);

        frgEnterMailEmail = (EditText) view.findViewById(R.id.frgEnterMailEmail);
        frgEnterMailButton = (Button) view.findViewById(R.id.frgEnterMailButton);

        handler = new Handler();
        mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    private void launchEvents(){

        frgEnterMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mgr.hideSoftInputFromWindow(frgEnterMailEmail.getWindowToken(), 0);

                if(frgEnterMailEmail.getText().length() == 0){
                    Toast.makeText(getActivity(), R.string.cannot_is_empty, Toast.LENGTH_SHORT).show();
                    return;
                }

                frgEnterMailButton.setEnabled(false);

                ctrRecoverPass = new CtrRecoverPass();
                ctrRecoverPass.setContext(getActivity().getApplicationContext());

                setEmail(frgEnterMailEmail.getText().toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        setMessage(ctrRecoverPass.sendEmail(getEmail()));

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                if(getMessage().equals(getString(R.string.successful_send))) {
                                    frgEnterCode = new FrgEnterCode();
                                    frgEnterCode.setEmail(getEmail());
                                    fragmentManager = getFragmentManager();
                                    transaction = fragmentManager.beginTransaction();
                                    transaction.replace(R.id.frlRecoverPassContainer, frgEnterCode);
                                    transaction.commit();
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle(R.string.frg_enter_mail_dialog_title_no_exist_acc);
                                    builder.setMessage(R.string.frg_enter_mail_dialog_message_no_exist_acc);
                                    builder.setPositiveButton(R.string.search_again_button, null);
                                    builder.show();
                                }

                                frgEnterMailButton.setEnabled(true);

                            }
                        });

                    }
                }).start();

            }
        });

    }

}
