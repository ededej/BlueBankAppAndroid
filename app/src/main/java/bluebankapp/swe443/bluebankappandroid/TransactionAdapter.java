package bluebankapp.swe443.bluebankappandroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    Context context;
    int layoutId;
    Transaction[] data;

    public TransactionAdapter(Context c, int layoutId, Transaction[] data) {
        super(c, layoutId, data);
        this.layoutId = layoutId;
        this.context = c;
        this.data = data;
    }

    // Method that grabs data from the individual transactions and populates the items.
    // Item template should be transaction_list_item.xml.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TransactionHolder holder;

        if (row == null) {
            // Make a new one and create a holder for it, set it as the tag for that row.
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);

            holder = new TransactionHolder();
            holder.date = (TextView) row.findViewById(R.id.date);
            holder.otherparty = (TextView) row.findViewById(R.id.otherparty);
            holder.amount = (TextView) row.findViewById(R.id.amount);
            holder.balance = (TextView) row.findViewById(R.id.balance);
            row.setTag(holder);
        } else {
            // Get the holder that's already made.
            holder = (TransactionHolder) row.getTag();
        }

        // Populate the fields of the holder/row.
        Transaction t = data[position];

        holder.date.setText(t.type);
        holder.otherparty.setText(t.acc1 + " --> ");
        //System.out.println("AMOUNT: "+Double.toString(t.amount));
        holder.amount.setText(String.format("$%.2f", t.amount));
        holder.balance.setText((t.acc2.equals("") ? t.acc1 : t.acc2));

        //if type is withdraw, red.  if deposit, green.  if transfer, blue.  if undo, grey.
        if (t.type.equals("Withdraw")){ holder.amount.setTextColor(Color.RED); }
        else if (t.type.equals("Deposit")){ holder.amount.setTextColor(Color.GREEN); }
        else if (t.type.equals("Transfer")){ holder.amount.setTextColor(Color.BLACK); }
        else { holder.amount.setTextColor(Color.GRAY); }

        return row;
    }

    private static class TransactionHolder {
        TextView date;
        TextView otherparty;
        TextView amount;
        TextView balance;
    }
}
