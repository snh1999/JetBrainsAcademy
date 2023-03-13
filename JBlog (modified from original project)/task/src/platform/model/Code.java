package platform.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@Entity
public class Code {
    @Id
    @Column
//    @GeneratedValue(strategy = GenerationType)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;
    @Column
    private String code;
    @Column
    private String date;
    @Column
    private int views = 0;
    @Column
    private long time = 0;

    @JsonIgnore
    private boolean isTimeLimSet = false;

    @JsonIgnore
    private boolean isViewsSet = false;
    public Code() {}

    private Code(String code) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.date = getDateTime();
    }
    private Code(String code, int views, long time) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.date = getDateTime();
        if(views > 0) {
            this.views = views;
            this.isViewsSet = true;
        }
        if (time > 0) {
            this.time = time;
            this.isTimeLimSet = true;
        }
    }
    protected Code createCode(String code) {
        return new Code(code);
    }
    protected Code createCode(String code, int views, long time) {
        Code newCode = new Code(code, views, time);
        return newCode;
    }

    public String getId() {
        return id;
    }
    public String getCode() {
        return code;
    }
    public String getDate() {
        return date;
    }
    private static String getDateTime() {
        String[] temp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).split("T");
        return temp[0] + " " + temp[1];
    }
    @JsonIgnore
    public LocalDateTime getInTimeFormat() {
        String[] temp = this.getDate().split(" ");
        return LocalDateTime.parse(temp[0] + "T" + temp[1]);
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    @JsonIgnore
    public boolean isCountSet() {
        return this.isViewsSet;
    }
    @JsonIgnore
    public boolean isTimeSet() {
        return this.isTimeLimSet;
    }

    @JsonIgnore
    public boolean isSecretCode() {
        return isCountSet() || isTimeSet();
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date='" + date + '\'' +
                '}';
    }


}
