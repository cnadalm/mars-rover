package com.aba.rover.control;

import com.aba.rover.control.exception.EngineException;
import com.aba.rover.entity.Movement;
import io.quarkus.scheduler.Scheduled;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class DefaultEngine implements Engine {

    private AtomicInteger batteryCapacity = new AtomicInteger();

    @Inject
    EntityManager em;

    @Override
    public Direction getCurrentDirection() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movement> cq = cb.createQuery(Movement.class);
        Root<Movement> root = cq.from(Movement.class);
        cq.select(root);

        Optional<Movement> movement = em.createQuery(cq).getResultList()
                .stream()
                .sorted((m1, m2) -> Long.compare(m1.getId(), m2.getId()))
                .findFirst();
        return movement.isPresent() ? movement.get().getDirection() : Direction.NONE;
    }

    @Override
    public void moveDirection(Direction direction) throws EngineException {

        if (batteryCapacity.get() > 0) {
            em.persist(new Movement(direction));
        }
    }

    @Override
    public Float getRemainingBatteryCapacity() {

        return batteryCapacity.floatValue();
    }

    @Override
    public void chargeBatteries() throws EngineException {

        this.moveDirection(Direction.NONE);
        this.batteryCapacity.getAndAdd(100);
    }

    @Scheduled(every = "1s")
    void consumeBattery() {

        if (batteryCapacity.get() > 0) {
            batteryCapacity.decrementAndGet();
        }
    }

}
