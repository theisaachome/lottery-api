package com.highway.lottery.common.mapper;

public interface IModelMapper<I,O,E> {
    E toEntity(I data);
    O toResponseDto(E e);
}
