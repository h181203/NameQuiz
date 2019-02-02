package com.example.randimarie.namequiz;

import android.app.Person;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        View view = inflater.inflate(R.layout.listperson_layout, parent);
        PersonViewHolder holder = new PersonViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int i) {
        Person person = personList.get(i);

        personViewHolder.textView.setText(person.getName());

        personViewHolder.imageview.setImageDrawable(mCtx.getResources().getDrawable(person.getUri()));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }


    class PersonViewHolder extends RecyclerView.ViewHolder{

            ImageView imageview;
            TextView textView;

            public PersonViewHolder(View itemView) {
                super(itemView);

                imageview = itemView.findViewById(R.id.imageView);
                textView = itemView.findViewById(R.id.textViewPersonName);


            }
        }
}

