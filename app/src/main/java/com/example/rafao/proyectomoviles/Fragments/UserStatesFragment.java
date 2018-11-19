package com.example.rafao.proyectomoviles.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.rafao.proyectomoviles.Interface.IUserStates;
import com.example.rafao.proyectomoviles.Models.Usuario;
import com.example.rafao.proyectomoviles.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class UserStatesFragment extends Fragment implements IUserStates {

    private int state;
    private ArrayList<Usuario> list;

    public UserStatesFragment(int state){
        this.state = state;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    Usuario product = item.getValue(Usuario.class);
                    if(product.admin != 1 && product.habilitado == state) {
                        list.add(product);
                    }
                }

                RecyclerView recyclerView = view.findViewById (R.id.recview);
                recyclerView.setLayoutManager (new LinearLayoutManager(getContext (),  LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter (new UserStatesAdapter(getContext (), list));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        root.addValueEventListener(event);

    }

}

class UserStatesAdapter extends RecyclerView.Adapter<UserStateViewHolder> {

    private Context _context;
    private ArrayList<Usuario> users;

    public UserStatesAdapter(Context context, ArrayList<Usuario> list) {
        _context = context;
        users = list;
    }

    @NonNull
    @Override
    public UserStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from (_context).inflate (R.layout.user_list_item, viewGroup, false);
        return new UserStateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserStateViewHolder userStateViewHolder, int i) {
        userStateViewHolder.bind(users.get(i));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

class UserStateViewHolder extends RecyclerView.ViewHolder{

    private TextView user;
    private Switch state;
    private Usuario Setuser;

    public UserStateViewHolder(@NonNull View itemView) {
        super(itemView);

        //user = itemView.findViewById(R.id.textView1);
        state = itemView.findViewById(R.id.switch1);

    }

    public void bind(Usuario usuario) {
        Setuser = usuario;
        String name = Setuser.nombre+" "+Setuser.apellidos;
        state.setText(name);
        if(usuario.habilitado != 0){
            state.setChecked(true);
        }else{
            state.setChecked(false);
        }
        state.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                Setuser.habilitado = 1;
            }else {
                Setuser.habilitado = 0;
            }
            DatabaseReference root = FirebaseDatabase.getInstance().getReference("/Usuarios").child(Setuser.id);
            root.setValue(Setuser);
        });
    }
}
