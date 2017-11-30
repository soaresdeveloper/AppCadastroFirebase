package firebase.androidinfnet.info.appcadastrofirebase;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SUCCESS_MESSENGER = "Cadastro realizado com sucesso";
    private static final String ERROR_MESSENGER = "Ocorreu um erro, tente novamente";
    List<EditText> campos;
    private EditText inputName, inputEmail, inputSenha, inputTelefone, inputCelular, inputCpf;
    private Spinner spnCidade;
    private Button btnSave, btnLimpar;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputSenha = (EditText) findViewById(R.id.senha);
        inputTelefone = (EditText) findViewById(R.id.telefone);
        inputCelular = (EditText) findViewById(R.id.celular);
        inputCpf = (EditText) findViewById(R.id.cpf);
        spnCidade = (Spinner) findViewById(R.id.spnCidade);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnLimpar = (Button) findViewById(R.id.btn_limpar);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // ativa persistencia de dados offline
        mFirebaseInstance.setPersistenceEnabled(true);

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");



        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        // Save / update the user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validarCampos()) {
                    String name = inputName.getText().toString();
                    String email = inputEmail.getText().toString();
                    String senha = inputSenha.getText().toString();
                    String cidade = spnCidade.getSelectedItem().toString();
                    Integer telefone = Integer.parseInt(inputTelefone.getText().toString());
                    Integer celular = Integer.parseInt(inputCelular.getText().toString());
                    Integer cpf = Integer.parseInt(inputCpf.getText().toString());

                    createUser(name, email, senha, telefone, celular, cpf, cidade);

                }
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparCampos();
            }
        });

    }


    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String email, String senha, Integer telefone, Integer celular, Integer cpf, String cidade) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth

        userId = mFirebaseDatabase.push().getKey();
        User user = new User(name, email, senha, telefone, celular, cpf, cidade);

        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();

    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {

                    showMessenger(ERROR_MESSENGER);
                    Log.e(TAG, "User data is null!");
                    return;
                }

                showMessenger(SUCCESS_MESSENGER);
                Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);

                limparCampos();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                showMessenger(ERROR_MESSENGER);
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void limparCampos() {
        // clear edit text
        inputEmail.setText("");
        inputName.setText("");
        inputSenha.setText("");
        inputTelefone.setText("");
        inputCelular.setText("");
        inputCpf.setText("");
    }

    private boolean validarCampos() {

        boolean result = true;

        if (campos == null) {
            campos = new ArrayList<>();
        }

        campos.add(inputName);
        campos.add(inputEmail);
        campos.add(inputSenha);
        campos.add(inputTelefone);
        campos.add(inputCelular);
        campos.add(inputCpf);

        for (EditText campo : campos) {
            if (campo.getText().toString().trim().equals("") || campo.getText().toString() == null) {
                campo.setError("Este campo é obrigatório!");
                result = false;
                break;
            }
        }

        return result;
    }

    private void showMessenger(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

}