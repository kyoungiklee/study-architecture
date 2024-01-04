package org.opennuri.study.architecture.remittance.adapter.out.persistence;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import org.opennuri.study.architecture.remittance.application.port.in.RemittanceSearchCommand;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class RemittanceSearchCriteria implements Specification<RemittanceRequestJpaEntity> {
    private final RemittanceSearchCommand criteria;

        @Override
        public Predicate toPredicate(@NonNull Root<RemittanceRequestJpaEntity> root
                , @NonNull CriteriaQuery<?> query
                , @NonNull CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            if(criteria.getSenderId() != null)
                predicates.add(criteriaBuilder.equal(root.get("senderId"), criteria.getSenderId()));
            if(criteria.getReceiverId() != null)
                predicates.add(criteriaBuilder.equal(root.get("receiverId"), criteria.getReceiverId()));
            if(criteria.getRequestType() != null)
                predicates.add(criteriaBuilder.equal(root.get("requestType"), criteria.getRequestType()));
            if(criteria.getToBankName() != null && !criteria.getToBankName().isBlank())
                predicates.add(criteriaBuilder.equal(root.get("toBankName"), criteria.getToBankName()));
            if(criteria.getToAccountNumber() != null && !criteria.getToAccountNumber().isBlank())
                predicates.add(criteriaBuilder.equal(root.get("toAccountNumber"), criteria.getToAccountNumber()));
            if(criteria.getAmount() != null)
                predicates.add(criteriaBuilder.equal(root.get("amount"), criteria.getAmount()));


            final Predicate[] predicateArray = new Predicate[predicates.size()];
            return query.where(criteriaBuilder.and(predicates.toArray(predicateArray)))
                    .distinct(true)
                    .getRestriction();
        }
    }
