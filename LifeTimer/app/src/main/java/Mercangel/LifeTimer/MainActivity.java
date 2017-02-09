package Mercangel.LifeTimer;

import android.app.*;
import android.os.*;
import java.util.*;
import android.widget.*;
import android.view.*;
import java.text.*;

public class MainActivity extends Activity 
{
	private Date date = new Date("7/3/1981");
	
	private String stat = "";
	
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
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
        setContentView(R.layout.main);
		
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
					final long millis = System.currentTimeMillis() - date.getTime();
					
					tickLabel.post(new Runnable(){
							@Override
							public void run()
							{
								if (tick){
									tickLabel.setText("Tick");
								} else {
									tickLabel.setText("Tock");
								}
								
								long seconds = millis / 1000;
								
								secondsLabel.setText(Long.toString(seconds) + " seconds");
								
								long minutes = (millis / 1000) / 60;
								
								minutesLabel.setText(Long.toString(minutes) + " minutes");
								
								long hours = (((millis / 1000) / 60) / 60);

								hoursLabel.setText(Long.toString(hours) + " hours");
								
								long days = ((((millis / 1000) / 60) / 60) / 24);

								daysLabel.setText(Long.toString(days) + " days");
								
								long weeks = ((((millis / 1000) / 60) / 60) / 24) / 7;

								weeksLabel.setText(Long.toString(weeks) + " weeks");
								
								long months = ((((millis / 1000) / 60) / 60) / 24) / 30;

								monthsLabel.setText(Long.toString(months) + " months");
								
								long years = ((((millis / 1000) / 60) / 60) / 24) / 365;

								yearsLabel.setText(Long.toString(years) + " years");
								
								statLabel.setText(stat);
								
								tick = !tick;
							}
					});
				}
			}, 1000, 1000);
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
}
