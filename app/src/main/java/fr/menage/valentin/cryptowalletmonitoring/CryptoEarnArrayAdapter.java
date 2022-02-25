package fr.menage.valentin.cryptowalletmonitoring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CryptoEarnArrayAdapter extends ArrayAdapter<Crypto> {
    private int resourceLayout;
    private Context mContext;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Crypto crypto = getItem(position);
        if (crypto != null) {
            TextView cryptoNameTextView = v.findViewById(R.id.cryptoName);
            TextView boughtQuantityTextView = v.findViewById(R.id.boughtQuantity);

            if (cryptoNameTextView != null) {
                cryptoNameTextView.setText(crypto.getName());
            }
            if (boughtQuantityTextView != null){
                boughtQuantityTextView.setText(mContext.getString(R.string.quantityBought) + crypto.getQuantity());
            }
        }
        return v;
    }

    public CryptoEarnArrayAdapter(Context context, int ressource, List<Crypto> cryptoList) {
        super(context, ressource, cryptoList);
        this.resourceLayout = ressource;
        this.mContext = context;
    }
}
