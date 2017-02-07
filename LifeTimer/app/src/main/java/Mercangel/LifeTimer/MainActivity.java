package Mercangel.LifeTimer;

import android.app.*;
import android.os.*;
import java.util.*;
import android.widget.*;

public class MainActivity extends Activity 
{
	private Date date = new Date("7/3/1981");
	
	TextView yearsLabel;
	TextView secondsLabel;
	TextView tickLabel;
	
	Timer life = new Timer();
	
	boolean tick = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		yearsLabel = (TextView)findViewById(R.id.years_label);
		secondsLabel = (TextView)findViewById(R.id.seconds_label);
		tickLabel = (TextView)findViewById(R.id.tick_label);
		
		life.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					long millis = System.currentTimeMillis() - date.getTime();
					
					tickLabel.post(new Runnable(){
							@Override
							public void run()
							{
								if (tick){
									tickLabel.setText("Tick");
								} else {
									tickLabel.setText("Tock");
								}
								
								tick = !tick;
							}
					});
				}
			}, 1000, 1000);
    }
}
