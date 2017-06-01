package com.sporlif.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
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

        //El AsyncTask lo pongo hasta que logremos hacer bien la conexión y el envío de datos al servidor
        //new ConnectExample().execute();
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
                builder.setTitle(R.string.act_regist_hint_genre)
                        .setItems(R.array.genre, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                genre = which == 0 ? 'M' : 'F';
                                actRegistSpnGenre.setText(genres[which]);

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
                builder.setTitle(R.string.act_regist_hint_position)
                        .setMultiChoiceItems(R.array.position, selectedPos,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) {
                                            mSelectedItems.add(allPositions[which]);
                                        } else if (mSelectedItems.contains(allPositions[which])) {
                                            System.out.println("Se borró la key " + which + " (" + allPositions[which] + ")");
                                            mSelectedItems.remove(allPositions[which]);
                                        }

                                        selectedPos[which] = isChecked;
                                    }
                                })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                if (mSelectedItems.size() == 0) {
                                    return;
                                }

                                for(int i=0;i<mSelectedItems.size();i++){
                                    System.out.println("Item "+i+": "+mSelectedItems.get(i));
                                }

                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < mSelectedItems.size(); i++) {
                                    sb.append(mSelectedItems.get(i) + (i == mSelectedItems.size() - 1 ? "" : ", "));
                                }
                                actRegistSpnPosition.setText(sb.toString());
                            }
                        })

                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                builder.setCancelable(true);
                builder.show();

            }
        });
        actRegistBtnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Dimos click al spn date");

                DialogFragment datePicker = new DialogTimePicker();
                datePicker.show(getFragmentManager(), "timePicker");
                datePicker.setCancelable(true);

                if (((DialogTimePicker) datePicker).getPickedDate() != null) {
                    birth = ((DialogTimePicker) datePicker).getPickedDate();
                    actRegistBtnRegist.setText(new SimpleDateFormat("yyyy/MM/dd").format(birth));
                }

            }
        });

        actRegistBtnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (actRegistEtEmail.getText().toString().trim().length() == 0) {
                    Toast.makeText(ActRegist.this, "Debe ingresar su correo electrónico", Toast.LENGTH_SHORT);
                    return;
                }

                if (actRegistEtPass.getText().toString().trim().length() == 0) {
                    Toast.makeText(ActRegist.this, "Debe ingresar una contraseña", Toast.LENGTH_SHORT);
                    return;
                }

                if (actRegistEtConfirmPass.getText().toString().trim().length() == 0) {
                    Toast.makeText(ActRegist.this, "Debe confirmar su contraseña", Toast.LENGTH_SHORT);
                    return;
                }

                if (actRegistEtFirstName.getText().toString().trim().length() == 0) {
                    Toast.makeText(ActRegist.this, "Debe ingresar su nombre", Toast.LENGTH_SHORT);
                    return;
                }

                if (actRegistEtLastName.getText().toString().trim().length() == 0) {
                    Toast.makeText(ActRegist.this, "Debe ingresar su apellido", Toast.LENGTH_SHORT);
                    return;
                }

                if (actRegistEtPlace.getText().toString().trim().length() == 0) {
                    Toast.makeText(ActRegist.this, "Debe ingresar su lugar de residencia", Toast.LENGTH_SHORT);
                    return;
                }

                if (genre == null) {
                    Toast.makeText(ActRegist.this, "Debe escoger su género", Toast.LENGTH_SHORT);
                    return;
                }

                if (birth == null) {
                    Toast.makeText(ActRegist.this, "Debe escoger su fecha de nacimiento", Toast.LENGTH_SHORT);
                    return;
                }

                if (actRegistEtPass.getText().toString() == actRegistEtConfirmPass.getText().toString()) {
                    Toast.makeText(ActRegist.this, "Las contraseñas deben coincidir", Toast.LENGTH_SHORT);
                    return;
                }

                String userData[] = {actRegistEtEmail.getText().toString(), actRegistEtPass.getText().toString(),
                        actRegistEtFirstName.getText().toString(), actRegistEtLastName.getText().toString(),
                        actRegistEtPlace.getText().toString(), actRegistEtNickName.getText().toString()};

                new sendUserData(genre, mSelectedItems.toArray(new String[mSelectedItems.size()]), birth, userData).execute();
            }
        });

    }

    private class sendUserData extends DialogAsyncTask {

        private JsonObject res;

        private String email, pass, firstName, lastName, place, nick;
        private char genre;
        private String[] positions;
        private Date birth;

        public sendUserData(Character genre, String[] positions, Date birth, String... data) {
            super();
            this.genre = genre;
            this.positions = positions;
            this.birth = birth;
            this.email = data[0];
            this.pass = data[1];
            this.firstName = data[2];
            this.lastName = data[3];
            this.place = data[4];
            this.nick = data[5];
            ;
        }

        @Override
        protected Object task() {

            int tries = 0;
            do {
                try {

                    ClientHttpRequest request = new ClientHttpRequest(new URL("http://192.168.5.197:8080/server/Prueba").openConnection());
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
                            pob.add("pos" + i, positions[i]);
                        }
                        js.add("selectedPositions", pob);
                    }
                    js.add("birth", new SimpleDateFormat("yyyy-MM-dd").format(birth));

                    request.setParameter("data", js.build().toString());
                    res = Json.createReader(request.post()).readObject();

                    break;

                } catch (Exception e) {
                    e.printStackTrace();
                    if (tries == 3) {
                        //pasar el siguiente sout a dialogo
                        return "TIMEOUT";
                    }
                }
            } while (tries < 3);

            return res.toString();
        }

    }

    public String getClientMessage(ClientHttpRequest request) throws IOException {
        Reader r = new InputStreamReader(request.post(), "UTF-8");
        char[] buffer = new char[4096];
        StringBuilder sb = new StringBuilder();
        for (int len; (len = r.read(buffer)) > 0; )
            sb.append(buffer, 0, len);
        return sb.toString();
    }

}
