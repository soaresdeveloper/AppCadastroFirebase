package firebase.androidinfnet.info.appcadastrofirebase.adapter;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import firebase.androidinfnet.info.appcadastrofirebase.User;

/**
 * Created by soares on 30/11/17.
 */

public class UserRecyclerAdapter extends FirebaseRecyclerAdapter<User, UserViewHolder>{

    public UserRecyclerAdapter(Class<User> modelClass, int modelLayout, Class<UserViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    public UserRecyclerAdapter(Class<User> modelClass, int modelLayout, Class<UserViewHolder> viewHolderClass, DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {
            viewHolder.text1.setText(model.name);
            viewHolder.text2.setText(model.email);
    }
}
