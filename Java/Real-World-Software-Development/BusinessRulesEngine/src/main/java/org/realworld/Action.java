package org.realworld;

/**
 * Action 하고자하는 동작을 의미한다.
 * 특정 조건이 충족되었을 때, 수행할 동작
 * Ex. 현재 지출이 지난 달의 지출을 넘어선 경우 특정 Action을 수행한다.
 */
@FunctionalInterface
public interface Action {
    void perform(Facts facts);
}
