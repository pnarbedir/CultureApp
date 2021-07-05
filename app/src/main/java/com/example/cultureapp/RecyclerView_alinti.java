package com.example.cultureapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cultureapp.model.Kullanici;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerView_alinti
{
    private Context mContext;
    private AlintiAdapter alintiAdapters;

    public void setConfig (RecyclerView recyclerView,Context context,List<AlintiOzellik> alint,List<String> keys) {
        mContext =context;
        alintiAdapters = new AlintiAdapter(alint,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(alintiAdapters);


    }


    class AlintiItemView extends RecyclerView.ViewHolder {
        private TextView mYazar;
        private TextView mSayfano;
        private TextView mAlinti;
        private TextView mKitapAd;
        private TextView mUserName;
        private String key;

        public  AlintiItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext)
            .inflate(R.layout.alinti_list_item,parent,false));

            mYazar = (TextView) itemView.findViewById(R.id.txtYazarad2);
            mSayfano = (TextView) itemView.findViewById(R.id.txtSayfano2);
            mAlinti = (TextView) itemView.findViewById(R.id.txtAlinti2);
            mKitapAd = (TextView) itemView.findViewById(R.id.txtKitapAd2);
            mUserName = (TextView) itemView.findViewById(R.id.txtAlintikullanici);

        }
        public void bind(AlintiOzellik al,String key) {
            mYazar.setText(al.getAlintiYazarad());
            mSayfano.setText( "sayfa no:" + al.getAlintiSayfano());
            mAlinti.setText(" ' "+al.getAlintiMetni() + " ' ");
            mKitapAd.setText(al.getAlintiKitap());
            mUserName.setText(al.getKullaniciID());
            this.key= key;
        }


    }
    class AlintiAdapter extends RecyclerView.Adapter<AlintiItemView>{
        private List<AlintiOzellik> mAlintiList;
        private List<String> mKeys;

        public AlintiAdapter(List<AlintiOzellik> mAlintiList, List<String> mKeys){
            this.mAlintiList=mAlintiList;
            this.mKeys = mKeys;
        }


        @Override
        public AlintiItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AlintiItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AlintiItemView holder, int position) {
        holder.bind(mAlintiList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mAlintiList.size();
        }


    }


}
