package ua.com.parkhub.persistence.impl;

import org.springframework.stereotype.Repository;
import ua.com.parkhub.persistence.entities.Address;
import ua.com.parkhub.persistence.entities.Parking;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class ParkingDAO extends ElementDAO<Parking> {

    public ParkingDAO() {
        super(Parking.class);
    }

    public Long countOfParkingsByName(String parkingName) {
        CriteriaBuilder cb = emp.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        Root<Parking> root = cr.from(Parking.class);
        cr.select(cb.count(root));
        cr.where(cb.equal(root.get("parkingName"), parkingName));
        TypedQuery<Long> count = emp.createQuery(cr);
        return count.getSingleResult();
    }

    public Long countOfParkingsByAddress(String city, String street, String building) {
        CriteriaBuilder cb = emp.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        Root<Address> root = cr.from(Address.class);
        cr.select(cb.count(root));
        cr.where
                ((cb.equal(root.get("city"), city)),(cb.equal(root.get("street"), street)),(cb.equal(root.get("building"), building)));
        TypedQuery<Long> count = emp.createQuery(cr);
        return count.getSingleResult();
    }
}


