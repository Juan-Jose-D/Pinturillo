package edu.eci.arsw.pinturillo.service;

import edu.eci.arsw.pinturillo.model.Stroke;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StrokeService {
    private final List<Stroke> strokes = new CopyOnWriteArrayList<>();
    private final AtomicLong seq = new AtomicLong(0);
    private final AtomicLong epoch = new AtomicLong(1); // increments on clear

    public Stroke add(Stroke s) {
        long id = seq.incrementAndGet();
        if (s.getTimestamp() == 0) {
            s.setTimestamp(System.currentTimeMillis());
        }
        s.setId(id);
        strokes.add(s);
        return s;
    }

    public List<Stroke> getSince(long sinceId) {
        if (sinceId <= 0) {
            return Collections.unmodifiableList(new ArrayList<>(strokes));
        }
        List<Stroke> res = new ArrayList<>();
        for (Stroke s : strokes) {
            if (s.getId() > sinceId) {
                res.add(s);
            }
        }
        return res;
    }

    public void clear() {
        strokes.clear();
        seq.set(0);
        epoch.incrementAndGet();
    }

    public long getEpoch() { return epoch.get(); }
}
