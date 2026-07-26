package com.sarana_NIS.newTown_backend.mapper;

import java.util.List;

public interface BaseMapper<E, R, C> {

    R toResponse(E entity);

    E toEntity(C request);

    default List<R> toResponseList(List<E> entities) {
        if (entities == null) return new java.util.ArrayList<>();
        return entities.stream()
                .map(this::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    default List<E> toEntityList(List<C> requests) {
        if (requests == null) return new java.util.ArrayList<>();
        return requests.stream()
                .map(this::toEntity)
                .collect(java.util.stream.Collectors.toList());
    }
}