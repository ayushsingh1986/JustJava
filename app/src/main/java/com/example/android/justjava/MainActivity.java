package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;


import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
int quantity=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void incrementOrder(View view) {
        quantity++;
        if(quantity >99){
            String incrementMessage="You have reached the upper limit.The no. of cups of coffee can't be more than 99.";
            Toast.makeText(this, incrementMessage, Toast.LENGTH_SHORT).show();
        quantity=2;
        }
        displayQuantity(quantity);
        displayPrice(quantity * 5);
    }
    public void decrementOrder(View view) {

        quantity--;
        if(quantity<1){
            String decrementMessage="You have reached the lower limit.The no. of cups of coffee cannot be less than 1";
            Toast.makeText(this, decrementMessage, Toast.LENGTH_SHORT).show();
            quantity=2;
        }

        displayQuantity(quantity);
        displayPrice(quantity * 5);
    }


    public void submitOrder(View view) {
        EditText text=(EditText) findViewById(R.id.name_field);
        String value=text.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int totalPrice = 0;
        totalPrice= totalPrice + calculatePrice();
        if(hasWhippedCream)
        {
            totalPrice+=(1*quantity);
        }
        if(hasChocolate)
        {
            totalPrice+=(2*quantity);
        }
        String message = createOrderSummary(totalPrice, hasWhippedCream, hasChocolate, value);
        displayMessage(message);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for" +value);
        intent.putExtra(Intent.EXTRA_TEXT,  message);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

    public int calculatePrice(){
        int price=0;
        price  = price + (quantity * 5) ;
        return price;
    }
    public String createOrderSummary(int totalPrice, boolean addWhippedCream, boolean addChocolate, String value){
        String priceMessage="Name:" +value;
        priceMessage+="\n Add WhippedCream ?" +addWhippedCream;
        priceMessage+="\n Add Chocolate ?" +addChocolate;
        priceMessage+="\n Quantity :" +quantity;
        priceMessage+="\n Total:$" +totalPrice;
        priceMessage+="\n Thank You !";
        return priceMessage;

    }
    public void displayMessage(String message)
    {
      TextView orderSummaryTextView=(TextView) findViewById(R.id.order_summary_text_view);
      orderSummaryTextView.setText(message);
      Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
    /* This method displays the given price on the screen.
 */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }


}