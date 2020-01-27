package com.mercangel.LifeTimer;

import android.app.*;
import android.os.*;
import java.util.*;
import android.widget.*;
import android.view.*;
import java.text.*;
import java.math.*;
import java.io.FileWriter;
import java.io.*;
import android.content.*;

public class MainActivity extends Activity 
{
	private Date date = new Date();
	
	SharedPreferences sharedPreferences = null;
	
	private String stat = "";
	
	NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	
	TextView setTimeLabel;
	TextView currentTimeLabel;
	TextView yearsLabel;
	TextView monthsLabel;
	TextView weeksLabel;
	TextView daysLabel;
	TextView hoursLabel;
	TextView minutesLabel;
	TextView secondsLabel;
	TextView tickLabel;
	TextView statLabel;
	
	Timer life = new Timer();
	
	boolean tick = false;
	
	long cadence = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
        setContentView(R.layout.main);
		
		numberFormat.setRoundingMode(RoundingMode.HALF_EVEN);
		
		setTimeLabel = (TextView)findViewById(R.id.set_time_label);
		currentTimeLabel = (TextView)findViewById(R.id.current_time_label);
		yearsLabel = (TextView)findViewById(R.id.years_label);
		monthsLabel = (TextView)findViewById(R.id.months_label);
		weeksLabel = (TextView)findViewById(R.id.weeks_label);
		daysLabel = (TextView)findViewById(R.id.days_label);
		hoursLabel = (TextView)findViewById(R.id.hours_label);
		minutesLabel = (TextView)findViewById(R.id.minutes_label);
		secondsLabel = (TextView)findViewById(R.id.seconds_label);
		tickLabel = (TextView)findViewById(R.id.tick_label);
		statLabel = (TextView)findViewById(R.id.stat_label);
		
		life.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					final long milli = Math.abs((long)(System.currentTimeMillis() - date.getTime()));
					
					
					
					tickLabel.post(new Runnable(){
							@Override
							public void run()
							{
								cadence++;
								
								if (tick && cadence % 10 == 0){
									tickLabel.setText("Tick");
									tick = !tick;
								} else if (cadence % 10 == 0) {
									tickLabel.setText("Tock");
									tick = !tick;
								}
								
								long millis = milli;
								
								if(millis % 10 == 0) millis++;
								
								setTimeLabel.setText("Set: " + date.toString());
								
								currentTimeLabel.setText("Now: " + new Date().toString());
								
								double seconds = millis / 1000.0;
								
								String secondstr = numberFormat.format(seconds);
								int len = Math.min(secondstr.indexOf('.') +2, secondstr.length());
								
								secondsLabel.setText(secondstr.substring(0, len)  + " seconds");
								
								double minutes = (millis / 1000.0) / 60;
								
								String minutestr = numberFormat.format(minutes);
								//len = Math.min(minutestr.indexOf('.') + 3, minutestr.length());
								
								while (minutestr.indexOf('.') + 3 > minutestr.length()){
									minutestr += "0";
								}
								
								minutesLabel.setText(minutestr.substring(0, minutestr.indexOf('.') + 3) + " minutes");
								
								double hours = (((millis / 1000.0) / 60) / 60);

								hoursLabel.setText(numberFormat.format(hours) + " hours");
								
								double days = ((((millis / 1000.0) / 60) / 60) / 24);

								daysLabel.setText(numberFormat.format(days) + " days");
								
								double weeks = ((((millis / 1000.0) / 60) / 60) / 24) / 7;

								weeksLabel.setText(numberFormat.format(weeks) + " weeks");
								
								double months = ((((millis / 1000.0) / 60) / 60) / 24) / (365.0 / 12);

								monthsLabel.setText(numberFormat.format(months) + " months");
								
								double years = ((((millis / 1000.0) / 60) / 60) / 24) / 365;

								yearsLabel.setText(numberFormat.format(years) + " years");
								
								//statLabel.setText(stat);
								
								
							}
					});
				}
			}, 100, 100);
			
			sharedPreferences = getPreferences(MODE_PRIVATE);
			
			if (sharedPreferences.contains("time")){
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
				
				String dateStr = sharedPreferences.getString("time", null);
				
				try
				{
					date =  df.parse(dateStr);
				}
				catch (ParseException e)
				{
					
				}
			} else {
				dateButtonClick(null);
			}
			
			
    }
	
	public void dateButtonClick(View view){
		DatePickerFragment newFragment = new DatePickerFragment();
		newFragment.Listener = new DatePickerDialog.OnDateSetListener(){

			@Override
			public void onDateSet(DatePicker p1, int year, int month, int day)
			{
				final String datestring = Integer.toString(month + 1) + "/" + day + "/" + year;
				//date = new Date(Integer.toString(month) + "/" + day + "/" + year);
				
				TimePickerFragment tfp = new TimePickerFragment();
				tfp.Listener = new TimePickerDialog.OnTimeSetListener(){

					@Override
					public void onTimeSet(TimePicker p1, int hour, int min)
					{
						try{
							String time = "" + hour + ":" + min;
						
							stat = datestring + " " + time;
						
							DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
							date =  df.parse(stat);
							
							SharedPreferences.Editor e = sharedPreferences.edit();
							
							e.putString("time", stat);
							e.commit();
						} catch (Exception ex){
							stat = ex.getMessage();
						}
						//date = new Date(datestring + " " + time);
					}
				};
				
				tfp.show(getFragmentManager(), "time");
			}
			
			
		};
		
		newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public void statusClick(View view){
		AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
		builder.setMessage("Created by Nick Gable (Mercangel Software)");
		builder.show();
	}
}
