package app.model;

public class Task {
	private int id;
	private int user_id;
	private String name; 
	private String description; 
	private String category; 
	private String date_start; 
	private int duration;
	private String time_start;
	private String time_end;
	public Task() {
	}
	public Task(int id, int user_id, String name, String description, String category, String date_start, int duration,
			String time_start, String time_end) {
		this.id = id;
		this.user_id = user_id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.date_start = date_start;
		this.duration = duration;
		this.time_start = time_start;
		this.time_end = time_end;
	}
	
	
	public Task(int id, String name, String description, String category, String date_start, int duration,
			String time_start, String time_end) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.date_start = date_start;
		this.duration = duration;
		this.time_start = time_start;
		this.time_end = time_end;
	}
	public Task(String category, int duration) {
		this.category = category;
		this.duration = duration;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDate_start() {
		return date_start;
	}
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	
}
