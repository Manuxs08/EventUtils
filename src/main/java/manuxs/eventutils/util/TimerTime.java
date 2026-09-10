package manuxs.eventutils.util;

public class TimerTime {
    private String screen_time_txt;
    private String formatted_time_txt;
    private int seconds = -1;
    private int minutes = -1;
    private int hours = -1;
    private long millis = -1L;

    public TimerTime(String formatted_time_txt) throws NumberFormatException {
        this.formatted_time_txt = formatted_time_txt;

        try {
            this.init();
            this.screen_time_txt = String.format("%02d:%02d:%02d", this.hours, this.minutes, this.seconds);
        } catch (NumberFormatException e) {
            this.screen_time_txt = "00:00:00";
            throw e;
        }
    }

    public String getFormattedText() {
        return this.formatted_time_txt;
    }

    public String getHudTimeText() {
        return this.screen_time_txt;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public int getHours() {
        return this.hours;
    }

    public long getMillis() {
        return this.millis;
    }

    public void setMillis(long millis) {
        this.millis = Math.max(0L, millis);
        this.updateFromMillis();
        this.screen_time_txt = String.format("%02d:%02d:%02d", this.hours, this.minutes, this.seconds);
        this.formatted_time_txt = this.hours + "h " + this.minutes + "m " + this.seconds + "s";
    }

    private void updateFromMillis() {
        int raw_seconds = (int)Math.ceil((double)this.millis / (double)1000.0F);
        this.hours = raw_seconds / 3600;
        raw_seconds %= 3600;
        this.minutes = raw_seconds / 60;
        this.seconds = raw_seconds % 60;
    }

    private void init() throws NumberFormatException {
        String[] txt_parts = this.formatted_time_txt.split(" ");
        long millis = 0L;
        int multiply = 1;

        try {
            for(String time : txt_parts) {
                int time_unit;
                if (time.contains("s")) {
                    time_unit = Integer.parseInt(time.replace("s", ""));
                    this.seconds = time_unit;
                    multiply = 1000;
                } else if (time.contains("m")) {
                    time_unit = Integer.parseInt(time.replace("m", ""));
                    this.minutes = time_unit;
                    multiply = 60000;
                } else {
                    if (!time.contains("h")) {
                        throw new NumberFormatException("error");
                    }

                    time_unit = Integer.parseInt(time.replace("h", ""));
                    this.hours = time_unit;
                    multiply = 3600000;
                }

                millis += (long)time_unit * (long)multiply;
            }

            this.millis = millis;
        } catch (NumberFormatException e) {
            this.seconds = 0;
            this.minutes = 0;
            this.hours = 0;
            this.millis = 0L;
            throw e;
        }
    }
}
