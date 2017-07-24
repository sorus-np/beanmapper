package co.sorus.beanmapper;

public interface BeanMapper<S, T> {

    T map(S s);
}