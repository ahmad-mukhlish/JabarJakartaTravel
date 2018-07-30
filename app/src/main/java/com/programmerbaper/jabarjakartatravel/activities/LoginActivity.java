package com.programmerbaper.jabarjakartatravel.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;
import com.programmerbaper.jabarjakartatravel.entities.User;
import com.programmerbaper.jabarjakartatravel.networking.QueryUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    public static User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        TextView register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        email = findViewById(R.id.edUsername);
        password = findViewById(R.id.edPassword);

        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginAsyncTask(getBaseContext()).execute(Trayek.BASE_PATH + Trayek.POST_AUTH);
            }
        });
    }

    private class LoginAsyncTask extends AsyncTask<String, Void, String> {

        private Context mContext;
        private Boolean status = false;

        LoginAsyncTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            try {
                JSONObject root = new JSONObject(QueryUtils.postWithHttp(QueryUtils.parseStringLinkToURL(urls[0]), createJsonMessage()));
                status = root.getBoolean("status");
                JSONObject data = root.getJSONObject("data");
                JSONObject user = data.getJSONObject("user");
                LoginActivity.user = new User(user.getInt("id") + "", user.getString("name"), user.getString("email"), data.getString("token"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;


        }


        @Override
        protected void onPostExecute(String response) {
            if (status) {
                Toast.makeText(getBaseContext(), "Login Sukses", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else
                Toast.makeText(getBaseContext(), "Username atau password tidak dikenal", Toast.LENGTH_SHORT).show();
        }


        private String createJsonMessage() {

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.accumulate("email", email.getText().toString());
                jsonObject.accumulate("password", password.getText().toString());
                ;


            } catch (JSONException e) {
                Log.e("Kelas Login", "Error when create JSON message", e);
            }

            return jsonObject.toString();

        }
    }

}
