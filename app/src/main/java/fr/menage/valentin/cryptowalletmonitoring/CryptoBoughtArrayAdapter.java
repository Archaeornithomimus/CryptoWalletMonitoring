package fr.menage.valentin.cryptowalletmonitoring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CryptoBoughtArrayAdapter extends ArrayAdapter<Crypto> {
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
            TextView cryptoBoughtPriceTextView = v.findViewById(R.id.boughtPrice);
            TextView cryptoBoughtDateTextView = v.findViewById(R.id.cryptoDateBought);
            TextView boughtQuantityTextView = v.findViewById(R.id.boughtQuantity);

            if (cryptoNameTextView != null) {
                cryptoNameTextView.setText(crypto.getName());
            }
            if (cryptoBoughtPriceTextView != null) {
                cryptoBoughtPriceTextView.setText(mContext.getString(R.string.boughtFor) + crypto.getBoughtPrice() + "€");
            }
            if (cryptoBoughtDateTextView != null) {
                if (crypto.getBoughtDate() != null) {
                    cryptoBoughtDateTextView.setText(mContext.getString(R.string.boughtOn)+ crypto.getBoughtDate());
                } else {
                    cryptoBoughtDateTextView.setText(mContext.getString(R.string.boughtOnError));
                }
            }
            if (boughtQuantityTextView != null){
                boughtQuantityTextView.setText(mContext.getString(R.string.quantityBought) + crypto.getQuantity());
            }
        }
        return v;
    }

    public CryptoBoughtArrayAdapter(Context context, int ressource, List<Crypto> cryptoList) {
        super(context, ressource, cryptoList);
        this.resourceLayout = ressource;
        this.mContext = context;
    }
}
