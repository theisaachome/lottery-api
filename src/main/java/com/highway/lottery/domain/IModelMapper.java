package com.highway.lottery.domain;

public interface IModelMapper<I,O,E> {
    E toEntity(I data);
    O toResponseDto(E e);
}
