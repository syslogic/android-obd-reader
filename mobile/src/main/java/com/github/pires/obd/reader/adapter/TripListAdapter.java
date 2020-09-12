package com.github.pires.obd.reader.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.pires.obd.reader.R;
import com.github.pires.obd.reader.model.TripRecord;

import java.util.Date;
import java.util.List;

public class TripListAdapter extends ArrayAdapter<TripRecord> {

    /// the Android Activity owning the ListView
    private final Activity activity;

    /// a list of trip records for display
    private final List<TripRecord> records;

    /**
     * DESCRIPTION:
     * Constructs an instance of TripListAdapter.
     *
     * @param activity - the Android Activity instance that owns the ListView.
     * @param records  - the List of TripRecord instances for display in the ListView.
     */
    public TripListAdapter(Activity activity, List<TripRecord> records) {
        super(activity, R.layout.item_trip, records);
        this.activity = activity;
        this.records = records;
    }

    /**
     * DESCRIPTION:
     * Constructs and populates a View for display of the TripRecord at the index
     * of the List specified by the position parameter.
     *
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @SuppressLint("SetTextI18n")
    @Override
    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        // create a view for the row if it doesn't already exist
        if (view == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.item_trip, null);
        }

        // get widgets from the view
        TextView startDate = (TextView) view.findViewById(R.id.startDate);
        TextView columnDuration = (TextView) view.findViewById(R.id.columnDuration);
        TextView rowEngine = (TextView) view.findViewById(R.id.rowEngine);
        TextView rowOther = (TextView) view.findViewById(R.id.rowOther);

        // populate row widgets from record data
        TripRecord record = records.get(position);

        // date
        startDate.setText(record.getStartDateString());
        columnDuration.setText(calcDiffTime(record.getStartDate(), record.getEndDate()));

        String rpmMax = String.valueOf(record.getEngineRpmMax());

        String engineRuntime = record.getEngineRuntime();
        if (engineRuntime == null) {engineRuntime = "None";}

        rowEngine.setText("Engine Runtime: " + engineRuntime + "\tMax RPM: " + rpmMax);
        rowOther.setText("Max speed: " + record.getSpeedMax());
        return view;
    }

    private String calcDiffTime(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        StringBuilder sb = new StringBuilder();
        if (diffDays > 0) {
            sb.append(diffDays + "d");
        }
        if (diffHours > 0) {
            if (sb.length() > 0) {sb.append(" ");}
            sb.append(diffHours + "h");
        }
        if (diffMinutes > 0) {
            if (sb.length() > 0) {sb.append(" ");}
            sb.append(diffMinutes + "m");
        }
        if (diffSeconds > 0) {
            if (sb.length() > 0) {sb.append(" ");}
            sb.append(diffSeconds + "s");
        }
        return sb.toString();
    }

    /**
     * DESCRIPTION:
     * Called by parent when the underlying data set changes.
     *
     * @see android.widget.ArrayAdapter#notifyDataSetChanged()
     */
    @Override
    public void notifyDataSetChanged() {

        // configuration may have changed - get current settings
        //todo
        //getSettings();

        super.notifyDataSetChanged();
    }
}
