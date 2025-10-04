package edu.eci.arsw.pinturillo.model;

public class Stroke {
    private long id;
    private double x;
    private double y;
    private String color;
    private double size;
    private long timestamp;

    public Stroke() {}

    public Stroke(long id, double x, double y, String color, double size, long timestamp) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
        this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public double getSize() { return size; }
    public void setSize(double size) { this.size = size; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
