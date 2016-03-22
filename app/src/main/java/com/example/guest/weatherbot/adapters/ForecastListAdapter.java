package com.example.guest.weatherbot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.weatherbot.R;
import com.example.guest.weatherbot.models.ForecastStatus;
import com.example.guest.weatherbot.services.TemperatureConverter;

import org.apache.commons.lang3.text.WordUtils;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ForecastViewHolder> {
    private ArrayList<ForecastStatus> mForecasts = new ArrayList<>();
    private Context mContext;

    public ForecastListAdapter(Context context, ArrayList<ForecastStatus> forecasts) {
        mContext = context;
        mForecasts = forecasts;
    }

    @Override
    public ForecastListAdapter.ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item, parent, false);
        ForecastViewHolder viewHolder = new ForecastViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ForecastListAdapter.ForecastViewHolder holder, int position) {
        holder.bindForecastStatus(mForecasts.get(position));
    }

    @Override
    public int getItemCount() {
        return mForecasts.size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.forecastDateTextView) TextView mDateTextView;
        @Bind(R.id.forecastTimeTextView) TextView mTimeTextView;
        @Bind(R.id.forecastTempTextView) TextView mTempTextView;
        @Bind(R.id.forecastDescriptionTextView) TextView mDescriptionTextView;
        private Context mContext;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindForecastStatus(ForecastStatus status) {
            DateFormat dfDate = DateFormat.getDateInstance(DateFormat.SHORT);

            SimpleDateFormat sdfTime = new SimpleDateFormat("h:mm a zzz");

            Resources res = mContext.getResources();
            mTempTextView.setText(String.format(res.getString(R.string.temperature_output), TemperatureConverter.toFahrenheit(status.getTemp())));
            mDescriptionTextView.setText(WordUtils.capitalize(status.getDescription()));
            mDateTextView.setText(dfDate.format(status.getDate()));
            mTimeTextView.setText(sdfTime.format(status.getDate()));
        }
    }
}
