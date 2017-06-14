package com.sporlif.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sporlif.R;
import com.sporlif.dialogs.DialogTimePicker;
import com.sporlif.utils.ClientHttpRequest;
import com.sporlif.utils.DialogAsyncTask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ActRegist extends Activity {

    private ImageView actRegistImgProfile;
    private EditText actRegistEtFirstName, actRegistEtLastName, actRegistEtPlace, actRegistEtNickName,
            actRegistEtEmail, actRegistEtPass, actRegistEtConfirmPass;
    private TextView actRegistSpnGenre, actRegistSpnPosition, actRegistSpnBirth;
    private Button actRegistBtnRegist;

    private Character genre;
    private ArrayList<String> mSelectedItems = new ArrayList<>();
    private String[] allPositions;
    private boolean[] selectedPos;
    private Date birth;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.act_regist);

        allPositions = ActRegist.this.getResources().getStringArray(R.array.position);
        selectedPos = new boolean[allPositions.length];

        if (saved == null) {

        } else {
            for (int i = 0; i < selectedPos.length; i++) {
                selectedPos[i] = false;
            }
        }

        launchWidgets();
        launchEvents();
    }

    public void launchWidgets() {
        actRegistImgProfile = (ImageView) findViewById(R.id.actRegistImgProfile);
        actRegistEtEmail = (EditText) findViewById(R.id.actRegistEtEmail);
        actRegistEtPass = (EditText) findViewById(R.id.actRegistEtPass);
        actRegistEtConfirmPass = (EditText) findViewById(R.id.actRegistEtConfirmPass);
        actRegistEtFirstName = (EditText) findViewById(R.id.actRegistEtFirstName);
        actRegistEtLastName = (EditText) findViewById(R.id.actRegistEtLastName);
        actRegistEtPlace = (EditText) findViewById(R.id.actRegistEtPlace);
        actRegistEtNickName = (EditText) findViewById(R.id.actRegistEtNickName);
        actRegistSpnGenre = (TextView) findViewById(R.id.actRegistSpnGenre);
        actRegistSpnPosition = (TextView) findViewById(R.id.actRegistSpnPosition);
        actRegistSpnBirth = (TextView) findViewById(R.id.actRegistSpnBirth);
        actRegistBtnRegist = (Button) findViewById(R.id.actRegistBtnRegist);
    }

    public void launchEvents() {

        actRegistSpnGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] genres = ActRegist.this.getResources().getStringArray(R.array.genre);

                AlertDialog.Builder builder = new AlertDialog.Builder(ActRegist.this);
                builder.setTitle(R.string.act_regist_hint_genre);
                builder.setItems(R.array.genre, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int index) {

                        genre = (index == 0 ? 'M' : 'F');
                        actRegistSpnGenre.setText(genres[index]);

                    }
                });
                builder.setCancelable(true);
                builder.show();

            }
        });

        actRegistSpnPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(ActRegist.this);

                builder.setTitle(R.string.act_regist_hint_position);
                builder.setMultiChoiceItems(R.array.position, selectedPos,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int index, boolean isChecked) {

                                if (isChecked) {
                                    mSelectedItems.add(allPositions[index]);
                                } else if (mSelectedItems.contains(allPositions[index])) {
                                    mSelectedItems.remove(allPositions[index]);
                                }

                                selectedPos[index] = isChecked;//cuando se borra todas las pocisiones, se borran también este arreglo

                            }
                        });
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        if (mSelectedItems.size() == 0) {
                            return;
                        }

                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            sb.append(mSelectedItems.get(i) + (i == mSelectedItems.size() - 1 ? "" : ", "));
                        }
                        actRegistSpnPosition.setText(sb.toString());
                    }

                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.setCancelable(true);
                builder.show();

            }
        });

        actRegistSpnBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DialogTimePicker();
                datePicker.show(ActRegist.this.getFragmentManager(), DialogTimePicker.TAG_DATE_PICKER);
                datePicker.setCancelable(true);
            }
        });

        actRegistBtnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (actRegistEtEmail.getText().toString().trim().length() == 0) {
                    Toast.makeText(ActRegist.this, "Debe ingresar su correo electrónico", Toast.LENGTH_SHORT).show();
                    actRegistEtEmail.requestFocus();
                    return;
                }

                if (actRegistEtPass.getText().toString().trim().length() == 0) {
                    Toast.makeText(ActRegist.this, "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
                    actRegistEtPass.requestFocus();
                    return;
                }

                if (actRegistEtConfirmPass.getText().toString().trim().length() == 0) {
                    Toast.makeText(ActRegist.this, "Debe confirmar su contraseña", Toast.LENGTH_SHORT).show();
                    actRegistEtConfirmPass.requestFocus();
                    return;
                }

                if (actRegistEtFirstName.getText().toString().trim().length() == 0) {
                    Toast.makeText(ActRegist.this, "Debe ingresar su nombre", Toast.LENGTH_SHORT).show();
                    actRegistEtFirstName.requestFocus();
                    return;
                }

                if (actRegistEtLastName.getText().toString().trim().length() == 0) {
                    Toast.makeText(ActRegist.this, "Debe ingresar su apellido", Toast.LENGTH_SHORT).show();
                    actRegistEtLastName.requestFocus();
                    return;
                }

                if (actRegistEtPlace.getText().toString().trim().length() == 0) {
                    Toast.makeText(ActRegist.this, "Debe ingresar su lugar de residencia", Toast.LENGTH_SHORT).show();
                    actRegistEtPlace.requestFocus();
                    return;
                }

                if (genre == null) {
                    Toast.makeText(ActRegist.this, "Debe escoger su género", Toast.LENGTH_SHORT).show();
                    actRegistSpnGenre.requestFocus();
                    return;
                }

                if (birth == null) {
                    Toast.makeText(ActRegist.this, "Debe escoger su fecha de nacimiento", Toast.LENGTH_SHORT).show();
                    actRegistSpnBirth.requestFocus();
                    return;
                }

                if (actRegistEtPass.getText().toString() == actRegistEtConfirmPass.getText().toString()) {
                    Toast.makeText(ActRegist.this, "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
                    actRegistEtConfirmPass.requestFocus();
                    return;
                }

                String userData[] = {actRegistEtEmail.getText().toString(), actRegistEtPass.getText().toString(),
                        actRegistEtFirstName.getText().toString(), actRegistEtLastName.getText().toString(),
                        actRegistEtPlace.getText().toString(), actRegistEtNickName.getText().toString()};

                new sendUserData(ActRegist.this, genre,
                        mSelectedItems.toArray(new String[mSelectedItems.size()]),
                        birth, userData).execute();
            }
        });

    }

    public void setBirthLabel(String date) {
        actRegistSpnBirth.setText(date);
    }

    private class sendUserData extends DialogAsyncTask<String> {

        private JsonObject res;

        private String email, pass, firstName, lastName, place, nick;
        private char genre;
        private String[] positions;
        private Date birth;

        public sendUserData(ActRegist ctx,char genre, String[] positions, Date birth, String... data) {
            super(ctx);
            this.genre = genre;
            this.positions = positions;
            this.birth = birth;
            this.email = data[0];
            this.pass = data[1];
            this.firstName = data[2];
            this.lastName = data[3];
            this.place = data[4];
            this.nick = data[5];
        }

        @Override
        protected String task() {

            int tries = 0;
            do {
                try {

                    ClientHttpRequest request = new ClientHttpRequest(new URL("https://wsporlif-project.herokuapp.com/index.php?op=dnktis").openConnection());
                    request.setConnectTimeout(ClientHttpRequest.CONNECT_TIMEOUT);
                    request.setParameter("poolName", "pruebads");
                    request.setParameter("tz", "-5");

                    JsonObjectBuilder js = Json.createObjectBuilder();
                    js.add("email", email);
                    js.add("pass", pass);
                    js.add("firstName", firstName);
                    js.add("lastName", lastName);
                    js.add("place", place);
                    if (nick != null) {
                        js.add("nick", nick);
                    }
                    js.add("genre", genre);

                    if (positions != null && positions.length > 0) {
                        JsonObjectBuilder pob = Json.createObjectBuilder();
                        for (int i = 0; i < positions.length; i++) {
                            pob.add("pos" + (i + 1), positions[i]);
                        }
                        js.add("selectedPositions", pob);
                    }
                    js.add("birth", new SimpleDateFormat("yyyy-MM-dd").format(birth));

                    request.setParameter("data", js.build().toString());
                    res = Json.createReader(request.post()).readObject();

                    break;

                } catch (Exception e) {
                    e.printStackTrace();
                    tries++;
                    if (tries == 3) {
                        //pasar el siguiente sout a dialogo
                        return "TIMEOUT";
                    }
                }
            } while (tries < 3);

            return res.toString();
        }

        @Override
        protected void result(String res) {
            System.out.println("El resultado es: " + res);
        }

    }

}
