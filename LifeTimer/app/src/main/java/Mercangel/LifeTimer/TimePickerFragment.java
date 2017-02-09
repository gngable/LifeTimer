package Mercangel.LifeTimer;
import android.app.*;
import android.os.*;
import java.util.*;
import android.widget.*;
import android.text.format.*;


public class TimePickerFragment extends DialogFragment 
implements TimePickerDialog.OnTimeSetListener {

	public TimePickerDialog.OnTimeSetListener Listener = null;
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        TimePickerDialog tpd = new TimePickerDialog(getActivity(), this, hour, minute,
									DateFormat.is24HourFormat(getActivity()));
								
		tpd.setTitle("Enter time");
									
		return tpd;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (Listener != null){
			Listener.onTimeSet(view, hourOfDay, minute);
		}
    }
}
