package com.example.randimarie.namequiz;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private Context mCtx;
    private List<Person> personList;

    public PersonAdapter(Context mCtx, List<Person> personList) {
        this.mCtx = mCtx;
        this.personList = personList;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.listperson_layout, null);
        PersonViewHolder holder = new PersonViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int i) {
        Person person = personList.get(i);

        personViewHolder.textView.setText(person.getName());

        personViewHolder.imageview.setImageURI(Uri.parse(person.getImage()));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }


    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            ImageView imageview;
            TextView textView;

            public PersonViewHolder(View itemView) {
                super(itemView);

                imageview = itemView.findViewById(R.id.imageView);
                textView = itemView.findViewById(R.id.textViewPersonName);

                itemView.setOnClickListener(this);

            }


        @Override
        public void onClick(View v) {
            Person person = personList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, UpdateDeleteActivity.class);
            intent.putExtra("person", person);
            mCtx.startActivity(intent);
        }
    }
}

