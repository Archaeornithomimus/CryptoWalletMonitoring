package fr.menage.valentin.cryptowalletmonitoring;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarnCryptoFragment extends Fragment {
    DatabaseInterface db = null;
    CryptoBoughtArrayAdapter cryptoBoughtArrayAdapter;
    ArrayList<Crypto> cryptoArrayList;
    ListView listView;
    private Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.earn_fragment, container, false);
        context = getActivity();
        db = new DatabaseInterface(context);

        listView = (ListView) view.findViewById(R.id.listCryptoEarn);
        printListCrypto();
        return view;
    }

    public void printListCrypto(){
        cryptoArrayList = db.getAllCryptoEarn();
        cryptoBoughtArrayAdapter = new CryptoBoughtArrayAdapter(getActivity(), R.layout.bought_list, cryptoArrayList);
        listView.setAdapter(cryptoBoughtArrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Crypto crypto = (Crypto)parent.getItemAtPosition(position);
                db.deleteCrypto(crypto);
                cryptoArrayList.remove(crypto);
                cryptoBoughtArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}
