package fr.menage.valentin.cryptowalletmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseInterface db = null;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseInterface(this);

        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BoughtCryptoFragment boughtCryptoFragment = new BoughtCryptoFragment();
        transaction.add(R.id.dynamicFragment,boughtCryptoFragment);
        transaction.commit();


        FloatingActionButton addButton = this.findViewById(R.id.addFloatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNew(view);
            }
        });

        Button buttonSwitchToBought = this.findViewById(R.id.buttonSwitchToBought);
        Button buttonSwitchToEarn = this.findViewById(R.id.buttonSwitchToEarn);

        buttonSwitchToEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getFragmentManager();
                if (fragmentManager.findFragmentById(R.id.dynamicFragment).getClass()==BoughtCryptoFragment.class) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    EarnCryptoFragment earnCryptoFragment = new EarnCryptoFragment();
                    transaction.replace(R.id.dynamicFragment, earnCryptoFragment);
                    transaction.commit();
                }
            }
        });

        buttonSwitchToBought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getFragmentManager();
                if (fragmentManager.findFragmentById(R.id.dynamicFragment).getClass()==EarnCryptoFragment.class) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.dynamicFragment, boughtCryptoFragment);
                    transaction.commit();
                }
            }
        });
    }

    public void addNew(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View mView = LayoutInflater.from(this).inflate(R.layout.dialog_custom_add, null);
        Button btnValidate = (Button) mView.findViewById(R.id.validateButton);
        Button btnCancel = (Button) mView.findViewById(R.id.cancel_button);

        EditText cryptoNameLabelEditText = (EditText) mView.findViewById(R.id.cryptoNameLabelEditText);
        EditText boughtPriceEditText = (EditText) mView.findViewById(R.id.boughtPriceEditText);
        EditText boughtDateEditText = (EditText) mView.findViewById(R.id.boughtDateEditText);
        EditText boughtQuantityEditText = (EditText) mView.findViewById(R.id.boughtQuantityEditText);
        TextView boughtPriceLabel = (TextView) mView.findViewById(R.id.boughtPriceLabel);
        TextView boughtQuantity = (TextView) mView.findViewById(R.id.cryptoQuantityLabel);
        TextView boughtDate = (TextView) mView.findViewById(R.id.boughtDateLabel);

        ArrayList<String> typeAddList = new ArrayList<>();
        typeAddList.add((String)getText(R.string.boughtType));
        typeAddList.add((String)getText(R.string.earnType));

        Spinner type = (Spinner) mView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,typeAddList);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapterState);
        type.setSelection(0);
        boughtPriceLabel.setVisibility(View.VISIBLE);
        boughtPriceEditText.setVisibility(View.VISIBLE);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectType = adapterState.getItem(i);
                if(selectType.equals((String)getText(R.string.boughtType))){
                    boughtPriceLabel.setVisibility(View.VISIBLE);
                    boughtPriceEditText.setVisibility(View.VISIBLE);
                    boughtDateEditText.setVisibility(View.VISIBLE);
                    boughtQuantity.setText(R.string.quantityBought);
                    boughtDate.setVisibility(View.VISIBLE);

                } else {

                    boughtPriceLabel.setVisibility(View.INVISIBLE);
                    boughtPriceEditText.setVisibility(View.INVISIBLE);
                    boughtDateEditText.setVisibility(View.INVISIBLE);
                    boughtQuantity.setText(R.string.quantityEarn);
                    boughtDate.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cryptoNameLabelEditText.clearComposingText();
        boughtPriceEditText.clearComposingText();
        boughtDateEditText.clearComposingText();
        boughtQuantityEditText.clearComposingText();

        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.getSelectedItem().equals((String) getText(R.string.boughtType))) {
                    if (!cryptoNameLabelEditText.getText().toString().isEmpty() && !boughtPriceEditText.getText().toString().isEmpty() && !boughtDateEditText.getText().toString().isEmpty() && !boughtQuantityEditText.getText().toString().isEmpty()) {
                        Crypto crypto = new Crypto(cryptoNameLabelEditText.getText().toString(), Double.parseDouble(String.valueOf(boughtPriceEditText.getText())), boughtDateEditText.getText().toString(), Double.parseDouble(String.valueOf(boughtQuantityEditText.getText())));
                        db.insertNewCryptoBought(crypto);
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        BoughtCryptoFragment boughtCryptoFragment = new BoughtCryptoFragment();
                        transaction.replace(R.id.dynamicFragment,boughtCryptoFragment);
                        transaction.commit();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Il faut remplir les champs", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!cryptoNameLabelEditText.getText().toString().isEmpty() && !boughtQuantityEditText.getText().toString().isEmpty()) {
                        Crypto crypto = new Crypto(boughtDateEditText.getText().toString(), 0, "", Double.parseDouble(String.valueOf(boughtQuantityEditText.getText())));
                        db.insertNewCryptoEarn(crypto);

                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        EarnCryptoFragment earnCryptoFragment = new EarnCryptoFragment();
                        transaction.replace(R.id.dynamicFragment,earnCryptoFragment);
                        transaction.commit();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Il faut remplir les champs", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}