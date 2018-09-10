package com.example.triumf.flow_sensor_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triumf.flow_sensor_app.API.SensorInterface;
import com.example.triumf.flow_sensor_app.Entiry.Sensors;
import com.example.triumf.flow_sensor_app.ItemClickLisener;
import com.example.triumf.flow_sensor_app.R;
import com.example.triumf.flow_sensor_app.SensorDetailsActivity;

import java.util.List;

public class SensorsAdapter  extends RecyclerView.Adapter<SensorsAdapter.MyViewHolder> {

    private List<Sensors> sensors;
    private Context context;
    private SensorInterface api;
    private List<Sensors> sensorsObj;

    private SensorsAdapter adapter;


    public SensorsAdapter(List<Sensors> sensors, Context context) {
        this.sensors = sensors;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sesnor, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.sensorName.setText(sensors.get(position).getName());
        holder.sensorType.setText("Type: "+sensors.get(position).getType());
        holder.sensorDiameter.setText("Diameter: "+sensors.get(position).getDiameter());

        holder.setItemClickLisener(new ItemClickLisener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                Intent intentSensorDetails = new Intent(context, SensorDetailsActivity.class);

                intentSensorDetails.putExtra("sid",sensors.get(position).getSensorid());
                intentSensorDetails.putExtra("name",sensors.get(position).getName());
                intentSensorDetails.putExtra("type",sensors.get(position).getType());
                intentSensorDetails.putExtra("diameter",sensors.get(position).getDiameter());
                intentSensorDetails.putExtra("description",sensors.get(position).getDescription());
                intentSensorDetails.putExtra("inputPin",sensors.get(position).getInputPin());
                intentSensorDetails.putExtra("outputPin",sensors.get(position).getOutputPin());
                intentSensorDetails.putExtra("statusPin",sensors.get(position).getStatusPin());

                context.startActivities(new Intent[]{intentSensorDetails});

                if(isLongClick){
                    Toast.makeText(context,"Long Click: "+sensors.get(position).getSensorid(), Toast.LENGTH_LONG).show();


                }
                else
                {
                    Log.d("tttttttttttttttttt", "123");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private ItemClickLisener itemClickLisener;
        TextView sensorName, sensorType, sensorDiameter;

        public MyViewHolder(View itemView) {
            super(itemView);

            sensorName = (TextView) itemView.findViewById(R.id.sensor_name);
            sensorType = (TextView) itemView.findViewById(R.id.sensor_type);
            sensorDiameter = (TextView) itemView.findViewById(R.id.sensor_diameter);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickLisener(ItemClickLisener itemClickLisener){
            this.itemClickLisener = itemClickLisener;
        }

        @Override
        public void onClick(View v) {
            itemClickLisener.onClick(v,getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickLisener.onClick(v,getAdapterPosition(), true);
            return true;
        }
    }
}
