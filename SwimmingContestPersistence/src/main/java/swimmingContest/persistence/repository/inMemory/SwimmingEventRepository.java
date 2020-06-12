package swimmingContest.persistence.repository.inMemory;

import org.springframework.stereotype.Component;
import swimmingContest.model.SwimmingEvent;
import swimmingContest.persistence.SwimmingEventInterface;
import swimmingContest.persistence.repository.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class SwimmingEventRepository implements SwimmingEventInterface {
    private Map<Long, SwimmingEvent> allSwimmingEvents;

    public SwimmingEventRepository() {
        allSwimmingEvents=new TreeMap<Long, SwimmingEvent>();
        populateSwimmingEvent();
    }

    private void populateSwimmingEvent() {
        SwimmingEvent swimmingEvent1 = new SwimmingEvent("backstroke",100);
        swimmingEvent1.setId(1L);
        allSwimmingEvents.put(swimmingEvent1.getId(),swimmingEvent1);
        SwimmingEvent swimmingEvent2 = new SwimmingEvent("butterfly",200);
        swimmingEvent2.setId(2L);
        allSwimmingEvents.put(swimmingEvent2.getId(),swimmingEvent2);
        SwimmingEvent swimmingEvent3 = new SwimmingEvent("freestyle",300);
        swimmingEvent3.setId(3L);
        allSwimmingEvents.put(swimmingEvent3.getId(),swimmingEvent3);
        SwimmingEvent swimmingEvent4 = new SwimmingEvent("mixed",400);
        swimmingEvent4.setId(4L);
        allSwimmingEvents.put(swimmingEvent4.getId(),swimmingEvent4);
    }

    @Override
    public SwimmingEvent findSwimmingEventByStyleDistance(String swimmingStroke, Integer distance) {
        List<SwimmingEvent> swimmingEventRes = new ArrayList<SwimmingEvent>();
        allSwimmingEvents.forEach((id,swimmingEvent)->{
          if(swimmingEvent.getDistance().equals(distance) && swimmingEvent.getSwimmingStroke().equals(swimmingStroke)){
              swimmingEventRes.add(swimmingEvent);
          }
        });
        if (swimmingEventRes.size()!=0){
            SwimmingEvent res = new SwimmingEvent(swimmingEventRes.get(0).getSwimmingStroke(),swimmingEventRes.get(0).getDistance());
            res.setId(swimmingEventRes.get(0).getId());
            return res;
        }
        return null;
    }

    @Override
    public int size() {
        return allSwimmingEvents.values().size();
    }

    @Override
    public SwimmingEvent[] getSwimmingEvents() {
        return allSwimmingEvents.values().toArray(new SwimmingEvent[allSwimmingEvents.size()]);
    }

    @Override
    public void save(SwimmingEvent entity) {
        System.out.println("[SwimmingEventRepository] save swimmingEvent - entering");
        if(allSwimmingEvents.containsKey(entity.getId()))
            throw new RepositoryException("swimmingEvent Id already exists: "+entity.getId());
        allSwimmingEvents.put(entity.getId(),entity);
        System.out.println("[SwimmingEventRepository] save swimmingEvent - exiting ok");
    }

    @Override
    public void delete(Long aLong) {
        if (allSwimmingEvents.containsKey(aLong))
            allSwimmingEvents.remove(aLong);
        else
            throw new RepositoryException("SwimmingEvent with Id ["+aLong+"] not found for deletion.");
    }

    @Override
    public void update(Long aLong, SwimmingEvent entity) {
        if (allSwimmingEvents.containsKey(aLong)){
            if (aLong.equals(entity.getId())) {
                allSwimmingEvents.put(aLong, entity);
                return;
            }

        }
        throw new RepositoryException("SwimmingEvent could not be updated "+entity);
    }

    @Override
    public SwimmingEvent findOne(Long aLong) {
        SwimmingEvent swimmingEventRes=allSwimmingEvents.get(aLong);
        if (swimmingEventRes==null)
            return null;
        return swimmingEventRes;
    }

    @Override
    public Iterable<SwimmingEvent> findAll() {
        return allSwimmingEvents.values();
    }
}
