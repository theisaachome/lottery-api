package com.highway.lottery.modules.ticket.repo;
import com.highway.lottery.modules.ticket.dto.TicketFilter;
import com.highway.lottery.modules.ticket.entity.Ticket;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class TicketSpecifications {

   public static Specification<Ticket> withFilter(TicketFilter filter) {
       return (root,query,cb)->{
           List<Predicate> predicates = new ArrayList<>();
           // equal matched key value
           if (filter.ticketCode() != null) {
               predicates.add(cb.equal(root.get("ticketCode"), filter.ticketCode()));
           }
           if (filter.agentCode() !=null){
               predicates.add(cb.equal(root.get("agentId"), filter.agentCode()));
           }
           if(filter.drawType() !=null){
               predicates.add(cb.equal(root.get("drawType"), filter.drawType()));
           }

           if(filter.drawDateFrom()!=null){
               predicates.add(cb.equal(root.get("drawDate"), filter.drawDateFrom()));
           }

           // like
           if (filter.customerName() != null) {
               predicates.add(cb.like(cb.lower(root.get("customerName")), "%" + filter.customerName().toLowerCase() + "%"));
           }
           if(filter.phone() !=null){
               predicates.add(cb.like(cb.lower(root.get("phone")), "%" + filter.phone() + "%"));
           }

           // range between
           if(filter.drawDateFrom() !=null && filter.drawDateTo() != null){
               predicates.add(cb.between(root.get("drawDate"), filter.drawDateFrom(), filter.drawDateTo()));
           }



           return cb.and(predicates.toArray(new Predicate[0]));
       };
   }
}
