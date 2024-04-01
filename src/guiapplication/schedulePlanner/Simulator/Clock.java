package guiapplication.schedulePlanner.Simulator;

import util.Subject;
import util.TimeFormatter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Clock extends Subject<Clock> {
    private double timeSpeed;
    private int currentTimeInt;
    private LocalTime currentTime;
    private long previousTime;

    public Clock(double timeSpeed) {
        this.timeSpeed = timeSpeed;
        this.currentTime = TimeFormatter.intToLocalTime(currentTimeInt);
    }

    public void update(double deltaTime) {
        long now = System.currentTimeMillis();
        long deltaActualTime = now-previousTime;
        if (deltaActualTime > this.timeSpeed * 1000) {
            currentTimeInt++;
            if (currentTimeInt % 100 > 59) {
                currentTimeInt -= 60;
                currentTimeInt += 100;
            }
            if (currentTimeInt > 2359) {
                currentTimeInt -= 2400;
            }
            currentTime = TimeFormatter.intToLocalTime(currentTimeInt);
            previousTime = now;
        }
    }

    public void draw(Graphics2D graphics){

        Rectangle2D minute = new Rectangle2D.Double(0,0,5,50);
        Rectangle2D hour = new Rectangle2D.Double(0,0,5,30);
        Ellipse2D clockBody = new Ellipse2D.Double(150,150,100,100);
        Font digitalTime = new Font("ariel", Font.PLAIN, 50);

        Shape timeShape = digitalTime.createGlyphVector(graphics.getFontRenderContext(), String.valueOf(currentTime)).getOutline();
        AffineTransform digitalTimeTranslation = new AffineTransform();
        digitalTimeTranslation.translate(clockBody.getX(),clockBody.getCenterY()+100);

        AffineTransform minuteRotation = new AffineTransform();
        minuteRotation.translate(clockBody.getCenterX() + 2 ,clockBody.getCenterY());
        minuteRotation.rotate(Math.toRadians(-180 + (360/60.0 * (currentTimeInt%100.0))));
        minuteRotation.translate(-2,-2);

        AffineTransform hourRotation = new AffineTransform();
        hourRotation.translate(clockBody.getCenterX() + 2,clockBody.getCenterY());
        hourRotation.rotate(Math.toRadians(-180 + (360/12.0 * (currentTimeInt/100.0))));
        hourRotation.translate(-2,-2);

        graphics.setColor(Color.WHITE);
        graphics.fill(clockBody);
        graphics.fill(digitalTimeTranslation.createTransformedShape(timeShape));

        graphics.setColor(Color.BLACK);
        graphics.draw(clockBody);
        graphics.draw(digitalTimeTranslation.createTransformedShape(timeShape));
        graphics.fill(minuteRotation.createTransformedShape(minute));
        graphics.fill(hourRotation.createTransformedShape(hour));
    }

    public void updateTimeSpeed(double timeSpeed){
        this.timeSpeed = 1.0 / timeSpeed;
        super.setState(this);
    }

    public double getTimeSpeed() {
        return timeSpeed;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }
}
