package fasttrackit.course9.repository.room;

import fasttrackit.course9.model.RoomFilters;
import fasttrackit.course9.model.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoomDao {

    private final MongoTemplate mongo;

    public Page<Room> getAllWithFilters(RoomFilters filters) {
        List<Criteria> criterias = new ArrayList<>();
        Optional.ofNullable(filters.getNumber())
                .ifPresent(element -> criterias.add(Criteria.where("number").is(element)));
        Optional.ofNullable(filters.getEtaj())
                .ifPresent(element -> criterias.add(Criteria.where("etaj").is(Integer.parseInt(element))));
        Optional.ofNullable(filters.getDoubleBed())
                .ifPresent(element -> criterias.add(Criteria.where("roomFacilities.doubleBed").is(Boolean.parseBoolean(element))));
        Optional.ofNullable(filters.getHotelName())
                .ifPresent(element -> criterias.add(Criteria.where("hotelName").is(element)));
        Optional.ofNullable(filters.getTv())
                .ifPresent(element -> criterias.add(Criteria.where("roomFacilities.tv").is(Boolean.parseBoolean(element))));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[0]));
        Pageable pageable = PageRequest.of(0, 2, Sort.by("roomNumber"));

        Query query = Query.query(criteria);

        List<Room> rooms = getAllRoomsWithCriteria(criterias, query);

        return PageableExecutionUtils.getPage(
                rooms,
                pageable,
                () -> getNumberPages(criterias, criteria));
    }

    List<Room> getAllRoomsWithCriteria(List<Criteria> criterias, Query query) {
        List<Room> rooms;
        if (criterias.isEmpty()) {
            rooms = mongo.findAll(Room.class);
        } else {
            rooms = mongo.find(
                    query,
                    Room.class);
        }
        return rooms;
    }

    Long getNumberPages(List<Criteria> criterias, Criteria criteria) {
        long pagesNumber;
        if (criterias.isEmpty()) {
            pagesNumber = mongo.findAll(Room.class).size();
        } else {
            pagesNumber = mongo.count(Query.query(criteria), Room.class);
        }
        return pagesNumber;
    }
}
