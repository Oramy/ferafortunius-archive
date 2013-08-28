package gui.jeu.utils;

public class TimeController {
	private int pas;
	
	private int hour, minute, second;
	
	public TimeController(int pas){
		this.pas = pas;
		hour = 11;
	}
	public void updateTime(float delta){
		addSeconds((int) (Math.ceil(pas * delta)));
	}
	private void addSeconds(int seconds){
		this.second += seconds;
		addMinutes((this.second) / 60);
		this.second = this.second % 60;
	}
	private void addMinutes(int minutes){
		this.minute += minutes;
		addHours((this.minute) / 60);
		this.minute = this.minute % 60;
	}
	private void addHours(int hours){
		this.hour += hours;
		
		if(this.hour > 24)
			this.hour = 0;
	}
	public int getPas() {
		return pas;
	}
	public void setPas(int pas) {
		this.pas = pas;
	}
	public float getNightValue(){
		float heure = (float)hour + (((float)minute + ((float)second / 60)) / 60);
		
		float nightValue = (float)(heure - 12) / 12f;
		if(nightValue < 0)
			nightValue = 1f + nightValue;
		if(8 < heure && heure < 16)
			nightValue = 0;
		if(heure <= 8)
			nightValue =  1f - (heure - 4) / 4f;
		if(heure >= 16)
			nightValue =  (heure - 16f)/ 4f;
		if(heure >= 20)
			nightValue =  1f;
		if(heure <= 4)
			nightValue =  1f;
		
		return nightValue;
	}
	private String normalizeNumber(int number){
		if(number < 10)
			return "0" + number;
		return number + "";
	}
	public int getHour(){
		return hour;
	}
	public String getStringHour(){
		return normalizeNumber(hour);
	}
	public int getMinute(){
		return minute;
	}
	public String getStringMinute(){
		return normalizeNumber(minute);
	}
	public int getSecond(){
		return second;
	}
	public String getStringSecond(){
		return normalizeNumber(second);
	}
}
