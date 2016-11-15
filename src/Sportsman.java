
public class Sportsman {
    private String name;
    private String team;
    private double rating;
    private int sportsmanID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getSportsmanId() {
        return sportsmanID;
    }

    public void setSportsmanId(int sportsmanId) {
        this.sportsmanID = sportsmanId;
    }

    public String toString() {
        return "ФИО: " + name + "Команда: " + team + ", Рейтинг : "+rating;
    }

}