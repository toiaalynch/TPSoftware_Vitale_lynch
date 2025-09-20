package org.udesa.giftcard.model;

public class FakeClock extends Clock {
    private long currentTime;

    public FakeClock(long startTime) {
        this.currentTime = startTime;
    }

    @Override
    public long now() {
        return currentTime;
    }

    public void advance(long millis) {
        currentTime += millis;
    }
}
