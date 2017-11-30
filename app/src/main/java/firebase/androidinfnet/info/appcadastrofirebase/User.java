package firebase.androidinfnet.info.appcadastrofirebase;

/**
 * Created by Soares on 28/11/2017.
 */

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User {

    public String name;
    public String email;
    public String senha;
    public Integer telefone;
    public Integer celular;
    public Integer cpf;
    public String cidade;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, String senha, Integer telefone, Integer celular, Integer cpf, String cidade) {
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.celular = celular;
        this.cpf = cpf;
        this.cidade = cidade;
    }
}