package com.integrator.group2backend.customFilters;

import com.integrator.group2backend.entities.Product;
import com.integrator.group2backend.entities.Reservation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    public static final Logger logger = Logger.getLogger(ProductRepositoryCustomImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
//    public List<Product> customDynamicQuery(Integer rooms, Integer beds, Integer bathrooms, Integer guests, Long city, Long category, Float minPrice, Float maxPrice, Date checkInDate, Date checkOutDate) {
      public List<Product> customDynamicQuery(Integer rooms, Integer beds, Integer bathrooms, Integer guests, Long city, Long category, Float minPrice, Float maxPrice) {


        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> queryProduct = cb.createQuery(Product.class);
        Root<Product> productRoot = queryProduct.from(Product.class);

        /*CriteriaQuery<Reservation> queryReservation = cb.createQuery(Reservation.class);
        Root<Reservation> reservationRoot = queryReservation.from(Reservation.class);*/

        List<Predicate> predicates = new ArrayList<>();

        if (rooms != (null)){
            Path<Integer> roomsPath = productRoot.get("rooms");
            predicates.add(cb.ge(roomsPath, rooms));
            logger.info("Filtro por habitaciones aplicado.");
        }
        if (beds != (null)){
            Path<Integer> bedsPath = productRoot.get("beds");
            predicates.add(cb.ge(bedsPath, beds));
            logger.info("Filtro por camas aplicado.");
        }
        if (bathrooms != (null)){
            Path<Integer> bathroomsPath = productRoot.get("bathrooms");
            predicates.add(cb.ge(bathroomsPath, bathrooms));
            logger.info("Filtro por banios aplicado.");
        }
        if (guests != (null)){
            Path<Integer> guestsPath = productRoot.get("guests");
            predicates.add(cb.ge(guestsPath, guests));
            logger.info("Filtro por huespedes aplicado.");
        }
        if (city != (null)){
            Path<Long> city_idPath = productRoot.get("city");
            predicates.add(cb.equal(city_idPath,city));
            logger.info("Filtro por city_id aplicado.");
        }
        if (category != (null)){
            Path<Long> category_idPath = productRoot.get("category");
            predicates.add(cb.equal(category_idPath, category));
            logger.info("Filtro por category_id aplicado.");
        }
        if (minPrice != (null)){
            Path<Float> minPricePath = productRoot.get("dailyPrice");
            predicates.add(cb.ge(minPricePath, minPrice));
            logger.info("Filtro por precio minimo aplicado.");
        }
        if (maxPrice != (null)){
            Path<Float> maxPricePath = productRoot.get("dailyPrice");
            predicates.add(cb.le(maxPricePath, maxPrice));
            logger.info("Filtro por precio maximo aplicado.");
        }
        /*
        if (!checkInDate.equals(null)){
            Path<Date> checkInDatePath = reservationRoot.get("checkInDate");
            predicates.add(cb.greaterThanOrEqualTo(checkInDatePath, checkInDate));
            logger.info("Filtro por fecha de inicio de la reserva aplicado.");
        }
        if (!checkOutDate.equals(null)){
            Path<Date> checkOutDatePath = reservationRoot.get("checkOutDate");
            predicates.add(cb.lessThanOrEqualTo(checkOutDatePath, checkOutDate));
            logger.info("Filtro por fecha de finalizacion de la reserva aplicado.");
        }
        */
        if (predicates.isEmpty()){
            logger.info("Se listaron todos los productos");
            queryProduct.select(productRoot);
        }else {
            logger.info("Los filtros fueron aplicados.");
            queryProduct.select(productRoot).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }
        System.out.println(entityManager.createQuery(queryProduct));
        System.out.println(entityManager.createQuery(queryProduct).getResultList());
        return entityManager.createQuery(queryProduct).getResultList();
    }
}
